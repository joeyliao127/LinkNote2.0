package com.joeyliao.linknoteresource.generic.config;

import com.joeyliao.linknoteresource.generic.interceptor.CollaboratorInterceptor;
import com.joeyliao.linknoteresource.generic.interceptor.DefaultInterceptor;
import com.joeyliao.linknoteresource.generic.interceptor.InvitationInterceptor;
import com.joeyliao.linknoteresource.generic.interceptor.NoteInterceptor;
import com.joeyliao.linknoteresource.generic.interceptor.NotebookInterceptor;
import com.joeyliao.linknoteresource.generic.interceptor.TagInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

  @Autowired
  NotebookInterceptor notebookInterceptor;

  @Autowired
  NoteInterceptor noteInterceptor;

  @Autowired
  TagInterceptor tagInterceptor;

  @Autowired
  InvitationInterceptor invitationInterceptor;

  @Autowired
  CollaboratorInterceptor collaboratorInterceptor;

  @Autowired
  DefaultInterceptor defaultInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(tagInterceptor).addPathPatterns("/api/notebooks/**/notes/**/tags/**");
    registry.addInterceptor(noteInterceptor).addPathPatterns("/api/notebooks/*/notes/**");
    registry.addInterceptor(invitationInterceptor)
        .addPathPatterns("/api/notebooks/*/invitations/**"
            , "/api/invitations/sent-invitations"
            , "/api/invitations/received-invitations");
    registry.addInterceptor(collaboratorInterceptor)
        .addPathPatterns("/api/notebooks/*/collaborators/**");
    registry.addInterceptor(notebookInterceptor).addPathPatterns("/api/notebooks/**", "/api/coNotebooks");
    registry.addInterceptor(defaultInterceptor).addPathPatterns("*")
        .excludePathPatterns("/", "notebooks", "notes");

  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/user/**")
        .allowedOrigins(":8081", ":443", ":80")
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        .allowedHeaders("*");
  }
}
