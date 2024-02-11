package com.joeyliao.linknoteresource.notebook.po;

import lombok.Data;

@Data
public class CreateNotebookRequestPo {
  private String name;
  private String description;
  private String userId;
}
