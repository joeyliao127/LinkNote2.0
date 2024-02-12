package com.joeyliao.linknoteresource.note.po;

import lombok.Data;

@Data
public class GetNotesRequestPo {
  private String notebookId;
  private Integer offset;
  private Integer limit;
  private String tag;
  private Boolean star;
  private Boolean sortDesc;
  private String keyword;
}
