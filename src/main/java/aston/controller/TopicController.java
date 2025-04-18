package aston.controller;

import aston.dto.TopicDto;
import aston.service.interfaces.TopicService;

import java.util.List;

public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    public List<TopicDto> getAllTopics() {
        return topicService.getAllTopics();
    }

    public TopicDto createTopic(TopicDto topicDto) {
        return topicService.createTopic(topicDto);
    }

    public boolean deleteTopic(Long id) {
        return topicService.deleteTopic(id);
    }

    public TopicDto getTopicById(Long id) {
        return topicService.getTopicById(id);
    }
}
