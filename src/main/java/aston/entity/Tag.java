package aston.entity;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    private Long id;

    private String name;

    @Override
    public boolean equals(Object o) {
        if ( this == o) return true;
        if (!(o instanceof Tag)) return false;
        Tag tags = (Tag) o;
        return id != null && id.equals(tags.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}