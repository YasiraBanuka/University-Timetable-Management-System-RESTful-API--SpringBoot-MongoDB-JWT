package sliit.af.assignment.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sliit.af.assignment.dtos.EnrollmentDto;
import sliit.af.assignment.services.EnrollmentService;

import java.util.List;

@RestController
@RequestMapping("/api/enrollment")
@AllArgsConstructor
public class EnrollmentController {

    private EnrollmentService enrollmentService;

    @PostMapping("/enroll")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<EnrollmentDto> enrollCourse(@RequestBody EnrollmentDto enrollmentDto) {
        EnrollmentDto savedEnrollment = enrollmentService.enrollCourse(enrollmentDto);
        return new ResponseEntity<>(savedEnrollment, HttpStatus.CREATED);
    }

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('STUDENT') || hasAuthority('ADMIN')")
    public ResponseEntity<List<EnrollmentDto>> getAllEnrollments() {
        List<EnrollmentDto> enrollments = enrollmentService.getAllEnrollments();
        return ResponseEntity.ok(enrollments);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteEnrollment(@PathVariable("id") String enrollmentId) {
        enrollmentService.deleteEnrollment(enrollmentId);
        return ResponseEntity.ok("Enrollment deleted successfully");
    }

}
