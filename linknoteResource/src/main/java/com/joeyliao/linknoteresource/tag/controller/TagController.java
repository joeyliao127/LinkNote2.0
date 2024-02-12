package com.joeyliao.linknoteresource.tag.controller;

import com.joeyliao.linknoteresource.tag.po.CreateNoteTagRequestPo;
import com.joeyliao.linknoteresource.tag.po.CreateNotebookTagRequestPo;
import com.joeyliao.linknoteresource.tag.po.DeleteNoteTagRequestPo;
import com.joeyliao.linknoteresource.tag.po.DeleteNotebookTagRequestPo;
import com.joeyliao.linknoteresource.tag.po.GetTagRequestPo;
import com.joeyliao.linknoteresource.tag.service.TagService;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagController {

  @Autowired
  TagService tagService;

  @PostMapping("/api/notebooks/{notebookId}/tags")
   public ResponseEntity<Object> createNotebookTag(
    @PathVariable String notebookId,
    @RequestBody @Valid CreateNotebookTagRequestPo po
  ) {
    po.setNotebookId(notebookId);
    tagService.createNotebookTag(po);
    return ResponseEntity.status(200).body(Map.of("result", true));
  }

  @PostMapping("/api/notebooks/{notebookId}/tags/{noteId}")
  public ResponseEntity<Object> createNoteTag(
      @PathVariable String notebookId,
      @RequestBody @Valid CreateNoteTagRequestPo po,
      @PathVariable String noteId) {
    po.setNoteId(noteId);
    tagService.createNoteTag(po);
    return ResponseEntity.status(200).body(Map.of("result", true));
  }

  @GetMapping("/api/notebooks/{notebookId}/notes/{noteId}/tags")
  public ResponseEntity<Object> getAllTags(
      @PathVariable String noteId
      ) {
    GetTagRequestPo po = new GetTagRequestPo();
    po.setNoteId(noteId);
    return ResponseEntity.status(200).body(tagService.getTags(po));
  }
  @DeleteMapping("/api/notebooks/{notebookId}/tags/{tagId}")
  public ResponseEntity<Object> deleteNotebookTag(
      @PathVariable String tagId,
      @PathVariable String notebookId) {
    DeleteNotebookTagRequestPo po = new DeleteNotebookTagRequestPo();
    po.setTagId(tagId);
    tagService.deleteNotebookTag(po);
    return ResponseEntity.status(200).body(Map.of("result", true));
  }

  @DeleteMapping("/api/notebooks/{notebookId}/notes/{noteId}/tags/{tagId}")
  public ResponseEntity<Object> deleteNoteTag(
      @PathVariable String tagId,
      @PathVariable String notebookId,
      @PathVariable String noteId) {
    DeleteNoteTagRequestPo po = new DeleteNoteTagRequestPo();
    po.setTagId(tagId);
    po.setNoteId(noteId);
    tagService.deleteNoteTag(po);
    return ResponseEntity.status(200).body(Map.of("result", true));
  }

}
