package com.joeyliao.linknoteresource.notebook.controller;

import com.joeyliao.linknoteresource.notebook.service.NotebookService;
import com.joeyliao.linknoteresource.po.GetNotebooksParamsDto;
import com.joeyliao.linknoteresource.po.NotebooksResPO;
import jakarta.validation.Valid;
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

  @GetMapping("/api/notebooks")
  public ResponseEntity<String> getNotebooks(
  ) {
//
//    GetNotebooksParamsDto params = new GetNotebooksParamsDto();
//    params.setLimit(limit);
//    params.setOffset(offset);
//    params.setKeyword(keyword);
//    params.setCoNotebook(coNotebook);
    return ResponseEntity.status(HttpStatus.OK).body("/api/notebooks測試成功");
  }

  @GetMapping("/api/notebooks/{notebookId}")
  public ResponseEntity<String> getNotebook(
      @PathVariable String notebookId
  ) {
    log.info("顯示path變數notebookId：" + notebookId);
//
//    GetNotebooksParamsDto params = new GetNotebooksParamsDto();
//    params.setLimit(limit);
//    params.setOffset(offset);
//    params.setKeyword(keyword);
//    params.setCoNotebook(coNotebook);
    return ResponseEntity.status(HttpStatus.OK).body("/api/notebooks/{notebookId}測試成功");
  }
//
//  @GetMapping("/api/notebooks/{notebookId}/notes")
//  public ResponseEntity<Object> getNotes(
//      @RequestHeader String Authorization,
//      @PathVariable Integer notebookId,
//      @RequestParam(defaultValue = "0") @Min(0) Integer offset,
//      @RequestParam(defaultValue = "1") @Max(20) @Min(0) Integer limit,
//      @RequestParam(defaultValue = "null") String tag,
//      @RequestParam(defaultValue = "false") Boolean star,
//      @RequestParam(defaultValue = "false") Boolean timeAsc,
//      @RequestParam(defaultValue = "null") String keyword
//  ){
//    GetNotesParamDto param = new GetNotesParamDto();
//    param.setUserId(tokenService.parserJWTToken(Authorization).get("userId", Integer.class));
//    param.setNotebookId(notebookId);
//    param.setOffset(offset);
//    param.setLimit(limit);
//    param.setTag(tag);
//    param.setStar(star);
//    param.setTimeAsc(timeAsc);
//    param.setKeyword(keyword);
//    return ResponseEntity.status(200).body(Map.of("result", true, "notes", notebookService.getNotes(param)));
//  }
//
//  @GetMapping("/api/notebooks/{notebookId}/tags")
//  public ResponseEntity<Object> getNotebookTags(
//      @RequestHeader String Authorization,
//      @PathVariable Integer notebookId
//  ){
//    GetTagsParamDto params = new GetTagsParamDto();
//    params.setNotebookId(notebookId);
//    params.setUserId(tokenService.parserJWTToken(Authorization).get("userId", Integer.class));
//    return ResponseEntity.status(200).body(notebookService.getNotebookTags(params));
//  }
//
//  @GetMapping("/api/notebooks/{notebookId}/collaborators")
//  public ResponseEntity<Object> getCollaborators(
//      @RequestHeader String Authorization,
//      @PathVariable Integer notebookId
//  ){
//    GetCollaboratorParamDto params = new GetCollaboratorParamDto();
//    params.setNotebookId(notebookId);
//    params.setUserId(tokenService.parserJWTToken(Authorization).get("userId", Integer.class));
//    return ResponseEntity.status(200).body(Map.of("result", true, "collaborators", notebookService.getCollaborators(params)));
//  }
//  //新增筆記本
//  @PostMapping("/api/notebooks")
//  public ResponseEntity<Object> createNotebook(
//      @RequestBody @Valid CreateNotebookParamsDto params,
//      @RequestHeader String Authorization
//  ){
//    if(params.getEmails().size() > 4){
//      return ResponseEntity.status(400).body(Map.of("result", false, "msg", "collaborators最多四人"));
//    }
//    params.setUserId(tokenService.parserJWTToken(Authorization).get("userId", Integer.class));
//    Integer notebookId = notebookService.createNotebook(params);
//    return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("result", true,"notebookId", notebookId));
//  }
//
//  //新增notebook tag
//  @PostMapping("/api/notebooks/{notebookId}/tags")
//  public ResponseEntity<Object> createNotebookTag(
//      @RequestBody @Valid CreateNotebookTagsParamsDto params,
//      @PathVariable Integer notebookId,
//      @RequestHeader String Authorization
//  ){
//    Integer userId = tokenService.parserJWTToken(Authorization).get("userId", Integer.class);
//    Integer tagId = notebookService.createNotebookTag(params.getTag(), notebookId, userId);
//    return ResponseEntity.status(201).body(Map.of("result", true, "tagId", tagId));
//  }
//
//  //新增協作者
//  @PostMapping("/api/notebooks/{notebookId}/collaborators")
//  public ResponseEntity<Object> createCollaborator(
//      @PathVariable Integer notebookId,
//      @RequestHeader String Authorization,
//      @RequestBody @Valid CreateCollaboratorParamsDto params
//  ){
//    Integer userId = tokenService.parserJWTToken(Authorization).get("userId", Integer.class);
//    params.setUserId(userId);
//    params.setNotebookId(notebookId);
//    Map<String, Object> collaborator = notebookService.createCollaborator(params);
//    return ResponseEntity.status(201).body(Map.of("result", true, "collaborator", collaborator));
//  }
//
//  //更新notebook name
//  @PutMapping("/api/notebooks/{notebookId}")
//  public ResponseEntity<Object> updateNotebook(
//      @PathVariable Integer notebookId,
//      @RequestBody @Valid UpdateNotebookParamDto params,
//      @RequestHeader String Authorization
//  ){
//    //檢查使用者是否沒有傳遞任何參數，如果是就不更新內容
//    if(params.getName().isEmpty() && params.getDescription().isEmpty()){
//      return ResponseEntity.status(200).body(Map.of("result", true));
//    }
//    params.setUserId(tokenService.parserJWTToken(Authorization).get("userId", Integer.class));
//    params.setNotebookId(notebookId);
//    notebookService.updateNotebook(params);
//    return ResponseEntity.status(200).body(Map.of("result", true));
//  }
//
//
//  @DeleteMapping("/api/notebooks/{notebookId}")
//  public ResponseEntity<Object> deleteNotebook(
//      @PathVariable Integer notebookId,
//      @RequestHeader String Authorization
//  ){
//    Integer userId = tokenService.parserJWTToken(Authorization).get("userId", Integer.class);
//    DeleteNotebookParamsDto params = new DeleteNotebookParamsDto();
//    params.setNotebookId(notebookId);
//    params.setUserId(userId);
//    notebookService.deleteNotebook(params);
//    return ResponseEntity.status(200).body(Map.of("result", true));
//  }
//
//  @DeleteMapping("/api/notebooks/{notebookId}/tags")
//  public ResponseEntity<Object> deleteNotebookTag(
//      @PathVariable Integer notebookId,
//      @RequestParam String tag,
//      @RequestHeader String  Authorization
//  ){
//    Integer userId = tokenService.parserJWTToken(Authorization).get("userId", Integer.class);
//    DeleteNotebookTagParamDto param = new DeleteNotebookTagParamDto();
//    param.setTag(tag);
//    param.setNotebookId(notebookId);
//    param.setUserId(userId);
//    notebookService.deleteNotebookTag(param);
//    return ResponseEntity.status(200).body(Map.of("result", true));
//  }
//
//  //刪除共編
//  @DeleteMapping("/api/notebooks/{notebookId}/collaborators/{collaboratorId}")
//  public ResponseEntity<Object> deleteCollaborators(
//      @PathVariable Integer notebookId,
//      @PathVariable Integer collaboratorId,
//      @RequestHeader String Authorization
//  ){
//
//    Integer userId = tokenService.parserJWTToken(Authorization).get("userId", Integer.class);
//    DeleteCollaboratorsParamDto params = new DeleteCollaboratorsParamDto();
//    params.setCollaboratorId(collaboratorId);
//    params.setUserId(userId);
//    params.setNotebookId(notebookId);
//    notebookService.deleteCollaborators(params);
//    return ResponseEntity.status(200).body(Map.of("result", true));
//  }

}
