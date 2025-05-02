package WebTech.WebTech.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import WebTech.WebTech.domain.Comment;
import WebTech.WebTech.domain.DTO.CommentDTO;
import WebTech.WebTech.service.CommentService;
@RestController
public class CommentController {
    private final CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping("/Comments/create")
    public void createComment(@RequestBody Comment comment) {
        commentService.createComment(comment);
    } 
    @PostMapping("/Comments/delete")
    public void deleteComment(@RequestBody Long id) {
        commentService.deleteComment(id);
    }
    @PostMapping("/Comments/edit")
    public void updateComment(@RequestBody Comment comment) {
        commentService.updateComment(comment);
    }
    @PostMapping("/Comments/getListUse")
    public Comment getListUse(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }
    
}
