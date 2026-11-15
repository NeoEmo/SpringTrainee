package hexlet.trainee.springtrainee.hexletSpringBlog.api.controller;

import hexlet.trainee.springtrainee.hexletSpringBlog.dto.post.PostCreateDTO;
import hexlet.trainee.springtrainee.hexletSpringBlog.dto.post.PostDTO;
import hexlet.trainee.springtrainee.hexletSpringBlog.dto.post.PostParamsDTO;
import hexlet.trainee.springtrainee.hexletSpringBlog.dto.post.PostPatchDTO;
import hexlet.trainee.springtrainee.hexletSpringBlog.dto.tag.TagCreateDTO;
import hexlet.trainee.springtrainee.hexletSpringBlog.dto.tag.TagDTO;
import hexlet.trainee.springtrainee.hexletSpringBlog.exception.ResourcesNotFoundException;

import hexlet.trainee.springtrainee.hexletSpringBlog.mappers.TagMapper;
import hexlet.trainee.springtrainee.hexletSpringBlog.repository.PostsRepository;
import hexlet.trainee.springtrainee.hexletSpringBlog.repository.UsersRepository;
import hexlet.trainee.springtrainee.hexletSpringBlog.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import java.util.List;


@RestController
@RequestMapping("/api/posts")
public class PostsController {

    @Autowired
    private PostService postService;

    @GetMapping
    public ResponseEntity<Page<PostDTO>> index(
            PostParamsDTO params,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(postService.index(params, page, size));
    }

    @PostMapping
    public ResponseEntity<PostDTO> create(@Valid @RequestBody PostCreateDTO data) {
        var dto = postService.create(data);
        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();

        return ResponseEntity.created(location).body(dto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PostDTO> update(@PathVariable Long id, @Valid @RequestBody PostPatchDTO data) {
        return ResponseEntity.ok().body(postService.update(data, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> show(@PathVariable Long id) {
        return ResponseEntity.ok().body(postService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{postId}/tags")
    public ResponseEntity<List<TagDTO>> getPostTags(@PathVariable Long postId) {
        return ResponseEntity.ok().body(postService.getPostTags(postId));
    }

    @PostMapping("/{postId}/tags")
    public ResponseEntity<TagDTO> createTagForPost(
            @PathVariable Long postId,
            @Valid @RequestBody TagCreateDTO data) {

        var dto = postService.createTagForPost(postId, data);

        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();

        return ResponseEntity.created(location).body(dto);
    }
}
