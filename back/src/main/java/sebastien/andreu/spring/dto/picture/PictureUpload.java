package sebastien.andreu.spring.dto.picture;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PictureUpload {
    private Long pictureId;
    private Long rankId;
}