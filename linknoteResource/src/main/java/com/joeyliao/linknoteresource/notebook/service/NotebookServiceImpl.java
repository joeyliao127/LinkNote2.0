package com.joeyliao.linknoteresource.notebook.service;

import com.joeyliao.linknoteresource.generic.enums.Target;
import com.joeyliao.linknoteresource.generic.uuidgenerator.service.UUIDGeneratorService;
import com.joeyliao.linknoteresource.notebook.dao.NotebookDAO;
import com.joeyliao.linknoteresource.notebook.dto.NotebooksDTO;
import com.joeyliao.linknoteresource.notebook.po.AllNotebookRequestPo;
import com.joeyliao.linknoteresource.notebook.po.CreateNotebookRequestPo;
import com.joeyliao.linknoteresource.notebook.po.UpdateNotebookPo;
import com.joeyliao.linknoteresource.token.service.TokenService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotebookServiceImpl implements NotebookService {

  @Autowired
  NotebookDAO notebookDAO;

  @Autowired
  TokenService tokenService;

  @Autowired
  UUIDGeneratorService uuidGeneratorService;

  @Override
  public void createNotebook(CreateNotebookRequestPo po, String authorization) {
    tokenService.verifyToken(authorization);
    po.setUserId(tokenService.parserJWTToken(authorization)
        .get("userId", String.class));
    String id = "NB" + uuidGeneratorService.generateUUID(Target.NOTEBOOK);
    log.info("Notebook產生的UUID為：" + id);
    notebookDAO.createNotebook(po, id);
  }

  @Override
  public List<NotebooksDTO> getAllNotebooks(AllNotebookRequestPo po) {
    return notebookDAO.getAllNotebooks(po);
  }

  @Override
  public List<NotebooksDTO> getCoNotebooks(AllNotebookRequestPo po) {
    return notebookDAO.getAllCoNotebook(po);
  }

  @Override
  public void updateNotebook(UpdateNotebookPo po) {
    notebookDAO.updateNotebook(po);
  }

  @Override
  public void deleteNotebook(String notebookId) {
    notebookDAO.deleteNotebook(notebookId);
  }
}
