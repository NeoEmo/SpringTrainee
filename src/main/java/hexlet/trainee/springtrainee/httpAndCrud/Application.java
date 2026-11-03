package hexlet.trainee.springtrainee.httpAndCrud;

import hexlet.trainee.springtrainee.httpAndCrud.model.Page;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
public class Application {
    private List<Page> pages = new ArrayList<>();

    @GetMapping("/pages") // Показать страницы
    public List<Page> index(@RequestParam(defaultValue = "10") Integer limit) {
        return pages.stream().limit(limit).toList();
    }

    @PostMapping("/pages") // C CRUD, создание страницы
    public Page create(@RequestBody Page page) {
        pages.add(page);
        return page;
    }

    @GetMapping("/pages/{id}") // R CRUD, Показать страницу
    public Optional<Page> show(@PathVariable String id) {
        return pages.stream().filter(p -> p.getSlug().equals(id)).findFirst();
    }

    @PutMapping("/pages/{id}") // U CRUD, обновление страницы
    public Page update(@PathVariable String id, @RequestBody Page data) {
        var page = pages.stream()
                .filter(p -> p.getSlug().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        page.setSlug(data.getSlug());
        page.setName(data.getName());
        page.setBody(data.getBody());

        return page;
    }

    @DeleteMapping("/pages/{id}") // D CRUD, удаление страницы
    public void delete(@PathVariable String id)  {
        pages.removeIf(p -> p.getSlug().equals(id));
    }
}
