package com.joeyliao.linknoteresource.collaborator.dao;

import com.joeyliao.linknoteresource.collaborator.dto.CollaboratorsDTO;
import com.joeyliao.linknoteresource.collaborator.po.CreateCollaboratorPo;
import com.joeyliao.linknoteresource.collaborator.po.DeleteCollaboratorPo;
import com.joeyliao.linknoteresource.collaborator.po.GetCollaboratorsRequestPo;
import com.joeyliao.linknoteresource.generic.enums.Role;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
  public void createCollaborator(String inviteeEmail, String notebookId) {
    String sql = """
        INSERT INTO notebooks_users_role(userId, notebookId, roleId)
        VALUES (:inviteeEmail, :notebookId, :roleId)
        """;
    Map<String, Object> map = new HashMap<>();
    map.put("inviteeEmail", inviteeEmail);
    map.put("notebookId", notebookId);
    map.put("roleId", roleMapper(Role.COLLABORATOR));
    namedParameterJdbcTemplate.update(sql, map);
  }

  private Integer roleMapper(Role role){
    Map<Role, Integer> map = new HashMap<>();
    map.put(Role.OWNER, 1);
    map.put(Role.COLLABORATOR, 2);
    map.put(Role.MEMBER, 3);
    map.put(Role.GUEST, 4);
    return map.get(role);
  }
}
