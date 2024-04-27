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
 * Integration tests for {@link EnrollmentController}
 */
@SpringBootTest
@AutoConfigureMockMvc
public class EnrollmentControllerIntegrationTest {

    private final MockMvc mockMvc;

    @Autowired
    public EnrollmentControllerIntegrationTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"STUDENT"})
    public void testEnrollCourse() throws Exception {
        String enrollmentJson = "{ \"courseId\": \"6607ac6502825f43602b6178\", \"studentId\": \"661f30afa2b1ee3ad8b0ad90\" }";

        mockMvc
                .perform(MockMvcRequestBuilders.post("/api/enrollment/enroll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(enrollmentJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"STUDENT", "ADMIN"})
    public void testGetAllEnrollments() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/api/enrollment/get")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
