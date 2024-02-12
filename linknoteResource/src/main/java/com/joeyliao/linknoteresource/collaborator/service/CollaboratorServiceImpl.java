package com.joeyliao.linknoteresource.collaborator.service;

import com.joeyliao.linknoteresource.collaborator.dao.CollaboratorDAO;
import com.joeyliao.linknoteresource.collaborator.dto.CollaboratorsDTO;
import com.joeyliao.linknoteresource.collaborator.po.CreateCollaboratorPo;
import com.joeyliao.linknoteresource.collaborator.po.DeleteCollaboratorPo;
import com.joeyliao.linknoteresource.collaborator.po.GetCollaboratorsRequestPo;
import com.joeyliao.linknoteresource.collaborator.po.GetCollaboratorsResponsePo;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CollaboratorServiceImpl implements CollaboratorService {

  @Autowired
  CollaboratorDAO collaboratorDAO;

  @Override
  public GetCollaboratorsResponsePo getCollaborators(GetCollaboratorsRequestPo po) {
    GetCollaboratorsResponsePo responsePo = new GetCollaboratorsResponsePo();
    List<CollaboratorsDTO> list = collaboratorDAO.getCollaborators(po);
    responsePo.setList(list);
    return responsePo;
  }

  @Override
  public void deleteCollaborator(DeleteCollaboratorPo po) {
    collaboratorDAO.deleteCollaborator(po);
  }

  @Override
  public void createCollaborator(CreateCollaboratorPo po) {
    collaboratorDAO.createCollaborator(po);
  }
}
