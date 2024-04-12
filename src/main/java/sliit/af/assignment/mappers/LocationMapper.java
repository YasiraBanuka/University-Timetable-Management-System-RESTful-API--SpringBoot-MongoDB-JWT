package sliit.af.assignment.mappers;

import sliit.af.assignment.dtos.LocationDto;
import sliit.af.assignment.entities.Location;

public class LocationMapper {

        public static LocationDto mapToLocationDto(Location location) {
            return LocationDto.builder()
                    .id(location.getId())
                    .locationName(location.getLocationName())
                    .availability(location.getAvailability())
                    .capacity(location.getCapacity())
                    .build();
        }

        public static Location mapToLocation(LocationDto locationDto) {
            return Location.builder()
                    .id(locationDto.getId())
                    .locationName(locationDto.getLocationName())
                    .availability(locationDto.getAvailability())
                    .capacity(locationDto.getCapacity())
                    .build();
        }

}
