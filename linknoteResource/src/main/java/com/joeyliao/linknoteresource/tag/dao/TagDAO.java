package com.joeyliao.linknoteresource.tag.dao;

import com.joeyliao.linknoteresource.tag.po.CreateNoteTagRequestPo;
import com.joeyliao.linknoteresource.tag.po.CreateNotebookTagRequestPo;
import com.joeyliao.linknoteresource.tag.po.DeleteNoteTagRequestPo;
import com.joeyliao.linknoteresource.tag.po.DeleteNotebookTagRequestPo;
import com.joeyliao.linknoteresource.tag.po.GetTagRequestPo;
import com.joeyliao.linknoteresource.tag.po.GetTagResponsePo;

public interface TagDAO {

  GetTagResponsePo getTags(GetTagRequestPo po);

  void createNotebookTag(CreateNotebookTagRequestPo po);

  void createNoteTag(CreateNoteTagRequestPo po);

  void deleteNoteTag(DeleteNoteTagRequestPo po);

  void deleteNotebookTag(DeleteNotebookTagRequestPo po);
}
