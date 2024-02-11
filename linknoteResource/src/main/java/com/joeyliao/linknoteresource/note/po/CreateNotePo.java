package com.joeyliao.linknoteresource.note.po;

import lombok.Data;

@Data
public class CreateNotePo {
  String notebookId;
  String name;
  String question;
  String content;
  String keypoint;
  Boolean star;
}
