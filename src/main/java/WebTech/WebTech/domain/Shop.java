package WebTech.WebTech.domain;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="shop")
public class Shop {
    @Id
    private long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String name;
    private String address;
    private double rating;
    private String image;
    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
    private List<Product> products;
}
