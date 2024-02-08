package com.joeyliao.linknoteresource.generic.interceptor;

import com.joeyliao.linknoteresource.generic.enums.Behavior;
import com.joeyliao.linknoteresource.generic.enums.Target;
import com.joeyliao.linknoteresource.generic.requestobject.AuthRequestBody;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
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

  public Boolean checkAccessPermission(HttpServletRequest request, Target target) {

    Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(
        HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

    String notebookId = pathVariables.get("notebookId");
    String Authorization = request.getHeader("Authorization");
    HttpMethod httpMethod = HttpMethod.valueOf(request.getMethod());

    log.info("AuthorizationHandler：接收到驗證權限請求");
    log.info("HTTP method: " + request.getMethod());
    log.info("Target: " + target);
    log.info("NotebookId: " + notebookId);
    log.info("Authorization: " + Authorization);

    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:8080/api/auth" + target.toString();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", Authorization);
    AuthRequestBody requestBody = new AuthRequestBody();
    requestBody.setHttpMethod(httpMethod);
    HttpEntity httpEntity = new HttpEntity(requestBody, headers);

    ResponseEntity<Boolean> response = restTemplate.exchange(url, httpMethod, httpEntity,
        Boolean.class);

    log.info("請求結果：" + response.getBody());
    return  response.getBody();
  }

}
