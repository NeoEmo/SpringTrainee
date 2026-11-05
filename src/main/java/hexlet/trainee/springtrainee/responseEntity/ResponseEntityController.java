package hexlet.trainee.springtrainee.responseEntity;

import hexlet.trainee.springtrainee.responseEntity.model.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/response")
public class ResponseEntityController {
    private List<Page> pages = new ArrayList<>();

    @GetMapping("/pages")
    public ResponseEntity<List<Page>> index(@RequestParam(defaultValue = "10") Integer limit) {
        var result = pages.stream().limit(limit).toList();

        return ResponseEntity.ok()
                .header("X-total-Count", String.valueOf(pages.size()))
                .body(result);
    }

    @GetMapping("/pages/{id}")
    public ResponseEntity<Page> show(@PathVariable String id) {
        var page = pages.stream().filter(p -> p.getSlug().equals(id)).findFirst();
        return ResponseEntity.of(page);
    }

    @PostMapping("/pages")
    public ResponseEntity<Page> create(@RequestBody Page page) {
        pages.add(page);
        var location = URI.create("/pages/" + page.getSlug());
        return ResponseEntity.created(location).body(page);
    }
}
