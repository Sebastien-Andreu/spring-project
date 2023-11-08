package sebastien.andreu.spring.repository;

import sebastien.andreu.spring.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByPseudoOrEmail(String pseudo, String email);
    User findByEmail(String email);
    User findByPseudo(String pseudo);
    boolean existsByEmail(String email);
    boolean existsByPseudo(String pseudo);
}