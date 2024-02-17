package com.joeyliao.linknote.templates;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class templatesController {

  @GetMapping("/")
  public String home(){
    return "index";
  }

  @GetMapping("/notebooks")
  public String notebooks(){
    return "notebooks";
  }

  @GetMapping("/notes")
  public String notes(){
    return "notes";
  }
}
