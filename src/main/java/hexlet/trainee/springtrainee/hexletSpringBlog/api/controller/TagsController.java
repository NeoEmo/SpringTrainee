package hexlet.trainee.springtrainee.hexletSpringBlog.api.controller;

import hexlet.trainee.springtrainee.hexletSpringBlog.dto.tag.TagCreateDTO;
import hexlet.trainee.springtrainee.hexletSpringBlog.dto.tag.TagDTO;
import hexlet.trainee.springtrainee.hexletSpringBlog.dto.tag.TagUpdateDTO;
import hexlet.trainee.springtrainee.hexletSpringBlog.exception.ResourcesNotFoundException;
import hexlet.trainee.springtrainee.hexletSpringBlog.mappers.PostMapper;
import hexlet.trainee.springtrainee.hexletSpringBlog.mappers.TagMapper;
import hexlet.trainee.springtrainee.hexletSpringBlog.repository.PostsRepository;
import hexlet.trainee.springtrainee.hexletSpringBlog.repository.TagsRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/tags")
public class TagsController {

    @Autowired
    private TagsRepository tagsRepository;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private PostMapper postMapper;

    @GetMapping
    public ResponseEntity<Page<TagDTO>> index(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        var safePage = Math.max(0, page - 1);
        var result = PageRequest.of(safePage, size);

        return ResponseEntity.ok().body(tagsRepository
                .findByOrderByIdDesc(result)
                .map(tagMapper::toDTO));
    }

    @PostMapping
    public ResponseEntity<TagDTO> create(@Valid @RequestBody TagCreateDTO data) {
        var post = postsRepository.findById(data.getPostId())
                .orElseThrow(() -> new ResourcesNotFoundException("Post with id " + data.getPostId() + " not found"));

        var tag = tagMapper.toEntity(data);
        tag.setPost(post);
        tagsRepository.save(tag);

        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(tag.getId())
                .toUri();

        var dto = tagMapper.toDTO(tag);

        return ResponseEntity.created(location).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDTO> show(@PathVariable Long id) {
        var tag = tagsRepository.findById(id).map(tagMapper::toDTO)
                .orElseThrow(() -> new ResourcesNotFoundException("Tag with id " + id + " not found"));

        return ResponseEntity.ok().body(tag);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TagDTO> patch(@PathVariable Long id, @Valid @RequestBody TagUpdateDTO data) {
        var tag = tagsRepository.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException("Tag with id " + id + " not found"));

        tagMapper.update(data, tag);
        tagsRepository.save(tag);

        var dto =  tagMapper.toDTO(tag);

        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!tagsRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        tagsRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
