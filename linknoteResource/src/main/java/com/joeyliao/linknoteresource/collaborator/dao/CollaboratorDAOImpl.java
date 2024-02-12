package com.joeyliao.linknoteresource.collaborator.dao;

import com.joeyliao.linknoteresource.collaborator.dao.CollaboratorDAO;
import com.joeyliao.linknoteresource.collaborator.dto.CollaboratorsDTO;
import com.joeyliao.linknoteresource.collaborator.po.CreateCollaboratorPo;
import com.joeyliao.linknoteresource.collaborator.po.DeleteCollaboratorPo;
import com.joeyliao.linknoteresource.collaborator.po.GetCollaboratorsRequestPo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CollaboratorDAOImpl implements CollaboratorDAO {

  @Autowired
  NamedParameterJdbcTemplate namedParameterJdbcTemplate;
  @Override
  public List<CollaboratorsDTO> getCollaborators(GetCollaboratorsRequestPo po) {
    return null;
  }

  @Override
  public void deleteCollaborator(DeleteCollaboratorPo po) {

  }

  @Override
  public void createCollaborator(CreateCollaboratorPo po) {
    
  }
}
