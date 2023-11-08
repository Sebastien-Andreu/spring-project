package sebastien.andreu.spring.repository;

import org.springframework.data.repository.CrudRepository;
import sebastien.andreu.spring.entity.Rank;

public interface RoleRepository extends CrudRepository<Rank, Long> {
}