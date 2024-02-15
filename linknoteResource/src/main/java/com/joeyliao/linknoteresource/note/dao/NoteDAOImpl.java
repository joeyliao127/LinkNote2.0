package com.joeyliao.linknoteresource.note.dao;

import com.joeyliao.linknoteresource.collaborator.po.NotebookOwnerDTO;
import com.joeyliao.linknoteresource.collaborator.rowmapper.NotebookOwnerRowMapper;
import com.joeyliao.linknoteresource.note.dto.NoteDTO;
import com.joeyliao.linknoteresource.note.po.CreateNotePo;
import com.joeyliao.linknoteresource.note.po.DeleteNotePo;
import com.joeyliao.linknoteresource.note.po.GetNoteRequestPo;
import com.joeyliao.linknoteresource.note.po.GetNotesRequestPo;
import com.joeyliao.linknoteresource.note.po.GetNotesResponsePo;
import com.joeyliao.linknoteresource.note.po.updateNotePo;
import com.joeyliao.linknoteresource.note.rowmapper.NotesRowMapper;
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
public class NoteDAOImpl implements NoteDAO {
  @Autowired
  NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public void createNotes(CreateNotePo po) {
  String sql = """
      INSERT INTO notes (id, notebookId) VALUES (:id, :notebookId);
      """;
    Map<String , Object> map = new HashMap<>();
    map.put("id", po.getNoteId());
    map.put("notebookId", po.getNotebookId());
    namedParameterJdbcTemplate.update(sql, map);
  }

  @Override
  public List<NoteDTO> getNotes(GetNotesRequestPo po) {
    log.info("notebookId = " + po.getNotebookId());
    String sql = """
        SELECT n.id, n.name, n.content, n.question
        , n.keypoint, n.star, n.createDate
        FROM notes n JOIN notebooks nb ON n.notebookId = nb.id 
        """;

    if(!(po.getTag().equals("null")) ){
      sql += """
      JOIN notes_tags nt ON n.id = nt.noteId
      JOIN tags t ON t.id = nt.tagId WHERE t.name = :tag AND nb.id = :notebookId
      """;
    }else{
      sql += "WHERE nb.id = :notebookId ";
    }

    if (!(po.getKeyword().equals("null"))) {
      sql += "AND n.name like :keyword ";
    }
//
    if(po.getStar().equals("true")){
      sql += "AND n.star = 1 ";
    }
//
    if(po.getSortDesc().equals("false")){
      sql += "ORDER BY n.createDate ASC ";
    }
//
    sql += "LIMIT :limit OFFSET :offset";


    Map<String, Object> map = new HashMap<>();
    map.put("notebookId", po.getNotebookId());
    map.put("offset", po.getOffset());
    map.put("limit", po.getLimit());
    map.put("tag", po.getTag());
    map.put("keyword", "%" + po.getKeyword() + "%");
    log.info("拼接完的sql:\n" + sql);
    log.info("%" + po.getKeyword() + "%");
    return namedParameterJdbcTemplate.query(sql, map, new NotesRowMapper());
  }

  @Override
  public NoteDTO getNote(GetNoteRequestPo po) {
    String sql = """
        SELECT n.id, n.name, n.content, n.question
        , n.keypoint, n.star, n.createDate
        FROM notes n WHERE n.id = :noteId;
        """;
    Map<String, Object> map = new HashMap<>();
    map.put("noteId", po.getNoteId());
    List<NoteDTO> list = namedParameterJdbcTemplate.query(sql ,map, new NotesRowMapper());
    if(list.isEmpty()){
      return null;
    }else {
      return list.get(0);
    }
  }

  @Override
  public void updateNote(updateNotePo po) {
    Map<String, Object> map = new HashMap<>();
    String sql = """
        UPDATE notes SET id = :id 
        """;
    map.put("id", po.getNoteId());

    if(po.getName() != null){
      sql += ",name = :name";
      map.put("name", po.getName());
    }
    if(po.getQuestion() != null){
      sql += ",question = :question ";
      map.put("question", po.getQuestion());
    }

    if(po.getKeypoint() != null){
      sql += ",keypoint = :keypoint ";
      map.put("keypoint", po.getKeypoint());
    }

    if(po.getContent() != null){
      sql += ",content = :content ";
      map.put("content", po.getContent());
    }

    if(po.getStar() != null){
      if(po.getStar()){
        sql += ",star = 1 ";
      }else{
        sql += ",star = 0 ";
      }
    }
    sql += "WHERE id = :noteId";
    map.put("noteId", po.getNoteId());
    namedParameterJdbcTemplate.update(sql, map);
  }

  @Override
  public void deleteNote(DeleteNotePo po) {
    String sql = """
       DELETE FROM notes WHERE id = :noteId
        """;
    Map<String, Object> map = new HashMap<>();
    map.put("noteId", po.getNoteId());
    namedParameterJdbcTemplate.update(sql, map);
  }


}
