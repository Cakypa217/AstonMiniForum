package aston.mapper;

import aston.dto.TagDto;
import aston.entity.Tag;

public class TagMapper {

    public static TagDto toDto(Tag tag) {
        return TagDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }

    public static Tag toEntity(TagDto tagDto) {
        return Tag.builder()
                .id(tagDto.getId())
                .name(tagDto.getName())
                .build();
    }
}