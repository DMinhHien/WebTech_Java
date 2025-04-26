package WebTech.WebTech.domain.DTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartDetailDTO {
    private long id;
    private long cartId;
    private long productId;
    private int quantity;
}
