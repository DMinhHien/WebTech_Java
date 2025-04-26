package WebTech.WebTech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import WebTech.WebTech.domain.Category;
import java.util.List;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
    List<Category> findByIdIn(List<Long> id);
    public List<Category> findByName(String name);
    public boolean existsByName(String name);
    public Category findByNameAndIdNot(String name, long id);
    public  Category findById(long id);
}
