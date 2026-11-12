package hexlet.trainee.springtrainee.patchUpdate.controllers.api;

import hexlet.trainee.springtrainee.hexletSpringBlog.exception.ResourcesNotFoundException;
import hexlet.trainee.springtrainee.patchUpdate.dto.PatchUpdatePostDTO;
import hexlet.trainee.springtrainee.patchUpdate.dto.PatchUpdatePostMapper;
import hexlet.trainee.springtrainee.patchUpdate.dto.PatchUpdatePostUpdateDTO;
import hexlet.trainee.springtrainee.patchUpdate.repository.PatchUpdatePostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/PatchUpdate/api/posts")
public class PatchUpdatePostController {
    @Autowired
    private PatchUpdatePostRepository repository;

    @Autowired
    private PatchUpdatePostMapper mapper;

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PatchUpdatePostDTO update(@PathVariable Long id, @RequestBody PatchUpdatePostUpdateDTO data) {
        var post = repository.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException("post not found"));

        mapper.update(data, post);
        repository.save(post);
        return mapper.map(post);
    }
}
