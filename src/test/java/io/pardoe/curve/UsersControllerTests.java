package io.pardoe.curve;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.pardoe.curve.models.Repository;
import io.pardoe.curve.models.User;
import io.pardoe.curve.services.GitHubService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsersControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GitHubService gitHubService;

    private HashMap<String, User> users = new HashMap<>();
    private HashMap<String, Repository> repositories = new HashMap<>();

    @Before
    public void setUp() {
        this.buildTestData();
        this.prepareMocks();
    }

    @Test
    public void paramShouldReturnCorrectUsers() throws Exception {
        this.mockMvc.perform(
                get("/users/{fromUsername}/contributionpath/{toUsername}",
                        "userone", "usertwo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fromUser.username").value("userone"))
                .andExpect(jsonPath("$.toUser.username").value("usertwo"));
    }

    @Test
    public void paramsShouldReturnSinglePath() throws Exception {
        this.mockMvc.perform(
                get("/users/{fromUsername}/contributionpath/{toUsername}",
                        "userone", "usertwo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pathLength").value(1))
                .andExpect(jsonPath("$.repositories[0].name").value("projectone"));
    }

    @Test
    public void paramsShouldReturnMultiplePathOne() throws Exception {
        this.mockMvc.perform(
                get("/users/{fromUsername}/contributionpath/{toUsername}",
                        "userone", "userfour"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pathLength").value(2))
                .andExpect(jsonPath("$.repositories[0].name").value("projectone"))
                .andExpect(jsonPath("$.repositories[1].name").value("projectfive"));
    }

    @Test
    public void paramsShouldReturnMultiplePathTwo() throws Exception {
        this.mockMvc.perform(
                get("/users/{fromUsername}/contributionpath/{toUsername}",
                        "usertwo", "userthree"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pathLength").value(2))
                .andExpect(jsonPath("$.repositories[0].name").value("projectone"))
                .andExpect(jsonPath("$.repositories[1].name").value("projecttwo"));
    }

    @Test
    public void paramsShouldReturnNoPath() throws Exception {
        this.mockMvc.perform(
                get("/users/{fromUsername}/contributionpath/{toUsername}",
                        "userone", "usersix"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pathLength").value(0));
    }

    private void buildTestData() {

        User userOne = new User("userone");
        User userTwo = new User("usertwo");
        User userThree = new User("userthree");
        User userFour = new User("userfour");
        User userFive = new User("userfive");
        User userSix = new User("usersix");

        Repository projectOne = new Repository("projectone");
        Repository projectTwo = new Repository("projecttwo");
        Repository projectThree = new Repository("projectthree");
        Repository projectFour = new Repository("projectfour");
        Repository projectFive = new Repository("projectfive");
        Repository projectSix = new Repository("projectsix");

        userOne.setRepositories(new ArrayList<Repository>() {{
            add(projectOne);
            add(projectTwo);
            add(projectThree);
            add(projectFour);
        }});

        userTwo.setRepositories(new ArrayList<Repository>() {{
            add(projectOne);
            add(projectFive);
        }});

        userThree.setRepositories(new ArrayList<Repository>() {{
            add(projectTwo);
        }});

        userFour.setRepositories(new ArrayList<Repository>() {{
            add(projectFive);
        }});

        userFive.setRepositories(new ArrayList<Repository>() {{
            add(projectFour);
            add(projectFive);
        }});

        userSix.setRepositories(new ArrayList<Repository>() {{
            add(projectSix);
        }});

        projectOne.setUsers(new ArrayList<User>() {{
            add(userOne);
            add(userTwo);
        }});

        projectTwo.setUsers(new ArrayList<User>() {{
            add(userOne);
            add(userThree);
        }});

        projectThree.setUsers(new ArrayList<User>() {{
            add(userOne);
        }});

        projectFour.setUsers(new ArrayList<User>() {{
            add(userOne);
            add(userFive);
        }});

        projectFive.setUsers(new ArrayList<User>() {{
            add(userTwo);
            add(userFour);
            add(userFive);
        }});

        projectSix.setUsers(new ArrayList<User>() {{
            add(userSix);
        }});

        users.put("userone", userOne);
        users.put("usertwo", userTwo);
        users.put("userthree", userThree);
        users.put("userfour", userFour);
        users.put("userfive", userFive);
        users.put("usersix", userSix);

        repositories.put("projectone", projectOne);
        repositories.put("projecttwo", projectTwo);
        repositories.put("projectthree", projectThree);
        repositories.put("projectfour", projectFour);
        repositories.put("projectfive", projectFive);
        repositories.put("projectsix", projectSix);
    }

    private void prepareMocks() {
        given(this.gitHubService.getUser("userone")).willReturn(
                this.users.get("userone"));
        given(this.gitHubService.getUser("usertwo")).willReturn(
                this.users.get("usertwo"));
        given(this.gitHubService.getUser("userthree")).willReturn(
                this.users.get("userthree"));
        given(this.gitHubService.getUser("userfour")).willReturn(
                this.users.get("userfour"));
        given(this.gitHubService.getUser("userfive")).willReturn(
                this.users.get("userfive"));
        given(this.gitHubService.getUser("usersix")).willReturn(
                this.users.get("usersix"));

        given(this.gitHubService.getRepository("projectone")).willReturn(
                this.repositories.get("projectone"));
        given(this.gitHubService.getRepository("projecttwo")).willReturn(
                this.repositories.get("projecttwo"));
        given(this.gitHubService.getRepository("projectthree")).willReturn(
                this.repositories.get("projectthree"));
        given(this.gitHubService.getRepository("projectfour")).willReturn(
                this.repositories.get("projectfour"));
        given(this.gitHubService.getRepository("projectfive")).willReturn(
                this.repositories.get("projectfive"));
        given(this.gitHubService.getRepository("projectsix")).willReturn(
                this.repositories.get("projectsix"));
    }
}
