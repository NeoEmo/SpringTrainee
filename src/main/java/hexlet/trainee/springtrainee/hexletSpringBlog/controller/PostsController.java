package hexlet.trainee.springtrainee.hexletSpringBlog.controller;

import hexlet.trainee.springtrainee.hexletSpringBlog.model.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/posts")
public class PostsController {
    private List<Post> posts =  new ArrayList<>();
    private Long post_id = 1L;

    @GetMapping
    public ResponseEntity<List<Post>> index(@RequestParam(defaultValue = "10") Integer limit) {
        var result = posts.stream().limit(limit).toList();

        return ResponseEntity.ok().body(result);
    }

    @PostMapping
    public ResponseEntity<Post> create(@RequestBody Post post) {
        post.setId(post_id++);
        post.setCreatedAt(LocalDateTime.now());
        posts.add(post);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(location).body(post);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> show(@PathVariable Long id) {
        var post = posts.stream().filter(p -> p.getId().equals(id)).findFirst();

        return ResponseEntity.of(post);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> update(@PathVariable Long id, @RequestBody Post data) {
        var postOptional = posts.stream().filter(p -> p.getId().equals(id)).findFirst();

        if(postOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var post = postOptional.get();
        post.setTitle(data.getTitle());
        post.setContent(data.getContent());
        post.setAuthor(data.getAuthor());

        return ResponseEntity.ok().body(post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        var isRemoved = posts.removeIf(p -> p.getId().equals(id));
        if (!isRemoved) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
