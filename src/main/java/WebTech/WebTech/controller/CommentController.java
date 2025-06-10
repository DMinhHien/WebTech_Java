package WebTech.WebTech.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
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
    @PostMapping("/Comment/create")
    public void createComment(@RequestBody CommentDTO commentDTO) {
        commentService.createComment(commentDTO);
    }
    @PostMapping("/Comment/delete/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }
    @PostMapping("/Comment/edit")
    public void updateComment(@RequestBody CommentDTO commentDTO) {
        commentService.updateComment(commentDTO);
    }
    @GetMapping("/Comment/getListUse/{id}")
    public List<CommentDTO> getListUse(@PathVariable Long id) {
        return commentService.getCommentsByProductId(id);
    }
    
}
