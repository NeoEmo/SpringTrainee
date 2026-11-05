package hexlet.trainee.springtrainee.components;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "config.users")
@Getter
@Setter
public class UserProperties {
    private List<String> names;
}
