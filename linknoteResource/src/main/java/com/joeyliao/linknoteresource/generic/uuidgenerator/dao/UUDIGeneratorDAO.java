package com.joeyliao.linknoteresource.generic.uuidgenerator.dao;

import com.joeyliao.linknoteresource.generic.enums.Target;
import java.util.List;

public interface UUDIGeneratorDAO {

  List<String> checkUUIDDoesNotExist(Target target);
}
