package WebTech.WebTech.domain;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="receipt_detail")
public class ReceiptDetail 
{
    @Id
    private long id;
    @ManyToOne
    @JoinColumn(name = "id_receipt")
    private Receipt receipt;
    @OneToOne
    @JoinColumn(name = "id_product")
    private Product product;
    private int quantity;
}
