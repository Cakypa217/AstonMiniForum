package aston.test;

import aston.dto.TagDto;
import aston.entity.Tag;
import aston.repository.TagRepository;
import aston.service.impl.TagServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagServiceImpl tagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTags_shouldReturnListOfTagDto() {
        Tag tag1 = new Tag();
        tag1.setId(1L);
        tag1.setName("Java");

        Tag tag2 = new Tag();
        tag2.setId(2L);
        tag2.setName("Spring");

        List<Tag> tags = Arrays.asList(tag1, tag2);

        when(tagRepository.findAll()).thenReturn(tags);

        List<TagDto> result = tagService.getAllTags();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Java", result.get(0).getName());
        assertEquals(2L, result.get(1).getId());
        assertEquals("Spring", result.get(1).getName());
        verify(tagRepository, times(1)).findAll();
    }

    @Test
    void createTag_shouldReturnTagDto_whenTagIsValid() {
        TagDto tagDto = new TagDto();
        tagDto.setName("Java");

        Tag tag = new Tag();
        tag.setName("Java");

        Tag savedTag = new Tag();
        savedTag.setId(1L);
        savedTag.setName("Java");

        when(tagRepository.save(any(Tag.class))).thenReturn(savedTag);

        TagDto result = tagService.createTag(tagDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Java", result.getName());
        verify(tagRepository, times(1)).save(any(Tag.class));
    }

    @Test
    void deleteTag_shouldReturnTrue_whenTagExists() {
        Long tagId = 1L;
        Tag tag = new Tag();
        tag.setId(tagId);
        tag.setName("Java");

        when(tagRepository.findById(tagId)).thenReturn(Optional.of(tag));

        boolean result = tagService.deleteTag(tagId);

        assertTrue(result);
        verify(tagRepository, times(1)).findById(tagId);
        verify(tagRepository, times(1)).delete(tag);
    }

    @Test
    void deleteTag_shouldThrowException_whenTagDoesNotExist() {
        Long tagId = 1L;
        when(tagRepository.findById(tagId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            tagService.deleteTag(tagId);
        });

        assertEquals("Tag not found", exception.getMessage());
        verify(tagRepository, times(1)).findById(tagId);
        verify(tagRepository, never()).delete(any(Tag.class));
    }
}