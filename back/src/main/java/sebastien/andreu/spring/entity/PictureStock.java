package sebastien.andreu.spring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PictureStock")
public class PictureStock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pictureStockId")
    private Long id;

    @JoinColumn(name = "rankId")
    private Long rankId;

    @JoinColumn(name = "pictureId")
    private Long pictureId;
}