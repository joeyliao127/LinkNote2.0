package com.joeyliao.linknoteresource.invitation.service;

import com.joeyliao.linknoteresource.invitation.po.CreateInvitationPo;
import com.joeyliao.linknoteresource.invitation.po.DeleteInvitationPo;
import com.joeyliao.linknoteresource.invitation.po.GetInvitationRequestPo;
import com.joeyliao.linknoteresource.invitation.po.GetReceivedInvitationResponsePo;
import com.joeyliao.linknoteresource.invitation.po.GetSentInvitationResponsePo;

public interface InvitationService {

  void createInvitation(CreateInvitationPo po);

  GetSentInvitationResponsePo getSentInvitation(GetInvitationRequestPo po);

  GetReceivedInvitationResponsePo getReceivedInvitation(GetInvitationRequestPo po);

  void deleteInvitation(DeleteInvitationPo po);
}
