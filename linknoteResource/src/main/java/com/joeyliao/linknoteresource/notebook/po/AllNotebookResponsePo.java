package com.joeyliao.linknoteresource.notebook.po;

import com.joeyliao.linknoteresource.notebook.dto.NotebooksDTO;
import java.util.List;
import lombok.Data;

@Data
public class AllNotebookResponsePo {
  List<NotebooksDTO> notebooks;
  Boolean nextPage;
}
