package com.joeyliao.linknoteresource.notebook.dao;

import com.joeyliao.linknoteresource.generic.enums.Role;
import com.joeyliao.linknoteresource.notebook.dto.NotebooksDTO;
import com.joeyliao.linknoteresource.notebook.po.AllNotebookRequestPo;
import com.joeyliao.linknoteresource.notebook.po.CreateNotebookRequestPo;
import com.joeyliao.linknoteresource.notebook.po.UpdateNotebookPo;
import com.joeyliao.linknoteresource.notebook.rowmapper.AllNotebooksRowMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class NotebookDAOImpl implements NotebookDAO {

  @Autowired
  NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public void createNotebook(CreateNotebookRequestPo po, String id) {
    String sql = """
        INSERT INTO notebooks(id, name, description, userId)
        VALUES (:id ,:name, :description, :userId);
        """;
    Map<String, Object> map = new HashMap<>();
    map.put("id", id);
    map.put("name", po.getName());
    map.put("description", po.getDescription());
    map.put("userId", po.getUserId());
    namedParameterJdbcTemplate.update(sql, map);
    setNotebookUserRoleAsOwner(id, po.getUserId());
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
    String sql = """
        UPDATE notebooks SET name = :name,description = :description
        WHERE id = :notebookId;
        """;
    Map<String, Object> map = new HashMap<>();
    map.put("notebookId", po.getNotebookId());
    map.put("name", po.getName());
    map.put("description", po.getDescription());
    return namedParameterJdbcTemplate.update(sql, map);
  }

  @Override
  public Integer deleteNotebook(String notebookId) {
    String sql = """
        DELETE FROM notebooks WHERE id = :id;
        """;
    Map<String, Object> map = new HashMap<>();
    map.put("id", notebookId);
    return namedParameterJdbcTemplate.update(sql, map);
  }

  private List<NotebooksDTO> getNotebooksCollation(AllNotebookRequestPo po, Boolean isCoNotebook) {
    String sql = null;
    if (isCoNotebook) {
      log.info("查詢coNotebooks");
      sql = """
          SELECT n.id, n.name, n.description FROM notebooks n 
          JOIN notebooks_users_role nur ON n.id = nur.notebookId 
          WHERE nur.userId = :userId 
          """;

    } else {
      sql = """
          SELECT n.id, n.name, n.description FROM notebooks n 
          JOIN users u ON n.userId = u.id  
          WHERE n.userId = :userId 
          """;
    }
    if (!Objects.equals(po.getKeyword(), "null")) {
      sql += "AND n.name like :keyword ";
    }
    sql += "LIMIT :limit OFFSET :offset";
    Map<String, Object> map = new HashMap<>();
    map.put("userId", po.getUserId());
    map.put("keyword", "%" + po.getKeyword() + "%");
    map.put("offset", po.getOffset());
    map.put("limit", po.getLimit() + 1);
    log.info("userId: " + po.getUserId());
    log.info("最終取得的sql:\n" + sql);
    List<NotebooksDTO> list = namedParameterJdbcTemplate.query(sql, map, new AllNotebooksRowMapper());
    log.info("list長度：" + list.size());
    log.info("list內容：" + list.get(0));
    return namedParameterJdbcTemplate.query(sql, map, new AllNotebooksRowMapper());
  }

  private void setNotebookUserRoleAsOwner(String notebookId, String userId){
    String sql = """
        INSERT INTO notebooks_users_role(notebookId, userId, roleId)
        VALUES (:notebookId, :userId, :role);
        """;
    Map<String, Object> map = new HashMap<>();
    map.put("notebookId", notebookId);
    map.put("userId", userId);
    map.put("role", roleMap(Role.OWNER));
    namedParameterJdbcTemplate.update(sql, map);
  }

  private Integer roleMap(Role role){
    Map<Role, Integer> map = new HashMap<>();
    map.put(Role.OWNER, 1);
    map.put(Role.COLLABORATOR, 2);
    map.put(Role.MEMBER, 3);
    map.put(Role.GUEST, 4);
    return map.get(role);
  }
}
