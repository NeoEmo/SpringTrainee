package hexlet.trainee.springtrainee.JpaSpecifications.api.controller;

import hexlet.trainee.springtrainee.JpaSpecifications.DTO.JPASPostDTO;
import hexlet.trainee.springtrainee.JpaSpecifications.DTO.JPASPostParamsDTO;
import hexlet.trainee.springtrainee.JpaSpecifications.mapper.JPASPostMapper;
import hexlet.trainee.springtrainee.JpaSpecifications.repository.JPASPostRepository;
import hexlet.trainee.springtrainee.JpaSpecifications.specification.JPASPostSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jpas/api/posts")
public class JPASPostController {
    @Autowired
    private JPASPostRepository repository;

    @Autowired
    private JPASPostSpecification specBuilder;

    @Autowired
    private JPASPostMapper mapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<JPASPostDTO> index(JPASPostParamsDTO params, @RequestParam(defaultValue = "1") int page) {
        var safePage = Math.max(1, page - 1);
        var spec = specBuilder.build(params);
        var posts = repository.findAll(spec, PageRequest.of(safePage, 10));
        return posts.map(mapper::toDTO);
    }
}
