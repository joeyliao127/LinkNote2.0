package com.joeyliao.linknoteresource.generic.interceptor;

import com.joeyliao.linknoteresource.generic.enums.Behavior;
import com.joeyliao.linknoteresource.generic.enums.Target;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationHandler {
  public Boolean checkAccessPermission(Integer notebookId, HttpMethod httpMethod){

  }

}
