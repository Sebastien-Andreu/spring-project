package sebastien.andreu.spring.dto.rank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RankPutDto {
    private Long rankId;
    private String title;
    private Long listId;
    private int ordering;
}