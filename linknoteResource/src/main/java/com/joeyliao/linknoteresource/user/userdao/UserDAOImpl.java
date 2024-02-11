package com.joeyliao.linknoteresource.user.userdao;

import com.joeyliao.linknoteresource.user.userRowMapper.UserIdRowMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class UserDAOImpl implements UserDAO {

  @Autowired
  NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public List<String> verifyUserByEmailAndUserId(String email, String userId) {
    String sql = """
        SELECT id FROM users
        WHERE id = :userId and email = :email
        """;
    log.info("userId:", userId);
    log.info("email:", email);
    Map<String, Object> map = new HashMap<>();
    map.put("userId", userId);
    map.put("email", email);
    List<String> list = namedParameterJdbcTemplate.query(sql, map, new UserIdRowMapper());
    log.info("user查詢的list為：" + list);
    return namedParameterJdbcTemplate.query(sql, map, new UserIdRowMapper());
  }
}
