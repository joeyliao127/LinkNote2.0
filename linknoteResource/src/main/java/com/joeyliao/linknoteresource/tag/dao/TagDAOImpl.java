package com.joeyliao.linknoteresource.tag.dao;

import com.joeyliao.linknoteresource.tag.TagsRowMapper;
import com.joeyliao.linknoteresource.tag.dto.TagDTO;
import com.joeyliao.linknoteresource.tag.po.CreateNoteTagRequestPo;
import com.joeyliao.linknoteresource.tag.po.CreateNotebookTagRequestPo;
import com.joeyliao.linknoteresource.tag.po.DeleteNoteTagRequestPo;
import com.joeyliao.linknoteresource.tag.po.DeleteNotebookTagRequestPo;
import com.joeyliao.linknoteresource.tag.po.GetNoteTagsRequestPo;
import com.joeyliao.linknoteresource.tag.po.GetTagResponsePo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TagDAOImpl implements TagDAO {

  @Autowired
  NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public void createNotebookTag(CreateNotebookTagRequestPo po) {
    String sql = """
        INSERT INTO tags(id, name, notebookId)
        VALUES (:id, :name, :notebookId);
        """;
    Map<String, String> map = new HashMap<>();
    map.put("id", po.getTagId());
    map.put("name", po.getName());
    map.put("notebookId", po.getNotebookId());
    namedParameterJdbcTemplate.update(sql, map);
  }

  @Override
  public void createNoteTag(CreateNoteTagRequestPo po) {
    String sql = """
        INSERT INTO notes_tags(noteId, tagId)
        VALUES (:noteId, :tagId);
        """;
    Map<String, String> map = new HashMap<>();
    map.put("noteId", po.getNoteId());
    map.put("tagId", po.getTagId());
    namedParameterJdbcTemplate.update(sql, map);
  }

  @Override
  public List<TagDTO> getNotebookTags(String notebookId) {
    String sql = """
        SELECT t.id, t.name FROM tags t
        JOIN notebooks n ON t.notebookId = n.id
        WHERE t.notebookId = :notebookId
        """;
    Map<String, String> map = new HashMap<>();
    map.put("notebookId", notebookId);
    return namedParameterJdbcTemplate.query(sql, map, new TagsRowMapper());
  }

  @Override
  public List<TagDTO> getNoteTags(String noteId) {
    String sql = """
        SELECT t.id, t.name FROM tags t
        JOIN notes_tags nt ON nt.tagId = t.id
        JOIN notes n ON nt.noteId = n.id
        WHERE n.id = :noteId
        """;
    Map<String, String> map = new HashMap<>();
    map.put("noteId", noteId);
    return namedParameterJdbcTemplate.query(sql, map, new TagsRowMapper());
  }

  @Override
  public void deleteNoteTag(DeleteNoteTagRequestPo po) {

  }

  @Override
  public void deleteNotebookTag(DeleteNotebookTagRequestPo po) {

  }
}
