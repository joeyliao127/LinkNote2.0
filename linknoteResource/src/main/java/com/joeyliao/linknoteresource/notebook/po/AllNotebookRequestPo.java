package com.joeyliao.linknoteresource.notebook.po;

import com.joeyliao.linknoteresource.generic.po.PaginationPo;
import lombok.Data;

@Data
public class AllNotebookRequestPo extends PaginationPo {
  private String userId;
}
