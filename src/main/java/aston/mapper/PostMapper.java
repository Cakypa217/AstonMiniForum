package aston.mapper;

import aston.dto.PostDto;
import aston.entity.Post;

public class PostMapper {

    public static PostDto toDto(Post post) {
        if (post == null) return null;

        return PostDto.builder()
                .id(post.getId())
                .content(post.getContent())
                .userId(post.getUserId())
                .topicId(post.getTopicId())
                .createdAt(post.getCreatedAt())
                .build();
    }

    public static Post toEntity(PostDto dto) {
        if (dto == null) return null;

        return Post.builder()
                .id(dto.getId())
                .content(dto.getContent())
                .userId(dto.getUserId())
                .topicId(dto.getTopicId())
                .createdAt(dto.getCreatedAt())
                .build();
    }
}