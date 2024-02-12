package com.joeyliao.linknoteresource.tag.service;

import com.joeyliao.linknoteresource.tag.dao.TagDAO;
import com.joeyliao.linknoteresource.tag.po.CreateNoteTagRequestPo;
import com.joeyliao.linknoteresource.tag.po.CreateNotebookTagRequestPo;
import com.joeyliao.linknoteresource.tag.po.DeleteNoteTagRequestPo;
import com.joeyliao.linknoteresource.tag.po.DeleteNotebookTagRequestPo;
import com.joeyliao.linknoteresource.tag.po.GetTagRequestPo;
import com.joeyliao.linknoteresource.tag.po.GetTagResponsePo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TagServiceImpl implements TagService {

  @Autowired
  TagDAO tagDAO;

  @Override
  public GetTagResponsePo getTags(GetTagRequestPo po) {
    return tagDAO.getTags(po);
  }

  @Override
  public void createNotebookTag(CreateNotebookTagRequestPo po) {
    tagDAO.createNotebookTag(po);
  }

  @Override
  public void createNoteTag(CreateNoteTagRequestPo po) {
    tagDAO.createNoteTag(po);
  }

  @Override
  public void deleteNoteTag(DeleteNoteTagRequestPo po) {
    tagDAO.deleteNoteTag(po);
  }

  @Override
  public void deleteNotebookTag(DeleteNotebookTagRequestPo po) {
    tagDAO.deleteNotebookTag(po);
  }
}
