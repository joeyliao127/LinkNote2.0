package com.joeyliao.linknoteresource.note.dto;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class NoteDTO {
  String name;
  String question;
  String content;
  String keypoint;
  Timestamp createDate;
  Boolean star;
}
