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
      responsePo.setNotebooks(notebookDAO.getAllCoNotebook(po));
    } else {
      log.info("執行notebook查詢");
      responsePo.setNotebooks(notebookDAO.getAllNotebooks(po));
    }

    log.info("limit: " + po.getLimit());
    log.info("dto長度：" + responsePo.getNotebooks().size());
    if (responsePo.getNotebooks().size() == po.getLimit()) {
      log.info("長度一樣");
    } else {
      log.info("長度不一樣");
    }
    responsePo.getNotebooks().remove(responsePo.getNotebooks().size() - 1);
    responsePo.setNextPage(responsePo.getNotebooks().size() == po.getLimit());

    return responsePo;
  }
}
