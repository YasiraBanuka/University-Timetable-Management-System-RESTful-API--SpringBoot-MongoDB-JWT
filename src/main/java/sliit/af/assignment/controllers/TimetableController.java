package sliit.af.assignment.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sliit.af.assignment.dtos.TimetableDto;
import sliit.af.assignment.services.TimetableService;

import java.util.List;

@RestController
@RequestMapping("/api/timetable")
@AllArgsConstructor
public class TimetableController {

    private TimetableService timetableService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TimetableDto> createTimetable(@RequestBody TimetableDto timetableDto) {
        TimetableDto savedTimetable = timetableService.saveTimetable(timetableDto);
        return new ResponseEntity<>(savedTimetable, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('STUDENT')")
    public ResponseEntity<TimetableDto> getTimetableById(@PathVariable("id") String timetableId) {
        TimetableDto timetableDto = timetableService.getTimetableById(timetableId);
        return ResponseEntity.ok(timetableDto);
    }

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('STUDENT')")
    public ResponseEntity<List<TimetableDto>> getAllTimetables() {
        List<TimetableDto> timetables = timetableService.getAllTimetables();
        return ResponseEntity.ok(timetables);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<TimetableDto> updateTimetable(@PathVariable("id") String timetableId, @RequestBody TimetableDto timetableDto) {
        TimetableDto updatedTimetable = timetableService.updateTimetable(timetableId, timetableDto);
        return ResponseEntity.ok(updatedTimetable);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteTimetable(@PathVariable("id") String timetableId) {
        timetableService.deleteTimetable(timetableId);
        return ResponseEntity.ok("Timetable deleted successfully");
    }

}
