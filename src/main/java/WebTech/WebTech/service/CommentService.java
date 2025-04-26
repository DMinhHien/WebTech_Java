package WebTech.WebTech.service;
import java.util.List;
import org.springframework.stereotype.Service;
import WebTech.WebTech.domain.Comment;
import WebTech.WebTech.repository.CommentRepository;
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getAllComments() {
        return this.commentRepository.findAll();
    }

    public Comment getCommentById(long id) {
        return this.commentRepository.findById(id).orElse(null);
    }

    public Comment createComment(Comment comment) {
        return this.commentRepository.save(comment);
    }

    public Comment updateComment(Comment comment) {
        return this.commentRepository.save(comment);
    }

    public void deleteComment(long id) {
        this.commentRepository.deleteById(id);
    }
    
}
