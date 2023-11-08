package sebastien.andreu.spring.repository;

import sebastien.andreu.spring.entity.List;
import org.springframework.data.repository.CrudRepository;

public interface ListRepository extends CrudRepository<List, Long> {
}