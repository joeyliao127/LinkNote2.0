package com.joeyliao.linknoteresource.generic.interceptor;

import com.joeyliao.linknoteresource.generic.enums.Target;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class CollaboratorInterceptor implements HandlerInterceptor {

  @Autowired
  AuthorizationHandler authorizationHandler;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    log.info("執行collaborator preHandle");
    return authorizationHandler.checkAccessPermission(request,response, Target.COLLABORATOR);
  }
}
