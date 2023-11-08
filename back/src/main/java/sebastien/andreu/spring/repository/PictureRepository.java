package sebastien.andreu.spring.repository;

import sebastien.andreu.spring.entity.Picture;
import org.springframework.data.repository.CrudRepository;

public interface PictureRepository extends CrudRepository<Picture, Long> {
}