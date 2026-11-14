package hexlet.trainee.springtrainee.hexletSpringBlog.api.controller;

import hexlet.trainee.springtrainee.hexletSpringBlog.dto.post.*;
import hexlet.trainee.springtrainee.hexletSpringBlog.dto.tag.TagCreateDTO;
import hexlet.trainee.springtrainee.hexletSpringBlog.dto.tag.TagDTO;
import hexlet.trainee.springtrainee.hexletSpringBlog.exception.ResourceAlreadyExistException;
import hexlet.trainee.springtrainee.hexletSpringBlog.exception.ResourcesNotFoundException;

import hexlet.trainee.springtrainee.hexletSpringBlog.mappers.PostMapper;
import hexlet.trainee.springtrainee.hexletSpringBlog.mappers.TagMapper;
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
import java.util.List;


@RestController
@RequestMapping("/api/posts")
public class PostsController {

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    UsersRepository userRepository;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private TagMapper tagMapper;

    @GetMapping
    public ResponseEntity<Page<PostDTO>> index(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        var safePage = Math.max(0, page - 1);
        Pageable result = PageRequest.of(safePage, size);

        return ResponseEntity.ok()
                .body(postsRepository.findByOrderByCreatedAtDesc(result)
                        .map(postMapper::toDTO));
    }

    @PostMapping
    public ResponseEntity<PostDTO> create(@Valid @RequestBody PostCreateDTO data) {
        var user = userRepository.findById(data.getUserId())
                .orElseThrow(() -> new ResourcesNotFoundException("User with id " + data.getUserId() + " not found"));

        var post = postMapper.toEntity(data);

        if(postsRepository.findBySlug(post.getSlug()).isPresent()) {
            throw new ResourceAlreadyExistException("Slug \"" + post.getSlug() + "\" already exists");
        }

        post.setAuthor(user);
        post =  postsRepository.save(post);

        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(location).body(postMapper.toDTO(post));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> show(@PathVariable Long id) {
        var post = postsRepository.findById(id)
                .map(postMapper::toDTO)
                .orElseThrow(() -> new ResourcesNotFoundException("Post with id " + id + " not found"));

        return ResponseEntity.ok().body(post);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PostDTO> update(@PathVariable Long id, @Valid @RequestBody PostPatchDTO data) {
        var post = postsRepository.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException("Post with id " + id + " not found"));

        if (data.getUserId() != null && data.getUserId().isPresent() && data.getUserId().get() != null) {
            var newUserId = data.getUserId().get();
            var newAuthor = userRepository.findById(newUserId)
                    .orElseThrow(() -> new ResourcesNotFoundException("User with id " + newUserId + " not found"));

            post.setAuthor(newAuthor);
        }

        postMapper.updateEntityFromDTO(data, post);
        postsRepository.save(post);
        return ResponseEntity.ok().body(postMapper.toDTO(post));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!postsRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        postsRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{postId}/tags")
    public ResponseEntity<List<TagDTO>> getPostTags(@PathVariable Long postId) {
        var post = postsRepository.findById(postId)
                .orElseThrow(() -> new ResourcesNotFoundException("Post with id " + postId + " not found"));

        var tags = post.getTags().stream()
                .map(tagMapper::toDTO)
                .toList();

        return ResponseEntity.ok().body(tags);
    }

    @PostMapping("/{postId}/tags")
    public ResponseEntity<TagDTO> createTagForPost(
            @PathVariable Long postId,
            @Valid @RequestBody TagCreateDTO data) {

        var post = postsRepository.findById(postId)
                .orElseThrow(() -> new ResourcesNotFoundException("Post with id " + postId + " not found"));

        var tag = tagMapper.toEntity(data);
        tag.setPost(post);

        post.getTags().add(tag);
        postsRepository.save(post);

        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(tag.getId())
                .toUri();

        return ResponseEntity.created(location).body(tagMapper.toDTO(tag));
    }
}
