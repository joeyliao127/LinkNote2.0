package com.joeyliao.linknoteresource.generic.uuidgenerator.dao;

import com.joeyliao.linknoteresource.generic.enums.Target;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class UUIDGeneratorDAOImpl implements UUDIGeneratorDAO {

  @Override
  public List<String> checkUUIDDoesNotExist(Target target) {
    String targetToString = targetMap(target);
    String sql = "SELECT";
    return null;
  }

  private String targetMap(Target target){
    Map<Target, String > map = new HashMap<>();
    map.put(Target.NOTEBOOK, "Notebooks");
    map.put(Target.NOTE, "Notes");
    map.put(Target.TAG, "Tags");
    return map.get(target);
  }
}
