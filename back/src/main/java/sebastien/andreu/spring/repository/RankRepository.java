package sebastien.andreu.spring.repository;

import sebastien.andreu.spring.entity.Rank;
import org.springframework.data.repository.CrudRepository;

public interface RankRepository extends CrudRepository<Rank, Long> {
}