package com.joeyliao.linknoteresource.note.po;

import lombok.Data;

@Data
public class updateNotePo {
  private String name;
  private String question;
  private String content;
  private String keypoint;
  private Boolean star;
  private String noteId;
}
