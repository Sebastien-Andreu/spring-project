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
@Table(name = "Rank")
public class Rank {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rankId")
    private Long rankId;


    private String title;

    @JoinColumn(name = "listId")
    private Long listId;

    @Column(name = "ordering")
    private int ordering;

    @Override
    public String toString() {
        return "Rank{" +
                "rankId=" + rankId +
                ", title='" + title + '\'' +
                ", listId=" + listId +
                ", ordering=" + ordering +
                '}';
    }
}