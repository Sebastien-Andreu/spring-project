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
@Table(name = "List")
public class List {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "listId")
    private Long listId;

    @Column(name = "title")
    private String title;

    @Column(name = "tag")
    private String tag;

    @Column(name = "updateTime")
    private String updateTime;

    @JoinColumn(name = "userId")
    private Long userId;

    @Override
    public String toString() {
        return "{" +
                "\"listId\":" + listId +
                ", \"title\":\"" + title + '\"' +
                ", \"tag\":\"" + tag + '\"' +
                ", \"updateTime\":\"" + updateTime + '\"' +
                ", \"userId\":" + userId +
                '}';
    }
}