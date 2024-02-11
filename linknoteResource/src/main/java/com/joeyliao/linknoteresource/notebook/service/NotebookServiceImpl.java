package com.joeyliao.linknoteresource.notebook.service;

import com.joeyliao.linknoteresource.generic.enums.Target;
import com.joeyliao.linknoteresource.generic.uuidgenerator.service.UUIDGeneratorService;
import com.joeyliao.linknoteresource.notebook.dao.NotebookDAO;
import com.joeyliao.linknoteresource.notebook.dto.NotebooksDTO;
import com.joeyliao.linknoteresource.notebook.po.AllNotebookRequestPo;
import com.joeyliao.linknoteresource.notebook.po.AllNotebookResponsePo;
import com.joeyliao.linknoteresource.notebook.po.CreateNotebookRequestPo;
import com.joeyliao.linknoteresource.notebook.po.UpdateNotebookPo;
import com.joeyliao.linknoteresource.token.service.TokenService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class NotebookServiceImpl implements NotebookService {

  @Autowired
  NotebookDAO notebookDAO;

  @Autowired
  TokenService tokenService;

  @Autowired
  UUIDGeneratorService uuidGeneratorService;

  @Transactional
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
  public AllNotebookResponsePo getAllNotebooks(AllNotebookRequestPo po) {
    return getNotebooks(po, false);
  }

  @Override
  public AllNotebookResponsePo getCoNotebooks(AllNotebookRequestPo po) {
    return getNotebooks(po, true);
  }

  @Override
  public void updateNotebook(UpdateNotebookPo po) {
    notebookDAO.updateNotebook(po);
  }

  @Override
  public void deleteNotebook(String notebookId) {
    notebookDAO.deleteNotebook(notebookId);
  }

  private AllNotebookResponsePo getNotebooks(AllNotebookRequestPo po, Boolean isCoNotebook) {
    AllNotebookResponsePo responsePo = new AllNotebookResponsePo();
    if (isCoNotebook) {
      log.info("coNotebook查詢");
      responsePo.setNotebooks(notebookDAO.getAllCoNotebook(po));
    } else {
      log.info("notebook查詢");
      responsePo.setNotebooks(notebookDAO.getAllNotebooks(po));
    }

    if (responsePo.getNotebooks().isEmpty()) {
      responsePo.setNextPage(false);
      return responsePo;
    }
    if (responsePo.getNotebooks().size() == po.getLimit()) {
      responsePo.setNextPage(true);
      responsePo.getNotebooks().remove(responsePo.getNotebooks().size() - 1);
    } else {
      responsePo.setNextPage(false);
    }
    return responsePo;
  }
}
