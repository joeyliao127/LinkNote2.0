package com.joeyliao.linknoteresource.invitation.dao;

import com.joeyliao.linknoteresource.invitation.dto.InvitationDTO;
import com.joeyliao.linknoteresource.invitation.dto.ReceivedInvitationDTO;
import com.joeyliao.linknoteresource.invitation.dto.SentInvitationDTO;
import com.joeyliao.linknoteresource.invitation.po.CreateInvitationPo;
import com.joeyliao.linknoteresource.invitation.po.DeleteInvitationPo;
import com.joeyliao.linknoteresource.invitation.po.GetInvitationRequestPo;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class InvitationDAOImpl implements InvitationDAO {

  @Override
  public void createInvitation(CreateInvitationPo po) {

  }

  @Override
  public List<Integer> checkInvitationNotExist(String inviteeId, String notebookId) {
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
