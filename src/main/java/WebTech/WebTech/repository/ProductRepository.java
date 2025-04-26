package WebTech.WebTech.repository;
import WebTech.WebTech.domain.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product>{
    List<Product> findByIdIn(List<Long> id);
    public List<Product> findByCategory_Id(long categoryId);
    public List<Product> findByShop_Id(long shopId);
    public boolean existsByProductName(String name);
    public Product findByProductName(String name);
    public List<Product> findAll();
    List<Product> findByShopId(long shopId);
    List<Product> findByCategoryId(long categoryId);
}