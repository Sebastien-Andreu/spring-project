package sebastien.andreu.spring.repository;

import sebastien.andreu.spring.entity.PictureStock;
import org.springframework.data.repository.CrudRepository;

public interface PictureStockRepository extends CrudRepository<PictureStock, Long> {
}