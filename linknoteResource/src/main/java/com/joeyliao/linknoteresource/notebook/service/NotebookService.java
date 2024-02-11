package com.joeyliao.linknoteresource.notebook.service;

import com.joeyliao.linknoteresource.notebook.po.AllNotebookRequestPo;
import com.joeyliao.linknoteresource.notebook.po.AllNotebookResponsePo;
import com.joeyliao.linknoteresource.notebook.po.CreateNotebookRequestPo;
import com.joeyliao.linknoteresource.notebook.po.UpdateNotebookPo;

public interface NotebookService {

  void createNotebook(CreateNotebookRequestPo po, String authorization);

  AllNotebookResponsePo getAllNotebooks(AllNotebookRequestPo po);

  AllNotebookResponsePo getCoNotebooks(AllNotebookRequestPo po);

  void updateNotebook(UpdateNotebookPo po);

  void deleteNotebook(String notebookId);
}
