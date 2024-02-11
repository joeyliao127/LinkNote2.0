package com.joeyliao.linknoteresource.notebook.controller;

import com.joeyliao.linknoteresource.notebook.po.AllNotebookRequestPo;
import com.joeyliao.linknoteresource.notebook.po.CreateNotebookRequestPo;
import com.joeyliao.linknoteresource.notebook.po.UpdateNotebookPo;
import com.joeyliao.linknoteresource.notebook.service.NotebookService;
import com.joeyliao.linknoteresource.token.service.TokenService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class NotebookController {

  @Autowired
  NotebookService notebookService;

  @Autowired
  TokenService tokenService;

  @PostMapping("/api/notebooks")
  public ResponseEntity<Object> createNotebook(
      @RequestHeader String Authorization,
      @RequestBody CreateNotebookRequestPo po
  ) {
    notebookService.createNotebook(po, Authorization);
    return ResponseEntity.status(200).body(Map.of("result", true));
  }

  @GetMapping("/api/notebooks")
  public ResponseEntity<Object> getNotebooks(
      @RequestHeader String Authorization,
      @RequestParam(defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(defaultValue = "1") @Max(20) @Min(0) Integer limit,
      @RequestParam(defaultValue = "null") String keyword
  ) {
    AllNotebookRequestPo po = setAllNotebookPoParams(Authorization, offset, limit, keyword);
    return ResponseEntity.status(HttpStatus.OK)
        .body(Map.of("result", true,
            "notebooks", notebookService.getAllNotebooks(po)));
  }

  @GetMapping("/api/coNotebooks")
  public ResponseEntity<Object> getCoNotebooks(
      @RequestHeader String Authorization,
      @RequestParam(defaultValue = "0") @Min(0) Integer offset,
      @RequestParam(defaultValue = "1") @Max(20) @Min(0) Integer limit,
      @RequestParam(defaultValue = "null") String keyword
  ) {

    AllNotebookRequestPo po = setAllNotebookPoParams(Authorization, offset, limit, keyword);
    return ResponseEntity.status(200).body(Map.of(
            "result", true,
            "notebooks", notebookService.getCoNotebooks(po)
        )
    );
  }

  @PutMapping("/api/notebooks/{notebookId}")
  public ResponseEntity<Object> updateNotebook(
      @PathVariable String notebookId,
      @RequestBody UpdateNotebookPo po
  ) {
    notebookService.updateNotebook(po);
    return ResponseEntity.status(200).body(Map.of("result", true));
  }

  @DeleteMapping("/api/notebook/{notebookId}")
  public ResponseEntity<Object> deleteNotebook(
      @PathVariable String notebookId
  ) {
    notebookService.deleteNotebook(notebookId);
    return ResponseEntity.status(200).body(Map.of("result", true));
  }

  private AllNotebookRequestPo setAllNotebookPoParams(
      String Authorization,
      Integer offset,
      Integer limit,
      String keyword) {
    String userId = tokenService.parserJWTToken(Authorization).get("userId", String.class);
    AllNotebookRequestPo params = new AllNotebookRequestPo();
    params.setUserId(userId);
    params.setLimit(limit);
    params.setOffset(offset);
    params.setUserId(userId);
    params.setKeyword(keyword);
    return params;
  }
}
