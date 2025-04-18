package aston.test;

import aston.dto.PostDto;
import aston.entity.Post;
import aston.repository.PostRepository;
import aston.service.impl.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPostById_shouldReturnPostDto_whenPostExists() {
        Long postId = 1L;
        Post post = new Post();
        post.setId(postId);
        post.setContent("Test Content");
        post.setUserId(1L);
        post.setTopicId(1L);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        PostDto result = postService.getPostById(postId);

        assertNotNull(result);
        assertEquals(postId, result.getId());
        assertEquals("Test Content", result.getContent());
        assertEquals(1L, result.getUserId());
        assertEquals(1L, result.getTopicId());
        verify(postRepository, times(1)).findById(postId);
    }

    @Test
    void getPostById_shouldThrowException_whenPostDoesNotExist() {
        Long postId = 1L;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            postService.getPostById(postId);
        });

        assertEquals("Post not found", exception.getMessage());
        verify(postRepository, times(1)).findById(postId);
    }

    @Test
    void getPostsByTopicId_shouldReturnListOfPostDto() {
        Long topicId = 1L;
        Post post1 = new Post();
        post1.setId(1L);
        post1.setContent("Content 1");
        post1.setUserId(1L);
        post1.setTopicId(topicId);

        Post post2 = new Post();
        post2.setId(2L);
        post2.setContent("Content 2");
        post2.setUserId(2L);
        post2.setTopicId(topicId);

        List<Post> posts = Arrays.asList(post1, post2);

        when(postRepository.findByTopicId(topicId)).thenReturn(posts);

        List<PostDto> result = postService.getPostsByTopicId(topicId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Content 1", result.get(0).getContent());
        assertEquals(2L, result.get(1).getId());
        assertEquals("Content 2", result.get(1).getContent());
        verify(postRepository, times(1)).findByTopicId(topicId);
    }

    @Test
    void createPost_shouldReturnPostDto_whenPostIsValid() {
        PostDto postDto = new PostDto();
        postDto.setContent("New Content");
        postDto.setUserId(1L);
        postDto.setTopicId(1L);

        Post post = new Post();
        post.setContent("New Content");
        post.setUserId(1L);
        post.setTopicId(1L);

        Post savedPost = new Post();
        savedPost.setId(1L);
        savedPost.setContent("New Content");
        savedPost.setUserId(1L);
        savedPost.setTopicId(1L);
        savedPost.setCreatedAt(LocalDateTime.now());

        when(postRepository.save(any(Post.class))).thenReturn(savedPost);

        PostDto result = postService.createPost(postDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("New Content", result.getContent());
        assertEquals(1L, result.getUserId());
        assertEquals(1L, result.getTopicId());
        assertNotNull(result.getCreatedAt());
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void updatePost_shouldReturnUpdatedPostDto_whenPostExists() {
        Long postId = 1L;
        PostDto postDto = new PostDto();
        postDto.setContent("Updated Content");
        postDto.setUserId(2L);
        postDto.setTopicId(2L);

        Post existingPost = new Post();
        existingPost.setId(postId);
        existingPost.setContent("Old Content");
        existingPost.setUserId(1L);
        existingPost.setTopicId(1L);
        existingPost.setCreatedAt(LocalDateTime.now());

        Post updatedPost = new Post();
        updatedPost.setId(postId);
        updatedPost.setContent("Updated Content");
        updatedPost.setUserId(2L);
        updatedPost.setTopicId(2L);
        updatedPost.setCreatedAt(existingPost.getCreatedAt());

        when(postRepository.findById(postId)).thenReturn(Optional.of(existingPost));
        when(postRepository.save(any(Post.class))).thenReturn(updatedPost);

        PostDto result = postService.updatePost(postId, postDto);

        assertNotNull(result);
        assertEquals(postId, result.getId());
        assertEquals("Updated Content", result.getContent());
        assertEquals(2L, result.getUserId());
        assertEquals(2L, result.getTopicId());
        verify(postRepository, times(1)).findById(postId);
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void updatePost_shouldThrowException_whenPostDoesNotExist() {
        Long postId = 1L;
        PostDto postDto = new PostDto();
        postDto.setContent("Updated Content");

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            postService.updatePost(postId, postDto);
        });

        assertEquals("Post not found", exception.getMessage());
        verify(postRepository, times(1)).findById(postId);
        verify(postRepository, never()).save(any(Post.class));
    }

    @Test
    void deletePost_shouldReturnTrue_whenPostExists() {
        Long postId = 1L;
        Post post = new Post();
        post.setId(postId);
        post.setContent("Test Content");

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        boolean result = postService.deletePost(postId);

        assertTrue(result);
        verify(postRepository, times(1)).findById(postId);
        verify(postRepository, times(1)).delete(post);
    }

    @Test
    void deletePost_shouldThrowException_whenPostDoesNotExist() {
        Long postId = 1L;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            postService.deletePost(postId);
        });

        assertEquals("Post not found", exception.getMessage());
        verify(postRepository, times(1)).findById(postId);
        verify(postRepository, never()).delete(any(Post.class));
    }
}