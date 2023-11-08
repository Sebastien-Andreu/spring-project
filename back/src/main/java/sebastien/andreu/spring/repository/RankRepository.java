package sebastien.andreu.spring.repository;

import sebastien.andreu.spring.entity.Rank;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RankRepository extends CrudRepository<Rank, Long> {
    Optional<List<Rank>> findByListId(Long listId);
}