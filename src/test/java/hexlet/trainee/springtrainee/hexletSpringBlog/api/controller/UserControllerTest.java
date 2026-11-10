package hexlet.trainee.springtrainee.hexletSpringBlog.api.controller;

import hexlet.trainee.springtrainee.hexletSpringBlog.model.User;

import hexlet.trainee.springtrainee.hexletSpringBlog.repository.UsersRepository;
import net.datafaker.Faker;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Faker faker;

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testIndex() throws Exception {
        var result = mockMvc.perform(get("/api/users")).andExpect(status().isOk()).andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).node("content").isArray();
    }

    @Test
    public void TestUpdate() throws Exception {
        var user = Instancio.of(User.class)
                .ignore(Select.field(User::getId))
                .supply(Select.field(User::getEmail), () -> faker.internet().emailAddress())
                .supply(Select.field(User::getPassword), () -> faker.credentials().password(10, 100))
                .create();

        userRepository.save(user);

        var data = new HashMap<>();
        data.put("username", "HellKnight");

        var request = patch("/api/users/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(data));

        mockMvc.perform(request).andExpect(status().isOk());
        user = userRepository.findById(user.getId()).get();
        assertThat(user.getUsername()).isEqualTo(data.get("username"));
    }

    @Test
    public void TestDelete() throws Exception {
        var user = Instancio.of(User.class)
                .ignore(Select.field(User::getId))
                .supply(Select.field(User::getEmail), () -> faker.internet().emailAddress())
                .supply(Select.field(User::getPassword), () -> faker.credentials().password(10, 100))
                .supply(Select.field(User::getUsername), () -> faker.fallout().character())
                .create();
        userRepository.save(user);
        var request = delete("/api/users/" + user.getId());

        mockMvc.perform(request).andExpect(status().isNoContent());

        var request2 = delete("/api/users/" + user.getId());

        mockMvc.perform(request2).andExpect(status().isNotFound());

        var request3 = get("/api/users/" + user.getId());

        mockMvc.perform(request3).andExpect(status().isNotFound());
    }

    @Test
    public void TestCreate() throws Exception {
        var data = new HashMap<>();
        data.put("username", faker.fallout().character());
        data.put("email", faker.internet().emailAddress());
        data.put("password", faker.credentials().password(10, 100));

        var request = post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(data));

        mockMvc.perform(request).andExpect(status().isCreated());

        var request2 = post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(data));

        mockMvc.perform(request2).andExpect(status().isConflict());

        var user = userRepository.findByUsername(data.get("username").toString()).get();
        var request3 = get("/api/users/"  + user.getId());
        mockMvc.perform(request3).andExpect(status().isOk());
        assertThat(user.getUsername()).isEqualTo(data.get("username"));
    }

    @Test
    public void TestShow() throws Exception {
        var user = Instancio.of(User.class)
                .ignore(Select.field(User::getId))
                .supply(Select.field(User::getEmail), () -> faker.internet().emailAddress())
                .supply(Select.field(User::getPassword), () -> faker.credentials().password(10, 100))
                .supply(Select.field(User::getUsername), () -> faker.fallout().character())
                .create();

        userRepository.save(user);

        var  request = get("/api/users/" + user.getId());
        mockMvc.perform(request).andExpect(status().isOk());

        var request2 = get("/api/users/444");
        mockMvc.perform(request2).andExpect(status().isNotFound());
    }
}
