package com.joeyliao.linknoteresource.token.service;

import com.joeyliao.linknoteresource.token.exception.TokenExpirationException;
import com.joeyliao.linknoteresource.token.exception.TokenInvalidException;
import com.joeyliao.linknoteresource.user.userdao.UserDAO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TokenServiceImpl implements TokenService {

  @Value("${jwt.secretKey}")
  private String SECRET;

  @Autowired
  UserDAO userDAO;

  @Override
  public String genJWTToken(String userId, String email, String username) {
    byte[] decodekey = Base64.getDecoder().decode(SECRET.getBytes(StandardCharsets.UTF_8));
    return Jwts.builder()
        .issuedAt(new Date())
        .issuer("linkNote")
        .expiration(new Date(System.currentTimeMillis() + 604800000L * 4))//一個月
        .subject(username)
        .claim("userId", userId)
        .claim("email", email)
        .signWith(new SecretKeySpec(decodekey, "HmacSHA256"))
        .compact();
  }

  @Override
  public Claims parserJWTToken(String Authorization) {
    String token = Authorization.substring(7);
    byte[] decodekey = Base64.getDecoder().decode(SECRET.getBytes(StandardCharsets.UTF_8));
    SecretKey key = new SecretKeySpec(decodekey, "HmacSHA256");
    try {
      Claims payload = Jwts.parser().verifyWith(key).build().parseClaimsJws(token).getPayload();
      return payload;
    } catch (RuntimeException e) {
      log.warn("token解析錯誤：" + e.getMessage());
      e.getMessage();
      throw new TokenInvalidException("invalid token");
    }
  }

  @Override
  public Boolean verifyToken(String Authorization) {
    Claims claims = this.parserJWTToken(Authorization);
    if (System.currentTimeMillis() < claims.getExpiration().getTime()) {
      String userId = claims.get("userId", String.class);
      String email = claims.get("email", String.class);
      log.info("userId: " + claims.get("userId", String.class));
      log.info("email: " + claims.get("email", String.class));
      log.info("userId: " + userId);
      log.info("email: " + email);
      List<String> userInfoPOS = userDAO.verifyUserByEmailAndUserId(
         email, userId);

      if (userInfoPOS.isEmpty()) {
        log.warn("token Service: 無效的token.");
        throw new TokenInvalidException("Invalid token");
      } else {
        log.info("TokenService - JWT token驗證成功，允許使用者登入。 User: " + claims.getSubject()
            + " UserId: " + claims.get("userId", String.class));
        return true;
      }
    } else {
      log.warn("token已過期. (TokenImpl)");
      throw new TokenExpirationException("token expired");
    }
  }
}
