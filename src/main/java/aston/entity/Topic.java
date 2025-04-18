package aston.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Topic {

    private Long id;

    private String title;

    private Long userId;

    private List<Tag> tags;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Override
    public boolean equals(Object o) {
        if ( this == o) return true;
        if (!(o instanceof Topic)) return false;
        Topic topics = (Topic) o;
        return id != null && id.equals(topics.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}