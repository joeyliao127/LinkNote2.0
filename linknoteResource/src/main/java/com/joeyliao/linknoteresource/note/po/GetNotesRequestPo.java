package com.joeyliao.linknoteresource.note.po;

import lombok.Data;

@Data
public class GetNotesRequestPo {
  String notebookId;
  Integer offset;
  Integer limit;
  String tag;
  Boolean star;
  Boolean sortDesc;
  String keyword;
}
