package com.joeyliao.linknoteresource.note.po;

import lombok.Data;

@Data
public class CreateNotePo {
  private String notebookId;
  private String name;
  private String question;
  private String content;
  private String keypoint;
  private Boolean star;
}
