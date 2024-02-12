package com.joeyliao.linknoteresource.invitation.controller;

import com.joeyliao.linknoteresource.invitation.po.CreateInvitationPo;
import com.joeyliao.linknoteresource.invitation.po.DeleteInvitationPo;
import com.joeyliao.linknoteresource.invitation.po.GetInvitationRequestPo;
import com.joeyliao.linknoteresource.invitation.service.InvitationService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvitationController {

  @Autowired
  InvitationService invitationService;

  @PostMapping("/api/notebooks/{notebookId}/invitations")
  public ResponseEntity<Object> createInvitation(
      @PathVariable String notebookId
  ) {
    CreateInvitationPo po = new CreateInvitationPo();
    po.setNotebookId(notebookId);
    invitationService.createInvitation(po);
    return ResponseEntity.status(200).body(Map.of("result", true));
  }

  @Validated
  @GetMapping("/api/user/sent-invitation")
  public ResponseEntity<Object> getSentInvitation(
      @RequestHeader String Authorization,
      @RequestParam String keyword,
      @RequestParam(defaultValue = "true") Boolean sortDesc,
      @RequestParam(defaultValue = "1") @Min(1) @Max(20) Integer limit,
      @RequestParam(defaultValue = "0") @Min(0) Integer offset
  ) {
    GetInvitationRequestPo po = InvitationRequestParamGenerator(
        Authorization, keyword, sortDesc, limit, offset);
    return ResponseEntity.status(200).body(invitationService.getSentInvitation(po));
  }

  @GetMapping("/api/user/received-invitation")
  public ResponseEntity<Object> getReceivedInvitation(
      @RequestHeader String Authorization,
      @RequestParam String keyword,
      @RequestParam(defaultValue = "true") Boolean sortDesc,
      @RequestParam(defaultValue = "1") @Min(1) @Max(20) Integer limit,
      @RequestParam(defaultValue = "0") @Min(0) Integer offset
  ) {
    GetInvitationRequestPo po = InvitationRequestParamGenerator(
        Authorization, keyword, sortDesc, limit, offset);
    po.setAuthorization(Authorization);
    return ResponseEntity.status(200).body(invitationService.getReceivedInvitation(po));
  }

  @DeleteMapping("/api/notebooks/{notebookId}/invitations/{invitationId}")
  public ResponseEntity<Object> deleteInvitation(
      @PathVariable String notebookId,
      @PathVariable String invitationId
  ){
    DeleteInvitationPo po = new DeleteInvitationPo();
    po.setNotebookId(notebookId);
    po.setInvitationId(invitationId);
    invitationService.deleteInvitation(po);
    return ResponseEntity.status(200).body(Map.of("result", true));
  }


  private GetInvitationRequestPo InvitationRequestParamGenerator(
      String Authorization,
      String keyword,
      Boolean sortDesc,
      Integer limit,
      Integer offset) {

    GetInvitationRequestPo po = new GetInvitationRequestPo();
    po.setKeyword(keyword);
    po.setOffset(offset);
    po.setLimit(limit);
    po.setAuthorization(Authorization);
    po.setOrderByDesc(sortDesc);

    return po;
  }
}
