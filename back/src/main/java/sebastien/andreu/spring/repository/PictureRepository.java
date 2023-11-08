package sebastien.andreu.spring.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import sebastien.andreu.spring.entity.Picture;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PictureRepository extends CrudRepository<Picture, Long> {
    Optional<java.util.List<Picture>> findByUserId(Long userId);
    Optional<java.util.List<Picture>> findByTagContaining(String tag);

    Picture findFirstByOrderByPictureIdDesc();

    Optional<List<Picture>> findAllByPictureId(Long pictureId);
}