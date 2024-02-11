package com.joeyliao.linknoteresource.note.po;

import lombok.Data;

@Data
public class updateNotePo {
  String name;
  String question;
  String content;
  String keypoint;
  Boolean star;
  String noteId;
}
