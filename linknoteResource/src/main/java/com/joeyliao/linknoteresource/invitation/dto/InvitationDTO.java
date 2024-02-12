package com.joeyliao.linknoteresource.invitation.dto;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class InvitationDTO {
  private Integer invitationId;
  private String message;
  private String notebook;
  private Timestamp Date;
}
