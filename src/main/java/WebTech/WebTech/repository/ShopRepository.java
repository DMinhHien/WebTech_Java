package WebTech.WebTech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import WebTech.WebTech.domain.Shop;
import java.util.List;
@Repository
public interface ShopRepository extends JpaRepository<Shop, Long>, JpaSpecificationExecutor<Shop> {
    List<Shop> findByIdIn(List<Long> id);
    public List<Shop> findByName(String name);
    public boolean existsByName(String name);
    public Shop findByNameAndIdNot(String name, long id);
    public Shop findById(long id);
    public Shop findByUser_Id(long userId);
    
}
