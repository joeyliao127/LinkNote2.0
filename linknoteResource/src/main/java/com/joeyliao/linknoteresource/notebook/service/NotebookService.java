package com.joeyliao.linknoteresource.notebook.service;

import com.joeyliao.linknoteresource.notebook.dto.NotebooksDTO;
import com.joeyliao.linknoteresource.notebook.po.AllNotebookRequestPo;
import com.joeyliao.linknoteresource.notebook.po.CreateNotebookRequestPo;
import com.joeyliao.linknoteresource.notebook.po.UpdateNotebookPo;
import java.util.List;

public interface NotebookService {
  void createNotebook(CreateNotebookRequestPo po, String authorization);

  List<NotebooksDTO> getAllNotebooks(AllNotebookRequestPo po);

  List<NotebooksDTO> getCoNotebooks(AllNotebookRequestPo po);

  void updateNotebook(UpdateNotebookPo po);

  void deleteNotebook(String notebookId);
}
