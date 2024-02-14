package com.joeyliao.linknoteresource.invitation.dao;

import com.joeyliao.linknoteresource.invitation.dto.InvitationDTO;
import com.joeyliao.linknoteresource.invitation.dto.ReceivedInvitationDTO;
import com.joeyliao.linknoteresource.invitation.dto.SentInvitationDTO;
import com.joeyliao.linknoteresource.invitation.po.CreateInvitationPo;
import com.joeyliao.linknoteresource.invitation.po.DeleteInvitationPo;
import com.joeyliao.linknoteresource.invitation.po.GetInvitationRequestPo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class InvitationDAOImpl implements InvitationDAO {

  @Autowired
  NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public void createInvitation(CreateInvitationPo po) {
    String sql = """
        INSERT INTO invitations(inviterEmail, inviteeEmail, notebookId, message)
        VALUES (:inviterEmail, :inviteeEmail, :notebookId, :message)
        """;
    Map<String, Object> map = new HashMap<>();
    map.put("inviterEmail", po.getInviterEmail());
    map.put("inviteeEmail", po.getInviteeEmail());
    map.put("notebookId", po.getNotebookId());
    map.put("message", po.getMessage());
    namedParameterJdbcTemplate.update(sql, map);
  }

  @Override
  public List<Integer> checkInvitationNotExist(CreateInvitationPo po) {
    return null;
  }

  @Override
  public List<SentInvitationDTO> getSentInvitation(GetInvitationRequestPo po) {
    return null;
  }

  @Override
  public List<ReceivedInvitationDTO> getReceivedInvitation(GetInvitationRequestPo po) {
    return null;
  }

  @Override
  public void deleteInvitation(DeleteInvitationPo po) {

  }
}
