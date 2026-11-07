package hexlet.trainee.springtrainee.hexletSpringBlog.api.controller;

import hexlet.trainee.springtrainee.hexletSpringBlog.exception.ResourcesNotFoundException;
import hexlet.trainee.springtrainee.hexletSpringBlog.model.Post;

import hexlet.trainee.springtrainee.hexletSpringBlog.repository.PostsRepository;
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

    @GetMapping
    public ResponseEntity<Page<Post>> index(
            @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        var safePage = Math.max(0, page - 1);
        Pageable result = PageRequest.of(safePage, size);

        return ResponseEntity.ok().body(postsRepository.findByOrderByCreatedAtDesc(result));
    }

    @PostMapping
    public ResponseEntity<Post> create(@Valid @RequestBody Post post) {
        postsRepository.save(post);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(location).body(post);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> show(@PathVariable Long id) {
        var post = postsRepository.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException("Post with id " + id + " not found"));

        return ResponseEntity.ok().body(post);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Post> update(@PathVariable Long id, @RequestBody Post data) {
        var postOptional = postsRepository.findById(id);

        if(postOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var post = postOptional.get();
        if (data.getTitle() != null) post.setTitle(data.getTitle());
        if (data.getContent() != null) post.setContent(data.getContent());
        if (data.getAuthor() != null) post.setAuthor(data.getAuthor());
        postsRepository.save(post);

        return ResponseEntity.ok().body(post);
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
