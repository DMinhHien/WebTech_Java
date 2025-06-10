package WebTech.WebTech.domain.DTO;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private long id;
    private String content;
    private double rating;
    private Instant date;
    private String username;
    private long productId; 
    private long userId;     
}
