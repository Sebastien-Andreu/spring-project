package sebastien.andreu.spring.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sebastien.andreu.spring.entity.PictureStock;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PictureStockRepository extends CrudRepository<PictureStock, Long> {
    Optional<List<PictureStock>> findByRankId(Long rankId);

    @Transactional
    @Modifying
    @Query("DELETE FROM PictureStock ps WHERE ps.pictureId = :pictureId")
    void deleteByPictureId(@Param("pictureId") Long pictureId);

}