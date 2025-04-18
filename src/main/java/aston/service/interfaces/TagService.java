package aston.service.interfaces;

import aston.dto.TagDto;
import java.util.List;

public interface TagService {

    List<TagDto> getAllTags();

    TagDto createTag(TagDto tagDto);

    boolean deleteTag(Long id);
}
