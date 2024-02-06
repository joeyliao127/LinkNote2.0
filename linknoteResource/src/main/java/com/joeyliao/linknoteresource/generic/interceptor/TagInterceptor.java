package com.joeyliao.linknoteresource.generic.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class TagInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    log.info("執行Tag preHandle");
    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer" );
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity requestEntity = new HttpEntity(headers);

    ResponseEntity<Boolean> res = restTemplate.exchange(
        "https://localhost:8080/api/auth/notebook",
        HttpMethod.POST,
        requestEntity,
        Boolean.class
    );
    return res.getBody();
  }
}
