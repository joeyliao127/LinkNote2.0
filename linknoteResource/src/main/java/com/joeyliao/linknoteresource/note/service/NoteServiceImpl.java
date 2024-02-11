package com.joeyliao.linknoteresource.note.service;

import com.joeyliao.linknoteresource.note.dao.NoteDAO;
import com.joeyliao.linknoteresource.note.po.CreateNotePo;
import com.joeyliao.linknoteresource.note.po.DeleteNotePo;
import com.joeyliao.linknoteresource.note.po.GetNoteRequestPo;
import com.joeyliao.linknoteresource.note.po.GetNoteResponsePo;
import com.joeyliao.linknoteresource.note.po.GetNotesRequestPo;
import com.joeyliao.linknoteresource.note.po.GetNotesResponsePo;
import com.joeyliao.linknoteresource.note.po.updateNotePo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NoteServiceImpl implements NoteService{

  @Autowired
  NoteDAO noteDAO;

  @Override
  public void createNote(CreateNotePo po) {
    noteDAO.createNotes(po);
  }

  @Override
  public GetNotesResponsePo getNotes(GetNotesRequestPo po) {
    return noteDAO.getNotes(po);
  }

  @Override
  public GetNoteResponsePo getNote(GetNoteRequestPo po) {
    GetNoteResponsePo responsePo = new GetNoteResponsePo();
    responsePo.setNote(noteDAO.getNote(po));
    return responsePo;
  }

  @Override
  public void updateNote(updateNotePo po) {
    noteDAO.updateNote(po);
  }

  @Override
  public void deleteNote(DeleteNotePo po) {
    noteDAO.deleteNote(po);
  }
}
