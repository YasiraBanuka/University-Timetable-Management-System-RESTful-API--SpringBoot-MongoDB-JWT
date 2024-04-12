package sliit.af.assignment.mappers;

import sliit.af.assignment.dtos.TimetableDto;
import sliit.af.assignment.entities.Timetable;

public class TimetableMapper {

    public static TimetableDto mapToTimetableDto(Timetable timetable) {
        return TimetableDto.builder()
                .id(timetable.getId())
                .courseId(timetable.getCourse().getId())
                .startTime(timetable.getStartTime())
                .endTime(timetable.getEndTime())
                .faculty(timetable.getFaculty())
                .locationId(timetable.getLocation().getId())
                .build();
    }

    public static Timetable mapToTimetable(TimetableDto timetableDto) {
        return Timetable.builder()
                .id(timetableDto.getId())
                .startTime(timetableDto.getStartTime())
                .endTime(timetableDto.getEndTime())
                .faculty(timetableDto.getFaculty())
                .build();
    }

}
