package hexlet.trainee.springtrainee.blueprintsPatternsDTO.controller.api;

import hexlet.trainee.springtrainee.blueprintsPatternsDTO.dto.DTOPostCreateDTO;
import hexlet.trainee.springtrainee.blueprintsPatternsDTO.dto.DTOPostDTO;
import hexlet.trainee.springtrainee.blueprintsPatternsDTO.dto.DTOPostMapper;
import hexlet.trainee.springtrainee.blueprintsPatternsDTO.dto.DTOPostUpdateDTO;
import hexlet.trainee.springtrainee.blueprintsPatternsDTO.model.DTOPost;
import hexlet.trainee.springtrainee.blueprintsPatternsDTO.repository.DTOPostRepository;
import hexlet.trainee.springtrainee.hexletSpringBlog.exception.ResourcesNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/DTO/api/posts")
public class DTOPostController {
    @Autowired
    private DTOPostRepository repository;

    @Autowired
    private DTOPostMapper postMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DTOPostDTO create(@RequestBody DTOPostCreateDTO data) {
        var post = postMapper.map(data);
        repository.save(post);
        return postMapper.map(post);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DTOPostDTO update(@PathVariable Long id, @RequestBody DTOPostUpdateDTO data) {
        var post = repository.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException("post not found"));

        postMapper.update(data, post);
        repository.save(post);
        return postMapper.map(post);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DTOPostDTO> index() {
        var posts = repository.findAll();
        return posts.stream().map(postMapper::map).toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DTOPostDTO show(@PathVariable Long id) {
        var post = repository.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException("post not found"));
        return postMapper.map(post);
    }

}
