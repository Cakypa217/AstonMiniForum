package aston.test;

import aston.dto.TopicDto;
import aston.entity.Topic;
import aston.repository.TopicRepository;
import aston.service.impl.TopicServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TopicServiceTest {

    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private TopicServiceImpl topicService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTopic_shouldReturnTopicDto_whenTopicExists() {
        Long topicId = 1L;
        Topic topic = Topic.builder()
                .id(topicId)
                .title("Test Description")
                .build();

        when(topicRepository.findById(topicId)).thenReturn(Optional.of(topic));

        TopicDto result = topicService.getTopicById(topicId);

        assertNotNull(result);
        assertEquals(topicId, result.getId());
        assertEquals("Test Description", result.getTitle());
        verify(topicRepository, times(1)).findById(topicId);
    }

    @Test
    void getTopic_shouldThrowException_whenTopicDoesNotExist() {
        Long topicId = 1L;
        when(topicRepository.findById(topicId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            topicService.getTopicById(topicId);
        });

        assertEquals("Topic not found", exception.getMessage());
        verify(topicRepository, times(1)).findById(topicId);
    }

    @Test
    void createTopic_shouldReturnTopicDto_whenTopicIsValid() {
        TopicDto topicDto = TopicDto.builder()
                .title("New Description")
                .build();

        Topic topic = Topic.builder()
                .title("New Description")
                .build();

        Topic savedTopic = Topic.builder()
                .id(1L)
                .title("New Description")
                .build();

        when(topicRepository.save(any(Topic.class))).thenReturn(savedTopic);

        TopicDto result = topicService.createTopic(topicDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("New Description", result.getTitle());
        verify(topicRepository, times(1)).save(any(Topic.class));
    }



    @Test
    void deleteTopic_shouldCallRepositoryDelete_whenTopicExists() {
        Long topicId = 1L;
        Topic topic = Topic.builder()
                .id(topicId)
                .title("Test Description")
                .build();

        when(topicRepository.findById(topicId)).thenReturn(Optional.of(topic));

        topicService.deleteTopic(topicId);

        verify(topicRepository, times(1)).findById(topicId);
        verify(topicRepository, times(1)).delete(topic);
    }

    @Test
    void deleteTopic_shouldThrowException_whenTopicDoesNotExist() {
        Long topicId = 1L;
        when(topicRepository.findById(topicId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            topicService.deleteTopic(topicId);
        });

        assertEquals("Topic not found", exception.getMessage());
        verify(topicRepository, times(1)).findById(topicId);
        verify(topicRepository, never()).delete(any(Topic.class));
    }
}
