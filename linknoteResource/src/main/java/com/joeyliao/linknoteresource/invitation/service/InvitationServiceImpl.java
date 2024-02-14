package com.joeyliao.linknoteresource.invitation.service;

import com.joeyliao.linknoteresource.invitation.dao.InvitationDAO;
import com.joeyliao.linknoteresource.invitation.dto.InvitationDTO;
import com.joeyliao.linknoteresource.invitation.dto.ReceivedInvitationDTO;
import com.joeyliao.linknoteresource.invitation.dto.SentInvitationDTO;
import com.joeyliao.linknoteresource.invitation.exception.InvitationAlreadyExistException;
import com.joeyliao.linknoteresource.invitation.po.CreateInvitationPo;
import com.joeyliao.linknoteresource.invitation.po.DeleteInvitationPo;
import com.joeyliao.linknoteresource.invitation.po.GetInvitationRequestPo;
import com.joeyliao.linknoteresource.invitation.po.GetReceivedInvitationResponsePo;
import com.joeyliao.linknoteresource.invitation.po.GetSentInvitationResponsePo;
import com.joeyliao.linknoteresource.invitation.service.InvitationService;
import com.joeyliao.linknoteresource.token.service.TokenService;
import com.joeyliao.linknoteresource.user.userdao.UserDAO;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InvitationServiceImpl implements InvitationService {
  @Autowired
  InvitationDAO invitationDAO;

  @Autowired
  TokenService tokenService;

  @Autowired
  UserDAO userDAO;

  @Override
  public void createInvitation(CreateInvitationPo po) {
    需要check Email是否存在。
    //回傳invitation id，如果有代表已經發出邀請。
    List<Integer> list = invitationDAO.checkInvitationNotExist(po.getInviteeId(), po.getNotebookId());
    if(!list.isEmpty()){
      throw new InvitationAlreadyExistException("已經存在重複的資料");
    }
    invitationDAO.createInvitation(po);
  }

  @Override
  public GetSentInvitationResponsePo getSentInvitation(GetInvitationRequestPo po) {
    tokenService.verifyToken(po.getAuthorization());
    List<SentInvitationDTO> list =  invitationDAO.getSentInvitation(po);
    GetSentInvitationResponsePo responsePo = new GetSentInvitationResponsePo();
    if(hasNextPage(list, po.getLimit())){
      list.remove(list.size() -1 );
      responsePo.setNextPage(true);
    }else{
      responsePo.setNextPage(false);
    }
    return responsePo;
  }

  @Override
  public GetReceivedInvitationResponsePo getReceivedInvitation(GetInvitationRequestPo po) {
    List<ReceivedInvitationDTO> list = invitationDAO.getReceivedInvitation(po);
    GetReceivedInvitationResponsePo responsePo = new GetReceivedInvitationResponsePo();
    if(hasNextPage(list, po.getLimit())){
      list.remove(list.size() -1 );
      responsePo.setNextPage(true);
    }else{
      responsePo.setNextPage(false);
    }
    return responsePo;
  }

  @Override
  public void deleteInvitation(DeleteInvitationPo po) {
    invitationDAO.deleteInvitation(po);
  }

  private Boolean hasNextPage(List list, Integer limit){
    if((list.size() - 1) == limit){
      return true;
    }else{
      return false;
    }
  }
}
