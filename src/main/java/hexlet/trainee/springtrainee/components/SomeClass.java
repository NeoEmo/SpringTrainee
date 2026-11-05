package hexlet.trainee.springtrainee.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SomeClass {
    @Autowired
    private DefaultUserProperties userInfo;

    public void getInfo() {
        System.out.println(userInfo.getFullName());
        System.out.println(userInfo.getAge());
    }
}
