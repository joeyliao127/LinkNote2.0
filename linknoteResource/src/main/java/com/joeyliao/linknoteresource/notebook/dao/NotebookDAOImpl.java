package com.joeyliao.linknoteresource.notebook.dao;

import com.joeyliao.linknoteresource.notebook.dto.NotebooksDTO;
import com.joeyliao.linknoteresource.notebook.po.AllNotebookRequestPo;
import com.joeyliao.linknoteresource.notebook.po.CreateNotebookRequestPo;
import com.joeyliao.linknoteresource.notebook.po.UpdateNotebookPo;
import com.joeyliao.linknoteresource.notebook.rowmapper.AllNotebooksRowMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class NotebookDAOImpl implements NotebookDAO {

  @Autowired
  NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public Integer createNotebook(CreateNotebookRequestPo po, String id) {
    String sql = """
        INSERT INTO notebooks(id, name, description, userId)
        VALUES (:id ,:name, :description, :userId);
        """;
    Map<String, Object> map = new HashMap<>();
    map.put("id", id);
    map.put("name", po.getName());
    map.put("description", po.getDescription());
    map.put("userId", po.getUserId());
    return namedParameterJdbcTemplate.update(sql, map);
  }

  @Override
  public List<NotebooksDTO> getAllNotebooks(AllNotebookRequestPo po) {
    return getNotebooksCollation(po, false);
  }

  @Override
  public List<NotebooksDTO> getAllCoNotebook(AllNotebookRequestPo po) {
    return getNotebooksCollation(po, true);
  }

  @Override
  public Integer updateNotebook(UpdateNotebookPo po) {
    return null;
  }

  @Override
  public Integer deleteNotebook(String notebookId) {
    return null;
  }

  private List<NotebooksDTO> getNotebooksCollation(AllNotebookRequestPo po, Boolean isCoNotebook) {
    String sql = null;
    if (isCoNotebook) {
      sql = """
          SELECT n.id, n.name, n.description FROM notebooks n
          JOIN notebooks_users_role nur ON n.id = nur.notebookId
          WHERE nur.userId = :userId 
          """;

    } else {
      sql = """
          SELECT n.id, n.name, n.description FROM notebooks n
          WHERE n.id = :notebookId 
          """;
    }
    if (!Objects.equals(po.getKeyword(), "null")) {
      sql += "name like %:keyword% ";
    }
    sql += "LIMIT :limit OFFSET :offset ";

    Map<String, Object> map = new HashMap<>();
    map.put("userId", po.getUserId());
    map.put("offset", po.getOffset());
    map.put("limit", po.getLimit() + 1);

    return namedParameterJdbcTemplate.query(sql, map, new AllNotebooksRowMapper());
  }
}
