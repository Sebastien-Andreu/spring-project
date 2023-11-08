package sebastien.andreu.spring.repository;

import sebastien.andreu.spring.entity.List;
import org.springframework.data.repository.CrudRepository;
import sebastien.andreu.spring.entity.User;

import java.util.Optional;

public interface ListRepository extends CrudRepository<List, Long> {
    Optional<java.util.List<List>> findByUserId(Long userId);
}