package sebastien.andreu.spring.dto.picture;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import sebastien.andreu.spring.entity.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PictureDto {
    private Long pictureId;
    private String tag;
    private Long userId;
    private Long rankId;
    private MultipartFile file;
}