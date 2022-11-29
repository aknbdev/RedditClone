package db.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE posts set deleted = true where id ?", check = ResultCheckStyle.COUNT)
public class EntPost extends EntBase{

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "vote_count")
    private Integer voteCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private EntUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subreddit", referencedColumnName = "id")
    private EntSubreddit entSubreddit;
}
