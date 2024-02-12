package com.joeyliao.linknoteresource.invitation.po;

import com.joeyliao.linknoteresource.invitation.dto.ReceivedInvitationDTO;
import java.util.List;
import lombok.Data;

@Data
public class GetReceivedInvitationResponsePo {
  private List<ReceivedInvitationDTO> invitations;
  private Boolean nextPage;
}
