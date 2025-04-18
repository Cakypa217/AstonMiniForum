package aston.controller;

import aston.dto.PostDto;
import aston.service.interfaces.PostService;
import java.util.List;

public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    public PostDto getPostById(Long id) {
        return postService.getPostById(id);
    }

    public List<PostDto> getPostsByTopicId(Long topicId) {
        return postService.getPostsByTopicId(topicId);
    }

    public PostDto createPost(PostDto postDto) {
        return postService.createPost(postDto);
    }

    public PostDto updatePost(Long id, PostDto postDto) {
        return postService.updatePost(id, postDto);
    }

    public boolean deletePost(Long id) {
        return postService.deletePost(id);
    }
}
