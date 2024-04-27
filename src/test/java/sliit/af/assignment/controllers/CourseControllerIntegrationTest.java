package sliit.af.assignment.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Integration tests for {@link CourseController}
 */
@SpringBootTest
@AutoConfigureMockMvc
public class CourseControllerIntegrationTest {

    private final MockMvc mockMvc;

    @Autowired
    public CourseControllerIntegrationTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    public void testGetAllCourses() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/course/get")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    public void testCreateCourse() throws Exception {
        String courseJson = "{ \"name\": \"Software Process\", \"code\": \"SP\", \"description\": \"Software Process Course\", \"credits\": 3, \"faculty\": \"Faculty of Computing\" }";

        mockMvc
                .perform(MockMvcRequestBuilders.post("/api/course/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER", "ADMIN"})
    public void testGetCourseById() throws Exception {
        String courseId = "65ffcf99ec2e485b6c8cf01c";

        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/course/get/" + courseId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(courseId));
    }

}
