package sliit.af.assignment.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
    public void testGetAllCourses() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/course/get")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCreateCourse() throws Exception {
        String courseJson = "{ \"name\": \"Software Engineering\", \"code\": \"SE\", \"description\": \"Software Engineering Course\", \"credits\": 3, \"faculty\": \"Faculty of Computing\" }";

        mockMvc
                .perform(MockMvcRequestBuilders.post("/api/course/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

}
