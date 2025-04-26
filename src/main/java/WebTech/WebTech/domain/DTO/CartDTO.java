package WebTech.WebTech.domain.DTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartDTO {
    private long id;
    private long userId;
}
