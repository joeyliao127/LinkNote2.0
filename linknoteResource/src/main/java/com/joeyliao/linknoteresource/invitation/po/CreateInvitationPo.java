package com.joeyliao.linknoteresource.invitation.po;

import lombok.Data;

@Data
public class CreateInvitationPo {
  private String notebookId;
  private String inviteeId;
  private String email;
}
