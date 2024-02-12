package com.joeyliao.linknoteresource.tag.po;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateNoteTagRequestPo {
  String noteId;
  @NotBlank
  String tagId;
}
