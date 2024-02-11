package com.joeyliao.linknoteresource.notebook.po;

import lombok.Data;

@Data
public class AllNotebookRequestPo {
  private String userId;
  private Integer limit;
  private Integer offset;
  private String keyword;
  private Boolean orderByDesc;
}
