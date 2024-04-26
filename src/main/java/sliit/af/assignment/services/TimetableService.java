package sliit.af.assignment.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sliit.af.assignment.dtos.TimetableDto;
import sliit.af.assignment.entities.Course;
import sliit.af.assignment.entities.Location;
import sliit.af.assignment.entities.Timetable;
import sliit.af.assignment.exceptions.ResourceNotFoundException;
import sliit.af.assignment.mappers.TimetableMapper;
import sliit.af.assignment.repositories.CourseRepository;
import sliit.af.assignment.repositories.LocationRepository;
import sliit.af.assignment.repositories.TimetableRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class TimetableService {

    private TimetableRepository timetableRepository;
    private CourseRepository courseRepository;
    private LocationRepository locationRepository;

    public TimetableDto saveTimetable(TimetableDto timetableDto) {
        Timetable timetable = TimetableMapper.mapToTimetable(timetableDto);

        // Fetch course details from the repository using the provided course ID
        Course course = courseRepository.findById(timetableDto.getCourseId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Course not found with id: " + timetableDto.getCourseId()));
        timetable.setCourse(course);

        // Fetch location details from the repository using the provided location ID
        Location location = locationRepository.findById(timetableDto.getLocationId())
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + timetableDto.getLocationId()));

        // Check if the location is available
        if (location.getAvailability().equals("AVAILABLE")) {
            timetable.setLocation(location);
            // Set location availability to BOOKED
            location.setAvailability("BOOKED");
            locationRepository.save(location);
        } else {
            System.out.println("Location not available");
            throw new ResourceNotFoundException("Location not available");
        }

        // Save the timetable
        Timetable savedTimetable = timetableRepository.save(timetable);

        System.out.println("Timetable saved: " + savedTimetable);

        // Map the saved timetable to TimetableDto
        return TimetableMapper.mapToTimetableDto(savedTimetable);
    }

    public TimetableDto getTimetableById(String timetableId) {
        Timetable timetable = timetableRepository.findById(timetableId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Timetable not found with id: " + timetableId)
                );
        return TimetableMapper.mapToTimetableDto(timetable);
    }

    public List<TimetableDto> getAllTimetables() {
        List<Timetable> timetables = timetableRepository.findAll();
        return timetables.stream()
                .map(TimetableMapper::mapToTimetableDto)
                .toList();
    }

    public TimetableDto updateTimetable(String timetableId, TimetableDto timetableDto) {
        // Check if the timetable exists in the database
        Timetable existingTimetable = timetableRepository.findById(timetableId)
                .orElseThrow(() -> new ResourceNotFoundException("Timetable not found with id: " + timetableId));

        // Update the existing timetable with the new data
        existingTimetable.setStartTime(timetableDto.getStartTime());
        existingTimetable.setEndTime(timetableDto.getEndTime());

        // Fetch location details from the repository using the provided location ID
        Location location = locationRepository.findById(timetableDto.getLocationId())
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + timetableDto.getLocationId()));
        // Set location to the timetable
        existingTimetable.setLocation(location);

        // Fetch course details from the repository using the provided course ID
        Course course = courseRepository.findById(timetableDto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + timetableDto.getCourseId()));
        // Set course to the timetable
        existingTimetable.setCourse(course);

        // Save the updated timetable
        Timetable updatedTimetable = timetableRepository.save(existingTimetable);

        System.out.println("Timetable updated: " + updatedTimetable);

        // Map the updated timetable to TimetableDto
        return TimetableMapper.mapToTimetableDto(updatedTimetable);
    }


    public void deleteTimetable(String timetableId) {
        // Check if the timetable exists in the database
        Timetable timetable = timetableRepository.findById(timetableId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Timetable not found with id: " + timetableId)
                );

        // Set location availability to AVAILABLE
        Location location = timetable.getLocation();
        location.setAvailability("AVAILABLE");
        locationRepository.save(location);

        // Delete the timetable
        timetableRepository.delete(timetable);
        System.out.println("Timetable deleted");
    }

}
