package aston.controller;

import aston.dto.TagDto;
import aston.service.interfaces.TagService;

import java.util.List;

public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    public TagDto createTag(TagDto tagDto) {
        return tagService.createTag(tagDto);
    }

    public boolean deleteTag(Long id) {
        return tagService.deleteTag(id);
    }

    public List<TagDto> getAllTags() {
        return tagService.getAllTags();
    }
}
