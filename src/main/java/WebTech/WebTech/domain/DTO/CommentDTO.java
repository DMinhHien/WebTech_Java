package WebTech.WebTech.domain.DTO;
import java.time.Instant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDTO {
    private long id;
    private String content;
    private double rating;
    private Instant date;
    private long productId; 
    private long userId;     
}
