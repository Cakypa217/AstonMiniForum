package aston.service.impl;

import aston.dto.TagDto;
import aston.entity.Tag;
import aston.mapper.TagMapper;
import aston.repository.TagRepository;
import aston.service.interfaces.TagService;

import java.util.List;
import java.util.stream.Collectors;

public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<TagDto> getAllTags() {
        List<Tag> tags = tagRepository.findAll();
        return tags.stream()
                .map(TagMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TagDto createTag(TagDto tagDto) {
        Tag tag = TagMapper.toEntity(tagDto);
        Tag savedTag = tagRepository.save(tag);
        return TagMapper.toDto(savedTag);
    }

    @Override
    public boolean deleteTag(Long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new RuntimeException("Tag not found"));
        tagRepository.delete(tag);
        return true;
    }
}
