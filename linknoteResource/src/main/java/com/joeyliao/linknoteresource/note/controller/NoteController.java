package com.joeyliao.linknoteresource.note.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoteController {

  @GetMapping("/api/notebooks/{notebookId}/notes/{noteId}")
  public ResponseEntity<Object> getNoteByNoteId(
      @RequestHeader String Authorization,
      @PathVariable String notebookId,
      @PathVariable String noteId
  ){

   return ResponseEntity.status(200).body(Map.of("result","取得notes成功！"));
  }
}
