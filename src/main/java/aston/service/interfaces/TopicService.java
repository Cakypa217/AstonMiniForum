package aston.service.interfaces;

import aston.dto.TopicDto;
import java.util.List;

public interface TopicService {

    List<TopicDto> getAllTopics();

    TopicDto createTopic(TopicDto topicDto);

    boolean deleteTopic(Long id);

    TopicDto getTopicById(Long id);
}