package com.joeyliao.linknoteresource.invitation.po;

import java.util.List;
import lombok.Data;

@Data
public class GetSentInvitationResponsePo {
  private List<GetSentInvitationResponsePo> invitations;
  private Boolean nextPage;
}
