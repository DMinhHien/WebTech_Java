package WebTech.WebTech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import WebTech.WebTech.domain.ReceiptDetail;
import java.util.List;
@Repository
public interface ReceiptDetailRepository extends JpaRepository<ReceiptDetail, Long>, JpaSpecificationExecutor<ReceiptDetail> {
    List<ReceiptDetail> findByReceipt_Id(long idReceipt);
    List<ReceiptDetail> findByProduct_Id(long idProduct);
    void deleteByReceipt_Id(long idReceipt); 
    
}
