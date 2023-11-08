package sebastien.andreu.spring.repository;

import sebastien.andreu.spring.entity.Picture;
import sebastien.andreu.spring.entity.PictureStock;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PictureStockRepository extends CrudRepository<PictureStock, Long> {
}