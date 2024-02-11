package com.joeyliao.linknoteresource.notebook.dao;

import com.joeyliao.linknoteresource.notebook.dto.NotebooksDTO;
import com.joeyliao.linknoteresource.notebook.po.AllNotebookRequestPo;
import com.joeyliao.linknoteresource.notebook.po.CreateNotebookRequestPo;
import com.joeyliao.linknoteresource.notebook.po.UpdateNotebookPo;
import java.util.List;
import org.springframework.data.relational.core.sql.In;

public interface NotebookDAO {
  Integer createNotebook(CreateNotebookRequestPo po, String id);

  List<NotebooksDTO> getAllNotebooks(AllNotebookRequestPo po);

  List<NotebooksDTO> getAllCoNotebook(AllNotebookRequestPo po);

  Integer updateNotebook(UpdateNotebookPo po);

  Integer deleteNotebook(String notebookId);

}
