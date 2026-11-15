package hexlet.trainee.springtrainee.hexletSpringBlog.service;

import hexlet.trainee.springtrainee.hexletSpringBlog.dto.post.PostCreateDTO;
import hexlet.trainee.springtrainee.hexletSpringBlog.dto.post.PostDTO;
import hexlet.trainee.springtrainee.hexletSpringBlog.dto.post.PostParamsDTO;
import hexlet.trainee.springtrainee.hexletSpringBlog.dto.post.PostPatchDTO;
import hexlet.trainee.springtrainee.hexletSpringBlog.dto.tag.TagCreateDTO;
import hexlet.trainee.springtrainee.hexletSpringBlog.dto.tag.TagDTO;
import hexlet.trainee.springtrainee.hexletSpringBlog.exception.ResourceAlreadyExistException;
import hexlet.trainee.springtrainee.hexletSpringBlog.exception.ResourcesNotFoundException;
import hexlet.trainee.springtrainee.hexletSpringBlog.mappers.PostMapper;
import hexlet.trainee.springtrainee.hexletSpringBlog.mappers.TagMapper;
import hexlet.trainee.springtrainee.hexletSpringBlog.model.User;
import hexlet.trainee.springtrainee.hexletSpringBlog.repository.PostsRepository;
import hexlet.trainee.springtrainee.hexletSpringBlog.repository.TagsRepository;
import hexlet.trainee.springtrainee.hexletSpringBlog.repository.UsersRepository;
import hexlet.trainee.springtrainee.hexletSpringBlog.specification.PostSpecification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PostService {
    @Autowired
    private PostsRepository repository;

    @Autowired
    private PostMapper mapper;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private TagsRepository tagsRepository;

    @Autowired
    private PostSpecification specBuilder;

    @Autowired
    private TagMapper tagMapper;

    public Page<PostDTO> index(PostParamsDTO params, int page, int size) {
        var safePage = Math.max(0, page - 1);
        var pageable = PageRequest.of(safePage, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        var spec = specBuilder.build(params);
        return repository.findAll(spec, pageable).map(mapper::toDTO);
    }

    public PostDTO create(PostCreateDTO data) {
        var author = usersRepository.findById(data.getUserId())
                .orElseThrow(() -> new ResourcesNotFoundException("User with id " + data.getUserId() + " not found"));


        var post = mapper.toEntity(data);

        if (repository.findBySlug(post.getSlug()).isPresent()) {
            throw new ResourceAlreadyExistException("Slug \"" + post.getSlug() + "\" already exists");
        }

        post.setAuthor(author);
        post.setAuthorName(author.getUsername());
        return mapper.toDTO(repository.save(post));
    }

    public PostDTO findById(Long id) {
        return repository.findById(id).map(mapper::toDTO)
                .orElseThrow(() -> new ResourcesNotFoundException("Post with id " + id + " not found"));
    }

    public PostDTO update(PostPatchDTO data, Long id) {
        var post = repository.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException("Post with id " + id + " not found"));

        if (data.getUserId() != null
                && data.getUserId().isPresent()
                && data.getUserId().get() != null) {

            var newAuthor = usersRepository.findById(data.getUserId().get())
                    .orElseThrow(() -> new ResourcesNotFoundException("User with id " + data.getUserId().get() + " not found"));

            post.setAuthor(newAuthor);
            post.setAuthorName(newAuthor.getUsername());
        }

        if (data.getSlug() != null
                && data.getSlug().isPresent()
                && data.getSlug().get() != null
                && repository.findBySlug(data.getSlug().get()).isPresent()
                && !data.getSlug().get().equals(post.getSlug())) {

            throw new ResourceAlreadyExistException("Slug \"" + data.getSlug().get() + "\" already exists");
        }

        mapper.updateEntityFromDTO(data, post);
        repository.save(post);
        return mapper.toDTO(post);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourcesNotFoundException("Post with id " + id + " not found");
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<TagDTO> getPostTags(Long id) {
        var post = repository.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException("Post with id " + id + " not found"));

        return post.getTags().stream()
                .map(tagMapper::toDTO)
                .toList();
    }

    public TagDTO createTagForPost(Long id, TagCreateDTO data) {
        var post = repository.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException("Post with id " + id + " not found"));

        var tag = tagMapper.toEntity(data);
        tag.setPost(post);

        var savedTag = tagsRepository.save(tag);
        return tagMapper.toDTO(savedTag);
    }
}
