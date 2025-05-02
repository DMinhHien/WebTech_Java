package WebTech.WebTech.domain.DTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShopDTO {
    private long id;
    private String name;
    private String address;
    private String image;
    private String userName; // Assuming the shop is associated with a user
    private double rating; // Assuming the shop has a rating
}
