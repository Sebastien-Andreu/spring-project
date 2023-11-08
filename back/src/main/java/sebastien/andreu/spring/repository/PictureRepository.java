package sebastien.andreu.spring.repository;

import sebastien.andreu.spring.entity.Picture;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PictureRepository extends CrudRepository<Picture, Long> {
    Optional<java.util.List<Picture>> findByUserId(Long userId);

    Picture findFirstByOrderByPictureIdDesc();

    Optional<List<Picture>> findAllById(Long pictureId);
}