package com.joeyliao.linknoteresource.invitation.dao;

import com.joeyliao.linknoteresource.invitation.dto.InvitationDTO;
import com.joeyliao.linknoteresource.invitation.po.CreateInvitationPo;
import com.joeyliao.linknoteresource.invitation.po.GetInvitationRequestPo;
import java.util.List;

public interface InvitationDAO {

  void createInvitation(CreateInvitationPo po);

  List<Integer> checkInvitationNotExist(String inviteeId, String notebookId);

  List<InvitationDTO> getSentInvitation(GetInvitationRequestPo po);

  List<InvitationDTO> getReceivedInvitation(GetInvitationRequestPo po);
}
