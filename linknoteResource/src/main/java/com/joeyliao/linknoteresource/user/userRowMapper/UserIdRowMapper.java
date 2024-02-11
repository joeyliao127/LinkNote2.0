package com.joeyliao.linknoteresource.user.userRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserIdRowMapper implements RowMapper<String> {

  @Override
  public String mapRow(ResultSet rs, int rowNum) throws SQLException {
    return rs.getString("id");
  }
}
