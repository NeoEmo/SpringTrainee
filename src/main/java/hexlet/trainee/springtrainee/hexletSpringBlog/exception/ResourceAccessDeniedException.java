package hexlet.trainee.springtrainee.hexletSpringBlog.exception;

public class ResourceAccessDeniedException extends RuntimeException {
    public ResourceAccessDeniedException(String message) {
        super(message);
    }
}
