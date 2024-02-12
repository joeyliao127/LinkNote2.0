package com.joeyliao.linknoteresource.note.po;

import com.joeyliao.linknoteresource.note.dto.NoteDTO;
import java.util.List;
import lombok.Data;

@Data
public class GetNotesResponsePo {
  private List<NoteDTO> notes;
  private Boolean nextPage;
}
