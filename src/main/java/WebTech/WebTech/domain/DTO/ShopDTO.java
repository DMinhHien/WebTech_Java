package WebTech.WebTech.domain.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
// @NoArgsConstructor
// @AllArgsConstructor
public class ShopDTO {
    private long id;
    private String name;
    private String address;
    private String image;
    private Long userId;
    private String userName; // Assuming the shop is associated with a user
    private double rating; // Assuming the shop has a rating
}
