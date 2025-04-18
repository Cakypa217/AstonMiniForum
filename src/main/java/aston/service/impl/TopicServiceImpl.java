package aston.service.impl;

import aston.dto.TopicDto;
import aston.entity.Topic;
import aston.mapper.TopicMapper;
import aston.repository.TopicRepository;
import aston.service.interfaces.TopicService;

import java.util.List;
import java.util.stream.Collectors;

public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;

    public TopicServiceImpl(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public List<TopicDto> getAllTopics() {
        List<Topic> topics = topicRepository.findAll();
        return topics.stream()
                .map(TopicMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TopicDto createTopic(TopicDto topicDto) {
        Topic topic = TopicMapper.toEntity(topicDto);
        Topic savedTopic = topicRepository.save(topic);
        return TopicMapper.toDto(savedTopic);
    }

    @Override
    public boolean deleteTopic(Long id) {
        Topic topic = topicRepository.findById(id).orElseThrow(() -> new RuntimeException("Topic not found"));
        topicRepository.delete(topic);
        return true;
    }

    @Override
    public TopicDto getTopicById(Long id) {
        Topic topic = topicRepository.findById(id).orElseThrow(() -> new RuntimeException("Topic not found"));
        return TopicMapper.toDto(topic);
    }
}
