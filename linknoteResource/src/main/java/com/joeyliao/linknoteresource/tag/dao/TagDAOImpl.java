package com.joeyliao.linknoteresource.tag.dao;

import com.joeyliao.linknoteresource.tag.po.CreateNoteTagRequestPo;
import com.joeyliao.linknoteresource.tag.po.CreateNotebookTagRequestPo;
import com.joeyliao.linknoteresource.tag.po.DeleteNoteTagRequestPo;
import com.joeyliao.linknoteresource.tag.po.DeleteNotebookTagRequestPo;
import com.joeyliao.linknoteresource.tag.po.GetTagRequestPo;
import com.joeyliao.linknoteresource.tag.po.GetTagResponsePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TagDAOImpl implements TagDAO {

  @Autowired
  NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Override
  public GetTagResponsePo getTags(GetTagRequestPo po) {
    return null;
  }

  @Override
  public void createNotebookTag(CreateNotebookTagRequestPo po) {

  }

  @Override
  public void createNoteTag(CreateNoteTagRequestPo po) {

  }

  @Override
  public void deleteNoteTag(DeleteNoteTagRequestPo po) {

  }

  @Override
  public void deleteNotebookTag(DeleteNotebookTagRequestPo po) {

  }
}
