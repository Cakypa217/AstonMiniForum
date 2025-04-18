package aston.service.impl;

import aston.dto.PostDto;
import aston.entity.Post;
import aston.mapper.PostMapper;
import aston.repository.PostRepository;
import aston.service.interfaces.PostService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        return PostMapper.toDto(post);
    }

    @Override
    public List<PostDto> getPostsByTopicId(Long topicId) {
        List<Post> posts = postRepository.findByTopicId(topicId);
        return posts.stream()
                .map(PostMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = PostMapper.toEntity(postDto);
        post.setCreatedAt(LocalDateTime.now());
        Post savedPost = postRepository.save(post);
        return PostMapper.toDto(savedPost);
    }

    @Override
    public PostDto updatePost(Long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setContent(postDto.getContent());
        post.setUserId(postDto.getUserId());
        post.setTopicId(postDto.getTopicId());
        Post updatedPost = postRepository.save(post);
        return PostMapper.toDto(updatedPost);
    }

    @Override
    public boolean deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        postRepository.delete(post);
        return true;
    }
}