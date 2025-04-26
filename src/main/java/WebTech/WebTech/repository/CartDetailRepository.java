package WebTech.WebTech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import WebTech.WebTech.domain.CartDetail;
import java.util.List;
@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long>, JpaSpecificationExecutor<CartDetail> {
    List<CartDetail> findByCart_Id(long idCart);
    List<CartDetail> findByProduct_Id(long idProduct);
    

}
