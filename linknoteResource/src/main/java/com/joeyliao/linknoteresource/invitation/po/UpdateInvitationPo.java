package com.joeyliao.linknoteresource.invitation.po;

import lombok.Data;

@Data
public class UpdateInvitationPo {
 private String inviteeEmail;
 private String notebookId;
 private Boolean isAccept;
}
