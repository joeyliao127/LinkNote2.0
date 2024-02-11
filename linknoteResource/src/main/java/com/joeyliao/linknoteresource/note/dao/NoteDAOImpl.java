package com.joeyliao.linknoteresource.note.dao;

import com.joeyliao.linknoteresource.note.dto.NoteDTO;
import com.joeyliao.linknoteresource.note.po.CreateNotePo;
import com.joeyliao.linknoteresource.note.po.DeleteNotePo;
import com.joeyliao.linknoteresource.note.po.GetNoteRequestPo;
import com.joeyliao.linknoteresource.note.po.GetNotesRequestPo;
import com.joeyliao.linknoteresource.note.po.GetNotesResponsePo;
import com.joeyliao.linknoteresource.note.po.updateNotePo;
import org.springframework.stereotype.Repository;

@Repository
public class NoteDAOImpl implements NoteDAO {

  @Override
  public void createNotes(CreateNotePo po) {

  }

  @Override
  public GetNotesResponsePo getNotes(GetNotesRequestPo po) {
    return null;
  }

  @Override
  public NoteDTO getNote(GetNoteRequestPo po) {
    return null;
  }

  @Override
  public void updateNote(updateNotePo po) {

  }

  @Override
  public void deleteNote(DeleteNotePo po) {

  }
}
