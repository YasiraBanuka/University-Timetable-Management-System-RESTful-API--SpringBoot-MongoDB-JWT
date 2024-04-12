package sliit.af.assignment.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sliit.af.assignment.dtos.LocationDto;
import sliit.af.assignment.entities.Location;
import sliit.af.assignment.exceptions.ResourceNotFoundException;
import sliit.af.assignment.mappers.LocationMapper;
import sliit.af.assignment.repositories.LocationRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class LocationService {

    private LocationRepository locationRepository;

    public LocationDto saveLocation(LocationDto locationDto) {
        Location location = LocationMapper.mapToLocation(locationDto);
        Location savedLocation = locationRepository.save(location);
        return LocationMapper.mapToLocationDto(savedLocation);
    }

    public LocationDto getLocationById(String locationId) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Location not found with id: " + locationId)
                );
        return LocationMapper.mapToLocationDto(location);
    }

    public List<LocationDto> getAllLocations() {
        List<Location> locations = locationRepository.findAll();
        return locations.stream()
                .map(LocationMapper::mapToLocationDto)
                .toList();
    }

    public LocationDto updateLocation(String locationId, LocationDto locationDto) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Location not found with id: " + locationId)
                );

        location.setLocationName(locationDto.getLocationName());
        location.setAvailability(locationDto.getAvailability());
        location.setCapacity(locationDto.getCapacity());

        Location updatedLocation = locationRepository.save(location);
        return LocationMapper.mapToLocationDto(updatedLocation);
    }

    public void deleteLocation(String locationId) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Location not found with id: " + locationId)
                );
        locationRepository.delete(location);
    }

}
