package com.joeyliao.linknoteresource.invitation.service;

import com.joeyliao.linknoteresource.generic.po.UserInfo;
import com.joeyliao.linknoteresource.invitation.dao.InvitationDAO;
import com.joeyliao.linknoteresource.invitation.dto.ReceivedInvitationDTO;
import com.joeyliao.linknoteresource.invitation.dto.SentInvitationDTO;
import com.joeyliao.linknoteresource.invitation.exception.InvitationAlreadyExistException;
import com.joeyliao.linknoteresource.invitation.po.CreateInvitationPo;
import com.joeyliao.linknoteresource.invitation.po.DeleteInvitationPo;
import com.joeyliao.linknoteresource.invitation.po.GetInvitationRequestPo;
import com.joeyliao.linknoteresource.invitation.po.GetReceivedInvitationResponsePo;
import com.joeyliao.linknoteresource.invitation.po.GetSentInvitationResponsePo;
import com.joeyliao.linknoteresource.invitation.po.InvitationResponsePo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class InvitationServiceImpl implements InvitationService {

  @Autowired
  InvitationDAO invitationDAO;

  @Value("${authenticationServer}")
  private String authenticationServerPath;

  private final RestTemplate restTemplate = new RestTemplate();


  @Override
  public void createInvitation(CreateInvitationPo po) throws BadRequestException {
    log.info("auth path:" + authenticationServerPath);
    log.info("invitee email: " + po.getInviteeEmail());
    verifyEmailIsExist(po.getInviteeEmail());
    verifyInvitationNotExist(po);
    po.setInviterEmail(getUserInfoByToken(po.getAuthorization()).getEmail());
    invitationDAO.createInvitation(po);
  }

  private void verifyEmailIsExist(String email) throws BadRequestException {
    String url = "http://localhost:8080/api/auth/user/email?email=" + email;
    log.info("請求驗證email的路徑：" + url);
    Map<String, Object> map = new HashMap<>();
    log.info("驗證invitee email: " + email);
    map.put("email", email);
    Boolean checkResponse = restTemplate.getForObject(url,Boolean.class, map);
    if (Boolean.FALSE.equals(checkResponse)) {
      throw new BadRequestException("Invalid email address");
    }
  }

  private void verifyInvitationNotExist(CreateInvitationPo po) {
    List<Integer> list = invitationDAO.checkInvitationNotExist(po);
    if (list != null) {
      throw new InvitationAlreadyExistException("Duplicate Invitation");
    }
  }

  private UserInfo getUserInfoByToken(String authorization) {
    String url = "http://" + authenticationServerPath + "/api/user/info";
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", authorization);
    HttpEntity<String> httpEntity = new HttpEntity<>(headers);
    UserInfo userInfo = restTemplate.exchange(url, HttpMethod.GET, httpEntity, UserInfo.class)
        .getBody();
    return userInfo;
  }

  @Override
  public GetSentInvitationResponsePo getSentInvitation(GetInvitationRequestPo po) {
    po.setLimit(po.getLimit() + 1);
    po.setUserEmail(getUserInfoByToken(po.getAuthorization()).getEmail());
    List<SentInvitationDTO> list = invitationDAO.getSentInvitation(po);
    GetSentInvitationResponsePo responsePo = new GetSentInvitationResponsePo();
    responsePo.setInvitations(list);
    setResponsePo(list, po.getLimit(), responsePo);
    return responsePo;
  }

  @Override
  public GetReceivedInvitationResponsePo getReceivedInvitation(GetInvitationRequestPo po) {
    po.setLimit(po.getLimit() + 1);
    po.setUserEmail(getUserInfoByToken(po.getAuthorization()).getEmail());
    List<ReceivedInvitationDTO> list = invitationDAO.getReceivedInvitation(po);
    GetReceivedInvitationResponsePo responsePo = new GetReceivedInvitationResponsePo();
    responsePo.setInvitations(list);
    setResponsePo(list, po.getLimit(), responsePo);
    return responsePo;
  }

  private void setResponsePo(List list, Integer limit, InvitationResponsePo po) {
    if ((list.size()) == limit) {
      list.remove(list.size() -1);
      po.setNextPage(true);
    } else {
      po.setNextPage(false);
    }
  }


  @Override
  public void deleteInvitation(DeleteInvitationPo po) {
    invitationDAO.deleteInvitation(po);
  }


}
