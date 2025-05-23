package WebTech.WebTech.repository;

import WebTech.WebTech.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;    
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {
    List<Comment> findByProduct_Id(long productId);
    List<Comment> findByUser_Id(long userId);
    boolean existsByProduct_IdAndUser_Id(long productId, long userId);
    void deleteByProduct_IdAndUser_Id(long productId, long userId);
    void deleteByProduct_Id(long productId);
    List<Comment> findByProduct_IdAndRatingNot(long productId, int i);
}
