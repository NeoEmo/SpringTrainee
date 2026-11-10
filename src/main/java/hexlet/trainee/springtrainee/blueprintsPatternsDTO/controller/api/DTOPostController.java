package hexlet.trainee.springtrainee.blueprintsPatternsDTO.controller.api;

import hexlet.trainee.springtrainee.blueprintsPatternsDTO.dto.DTOPostCreateDTO;
import hexlet.trainee.springtrainee.blueprintsPatternsDTO.dto.DTOPostDTO;
import hexlet.trainee.springtrainee.blueprintsPatternsDTO.model.DTOPost;
import hexlet.trainee.springtrainee.blueprintsPatternsDTO.repository.DTOPostRepository;
import hexlet.trainee.springtrainee.hexletSpringBlog.exception.ResourcesNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/DTO/api/posts")
public class DTOPostController {
    @Autowired
    private DTOPostRepository repository;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DTOPostDTO show(@PathVariable Long id) {
        var post = repository.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException("Post with id " + id + " not found!"));

        return toDTO(post);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DTOPostDTO create(@RequestBody DTOPostCreateDTO postData) {
        var post = toEntity(postData);
        repository.save(post);
        return toDTO(post);
    }

    private DTOPostDTO toDTO(DTOPost data) {
        var dto = new DTOPostDTO();
        dto.setId(data.getId());
        dto.setName(data.getName());
        dto.setBody(data.getBody());
        dto.setSlug(data.getSlug());
        dto.setCreatedAt(data.getCreatedAt());
        return dto;
    }

    private DTOPost toEntity(DTOPostCreateDTO data) {
        var post = new DTOPost();
        post.setName(data.getName());
        post.setBody(data.getBody());
        post.setSlug(data.getSlug());
        return post;
    }
}
