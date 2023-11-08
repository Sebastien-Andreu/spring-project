package sebastien.andreu.spring.dto.picture;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sebastien.andreu.spring.entity.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PictureDto {
    private String tag;
    private Long userId;
}