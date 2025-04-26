package WebTech.WebTech.domain;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name="receipt")
public class Receipt {
    @Id
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String date;
    @OneToMany(mappedBy = "receipt", fetch = FetchType.LAZY)
    private List<ReceiptDetail> receiptDetails;
    
}
