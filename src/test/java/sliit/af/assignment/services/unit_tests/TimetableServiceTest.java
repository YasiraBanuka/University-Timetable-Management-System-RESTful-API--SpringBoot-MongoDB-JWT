package sliit.af.assignment.services.unit_tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sliit.af.assignment.dtos.TimetableDto;
import sliit.af.assignment.entities.Course;
import sliit.af.assignment.entities.Location;
import sliit.af.assignment.entities.Timetable;
import sliit.af.assignment.repositories.CourseRepository;
import sliit.af.assignment.repositories.LocationRepository;
import sliit.af.assignment.repositories.TimetableRepository;
import sliit.af.assignment.services.TimetableService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TimetableServiceTest {

    // service class to be tested
    @InjectMocks
    private TimetableService timetableService;

    // mock repository
    @Mock
    private TimetableRepository timetableRepository;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private LocationRepository locationRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_successfully_save_a_timetable() {
        // Given
        TimetableDto timetableDto = TimetableDto.builder()
                .id("1L")
                .courseId("1L")
                .locationId("1L")
                .build();
        Course course = Course.builder()
                .id("1L")
                .name("Software Engineering")
                .code("SE")
                .description("Software Engineering Course")
                .credits(4)
                .faculty("Computing")
                .build();
        Location location = Location.builder()
                .id("1L")
                .locationName("A501")
                .availability("AVAILABLE")
                .build();
        Timetable timetable = Timetable.builder()
                .id("1L")
                .course(course)
                .location(location)
                .build();

        // Mock the calls
        when(courseRepository.findById("1L")).thenReturn(Optional.of(course));
        when(locationRepository.findById("1L")).thenReturn(Optional.of(location));
        when(timetableRepository.save(any(Timetable.class))).thenReturn(timetable);

        // When
        TimetableDto savedTimetable = timetableService.saveTimetable(timetableDto);

        // Then
        assertNotNull(savedTimetable);
        assertEquals(savedTimetable.getId(), "1L");
        assertEquals(savedTimetable.getCourseId(), "1L");
        assertEquals(savedTimetable.getLocationId(), "1L");

        verify(courseRepository, times(1)).findById("1L");
        verify(locationRepository, times(1)).findById("1L");
        verify(timetableRepository, times(1)).save(any(Timetable.class));
    }

    @Test
    public void should_return_all_timetables() {
        // Given
        Timetable timetable = Timetable.builder()
                .id("1L")
                .course(Course.builder()
                        .id("1L")
                        .name("Software Engineering")
                        .code("SE")
                        .description("Software Engineering Course")
                        .credits(4)
                        .faculty("Computing")
                        .build())
                .location(Location.builder()
                        .id("1L")
                        .locationName("A501")
                        .availability("AVAILABLE")
                        .build())
                .build();

        // Mock the calls
        when(timetableRepository.findAll()).thenReturn(java.util.List.of(timetable));

        // When
        var timetables = timetableService.getAllTimetables();

        // Then
        assertEquals(1, timetables.size());

        verify(timetableRepository, times(1)).findAll();
    }

    @Test
    public void should_return_timetable_by_id() {
        // Given
        String timetableId = "1L";
        Timetable timetable = Timetable.builder()
                .id("1L")
                .course(Course.builder()
                        .id("1L")
                        .name("Software Engineering")
                        .code("SE")
                        .description("Software Engineering Course")
                        .credits(4)
                        .faculty("Computing")
                        .build())
                .location(Location.builder()
                        .id("1L")
                        .locationName("A501")
                        .availability("AVAILABLE")
                        .build())
                .build();

        // Mock the calls
        when(timetableRepository.findById(timetableId)).thenReturn(Optional.of(timetable));

        // When
        TimetableDto timetableDto = timetableService.getTimetableById(timetableId);

        // Then
        assertEquals(timetable.getId(), timetableDto.getId());
        assertEquals(timetable.getCourse().getId(), timetableDto.getCourseId());
        assertEquals(timetable.getLocation().getId(), timetableDto.getLocationId());

        verify(timetableRepository, times(1)).findById(timetableId);
    }

    @Test
    public void should_successfully_update_a_timetable() {
        // Given
        String timetableId = "1L";
        TimetableDto timetableDto = TimetableDto.builder()
                .id("1L")
                .courseId("1L")
                .locationId("1L")
                .build();
        Course course = Course.builder()
                .id("1L")
                .name("Software Engineering")
                .code("SE")
                .description("Software Engineering Course")
                .credits(4)
                .faculty("Computing")
                .build();
        Location location = Location.builder()
                .id("1L")
                .locationName("A501")
                .availability("AVAILABLE")
                .build();
        Timetable timetable = Timetable.builder()
                .id("1L")
                .course(course)
                .location(location)
                .build();

        // Mock the calls
        when(timetableRepository.findById(timetableId)).thenReturn(Optional.of(timetable));
        when(courseRepository.findById("1L")).thenReturn(Optional.of(course));
        when(locationRepository.findById("1L")).thenReturn(Optional.of(location));
        when(timetableRepository.save(any(Timetable.class))).thenReturn(timetable);

        // When
        TimetableDto updatedTimetable = timetableService.updateTimetable(timetableId, timetableDto);

        // Then
        assertNotNull(updatedTimetable);
        assertEquals(updatedTimetable.getId(), "1L");
        assertEquals(updatedTimetable.getCourseId(), "1L");
        assertEquals(updatedTimetable.getLocationId(), "1L");

        verify(timetableRepository, times(1)).findById(timetableId);
        verify(courseRepository, times(1)).findById("1L");
        verify(locationRepository, times(1)).findById("1L");
        verify(timetableRepository, times(1)).save(any(Timetable.class));
    }

    @Test
    public void should_successfully_delete_a_timetable() {
        // Given
        String timetableId = "1L";
        Course course = Course.builder()
                .id("1L")
                .name("Software Engineering")
                .code("SE")
                .description("Software Engineering Course")
                .credits(4)
                .faculty("Computing")
                .build();
        Location location = Location.builder()
                .id("1L")
                .locationName("A501")
                .availability("BOOKED")
                .build();
        Timetable timetable = Timetable.builder()
                .id("1L")
                .course(course)
                .location(location)
                .build();

        // Mock the calls
        when(timetableRepository.findById(timetableId)).thenReturn(Optional.of(timetable));

        // When
        timetableService.deleteTimetable(timetableId);

        // Then
        verify(timetableRepository, times(1)).findById(timetableId);
        verify(locationRepository, times(1)).save(any(Location.class));
        verify(timetableRepository, times(1)).delete(any(Timetable.class));
    }

}
