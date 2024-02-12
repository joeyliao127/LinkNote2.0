package com.joeyliao.linknoteresource.tag.po;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateNotebookTagRequestPo {
  private String notebookId;
  @NotBlank
  private String name;
}
