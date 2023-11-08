package sebastien.andreu.spring.dto.list;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListPutDto {
    private Long listId;
    private String title;
    private String tag;
    private String updateTime;
    private Long userId;
}