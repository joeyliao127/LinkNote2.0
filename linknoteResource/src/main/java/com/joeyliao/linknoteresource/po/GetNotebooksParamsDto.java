package com.joeyliao.linknoteresource.po;

import lombok.Data;

@Data

public class GetNotebooksParamsDto {

  private Integer userId;
  private Integer offset;
  private Integer limit;
  private String keyword;
  private Boolean coNotebook;

}
