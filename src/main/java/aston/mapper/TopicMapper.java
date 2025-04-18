package aston.mapper;

import aston.dto.TagDto;
import aston.dto.TopicDto;
import aston.entity.Tag;
import aston.entity.Topic;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TopicMapper {

    public static TopicDto toDto(Topic topic) {
        if (topic == null) return null;

        List<TagDto> tagDto = topic.getTags() != null
                ? topic.getTags().stream()
                .map(tag -> TagDto.builder()
                        .id(tag.getId())
                        .name(tag.getName())
                        .build())
                .collect(Collectors.toList())
                : Collections.emptyList();

        return TopicDto.builder()
                .id(topic.getId())
                .title(topic.getTitle())
                .userId(topic.getUserId())
                .createdAt(topic.getCreatedAt())
                .tags(tagDto)
                .build();
    }

    public static Topic toEntity(TopicDto dto) {
        if (dto == null) return null;

        List<Tag> tags = dto.getTags() != null
                ? dto.getTags().stream()
                .map(tagDto -> Tag.builder()
                        .id(tagDto.getId())
                        .name(tagDto.getName())
                        .build())
                .collect(Collectors.toList())
                : Collections.emptyList();

        return Topic.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .userId(dto.getUserId())
                .createdAt(dto.getCreatedAt())
                .tags(tags)
                .build();
    }
}