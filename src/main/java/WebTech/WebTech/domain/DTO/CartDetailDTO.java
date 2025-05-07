package WebTech.WebTech.domain.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDetailDTO {
    private long id;
    private long idCart;
    private long idProduct;
    private int quantity;
}
