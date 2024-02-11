package com.joeyliao.linknoteresource.note.po;

import com.joeyliao.linknoteresource.note.dto.NoteDTO;
import java.util.List;
import lombok.Data;

@Data
public class GetNotesResponsePo {
  List<NoteDTO> notes;
  Boolean nextPage;
}
