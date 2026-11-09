package hexlet.trainee.springtrainee.hexletSpringBlog.exception;

public class ResourceAlreadyExistException extends RuntimeException {
    public ResourceAlreadyExistException(String message) {
        super(message);
    }
}
