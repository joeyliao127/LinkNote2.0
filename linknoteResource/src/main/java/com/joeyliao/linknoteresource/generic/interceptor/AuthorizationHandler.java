package com.joeyliao.linknoteresource.generic.interceptor;

import com.joeyliao.linknoteresource.generic.enums.Behavior;
import com.joeyliao.linknoteresource.generic.enums.Target;
import com.joeyliao.linknoteresource.generic.requestobject.AuthRequestBody;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerMapping;

@Component
@Slf4j
public class AuthorizationHandler {

  public Boolean checkAccessPermission(HttpServletRequest request, HttpServletResponse response,
      Target target) {
    log.info("=================執行Notebook preHandle=================");

    Map pathVariables = (Map) request.getAttribute(
        HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
    String notebookId = (String) pathVariables.get("notebookId");
    String Authorization = request.getHeader("Authorization");
    HttpMethod httpMethod = HttpMethod.valueOf(request.getMethod());
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", Authorization);
    String url = setURL(request.getRequestURI(), Authorization, response, target);

    AuthRequestBody requestBody = new AuthRequestBody();
    requestBody.setBehavior(behaviorMapping(httpMethod));
    requestBody.setNotebookId(notebookId);
    HttpEntity httpEntity = new HttpEntity(requestBody, headers);
    log.info("AuthorizationHandler：接收到驗證權限請求");
    log.info("HTTP method: " + request.getMethod());
    log.info("Target: " + target);
    log.info("NotebookId: " + notebookId);
    log.info("Authorization: " + Authorization);
    log.info("請求resource路徑：" + request.getRequestURI());
    log.info("發送auth request為：" + url);
    return sendAccessPermissionRequest(url, httpEntity, response);
  }

  private Behavior behaviorMapping(HttpMethod httpMethod) {
    Map<HttpMethod, Behavior> map = new HashMap<>();
    map.put(HttpMethod.GET, Behavior.READ);
    map.put(HttpMethod.POST, Behavior.CREATE);
    map.put(HttpMethod.PUT, Behavior.UPDATE);
    map.put(HttpMethod.DELETE, Behavior.DELETE);
    return map.get(httpMethod);
  }

  private Boolean sendAccessPermissionRequest(String url, HttpEntity httpEntity,
      HttpServletResponse response) {
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<Boolean> result = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
        Boolean.class);
    log.info("請求結果：" + result.getBody());
    log.info("=================結束Notebook preHandle=================");
    if (Boolean.TRUE.equals(result.getBody())) {
      return true;
    } else {
      log.warn("access deny");
      response.setStatus(401);
      return false;
    }
  }

  private String setURL(String requestURL, String Authorization, HttpServletResponse response,
      Target target) {
    if (requestURL.equals("/api/notebooks")) {
      if (Authorization == null) {
        //如果沒有token又想執行GET或CREATE，直接return false。
        response.setStatus(400);

      }
      return "http://localhost:8080/api/auth/user/token";
    } else {
      return "http://localhost:8080/api/auth/" + target.toString().toLowerCase();
    }
  }
}
