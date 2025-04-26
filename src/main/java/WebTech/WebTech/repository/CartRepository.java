package WebTech.WebTech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import WebTech.WebTech.domain.Cart;
import java.util.List;
@Repository
public interface CartRepository extends JpaRepository<Cart, Long>, JpaSpecificationExecutor<Cart> {
    List<Cart> findByIdIn(List<Long> id);
    public Cart findById(long id);
    public Cart findByUserId(long idUser);
    
}
