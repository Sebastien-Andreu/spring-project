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
@Table(name = "Picture")
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pictureId")
    private Long pictureId;

    @Column(name = "url")
    private String url;

    @Column(name = "isAvailable")
    private boolean isAvailable;

    @Column(name = "tag")
    private String tag;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "userId")
    private User user;

    @Override
    public String toString() {
        return "Picture{" +
                "pictureId=" + pictureId +
                ", url='" + url + '\'' +
                ", isAvailable=" + isAvailable +
                ", tag='" + tag + '\'' +
                ", user=" + user +
                '}';
    }
}