package com.joeyliao.linknoteresource.generic.uuidgenerator.service;

import com.joeyliao.linknoteresource.generic.enums.Target;
import com.joeyliao.linknoteresource.generic.uuidgenerator.dao.UUDIGeneratorDAO;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UUIDGeneratorServiceImpl implements UUIDGeneratorService{

  @Autowired
  UUDIGeneratorDAO uudiGeneratorDAO;

  @Override
  public UUID generateUUID(Target target) {
    while (true){
      UUID uuid = UUID.randomUUID();
      List<String> uuidList = uudiGeneratorDAO.checkUUIDDoesNotExist(target);
      if(uuidList.isEmpty()){
        return uuid;
      }
    }
  }
}
