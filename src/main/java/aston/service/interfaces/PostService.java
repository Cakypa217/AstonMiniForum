package aston.service.interfaces;

import aston.dto.PostDto;
import java.util.List;

public interface PostService {

    List<PostDto> getPostsByTopicId(Long topicId);

    PostDto getPostById(Long id);

    PostDto createPost(PostDto postDto);

    PostDto updatePost(Long id, PostDto postDto);

    boolean deletePost(Long id);
}
