package hexlet.trainee.springtrainee.hexletSpringBlog.api.controller;

import hexlet.trainee.springtrainee.hexletSpringBlog.dto.post.*;
import hexlet.trainee.springtrainee.hexletSpringBlog.exception.ResourceAlreadyExistException;
import hexlet.trainee.springtrainee.hexletSpringBlog.exception.ResourcesNotFoundException;

import hexlet.trainee.springtrainee.hexletSpringBlog.mappers.PostMapper;
import hexlet.trainee.springtrainee.hexletSpringBlog.repository.PostsRepository;
import hexlet.trainee.springtrainee.hexletSpringBlog.repository.UsersRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/api/posts")
public class PostsController {

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    UsersRepository userRepository;

    @Autowired
    private PostMapper postMapper;

    @GetMapping
    public ResponseEntity<Page<PostDTO>> index(
            @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        var safePage = Math.max(0, page - 1);
        Pageable result = PageRequest.of(safePage, size);

        return ResponseEntity.ok()
                .body(postsRepository.findByOrderByCreatedAtDesc(result).map(postMapper::toDTO));
    }

    @PostMapping
    public ResponseEntity<PostDTO> create(@Valid @RequestBody PostCreateDTO data) {
        var user = userRepository.findById(data.getUserId())
                .orElseThrow(() -> new ResourcesNotFoundException("User with id" + data.getUserId() + " not found"));


        var post = postMapper.toEntity(data);

        if (postsRepository.existsByTitle(post.getTitle())) {
            throw new ResourceAlreadyExistException("Title already exists");
        }

        post.setAuthor(user);
        post = postsRepository.save(post);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();

        var dto = postMapper.toDTO(post);

        return ResponseEntity.created(location).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> show(@PathVariable Long id) {
        var post = postsRepository.findById(id).map(postMapper::toDTO)
                .orElseThrow(() -> new ResourcesNotFoundException("Post with id " + id + " not found"));

        return ResponseEntity.ok().body(post);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PostDTO> update(@PathVariable Long id, @Valid @RequestBody PostPatchDTO data) {
        var post = postsRepository.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException("Post with id " + id + " not found"));

        postMapper.updateEntityFromDTO(data, post);

        postsRepository.save(post);

        return ResponseEntity.ok().body(postMapper.toDTO(post));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!postsRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        postsRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
