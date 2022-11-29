package uz.aknb.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubredditDto {

    private Long id;
    private String name;
    private String description;
    private Integer numberOfPosts;
}
