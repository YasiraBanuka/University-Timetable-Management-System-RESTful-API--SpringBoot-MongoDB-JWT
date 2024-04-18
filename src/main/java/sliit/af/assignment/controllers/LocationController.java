package sliit.af.assignment.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sliit.af.assignment.dtos.LocationDto;
import sliit.af.assignment.services.LocationService;

import java.util.List;

@RestController
@RequestMapping("/api/location")
@AllArgsConstructor
public class LocationController {

        private LocationService locationService;

        @PostMapping("/create")
        @ResponseStatus(HttpStatus.CREATED)
        @PreAuthorize("hasAuthority('ADMIN')")
        public ResponseEntity<LocationDto> createLocation(@RequestBody LocationDto locationDto) {
            LocationDto savedLocation = locationService.saveLocation(locationDto);
            return new ResponseEntity<>(savedLocation, HttpStatus.CREATED);
        }

        @GetMapping("/get/{id}")
        @ResponseStatus(HttpStatus.OK)
        @PreAuthorize("hasAuthority('ADMIN')")
        public ResponseEntity<LocationDto> getLocationById(@PathVariable("id") String locationId) {
            LocationDto locationDto = locationService.getLocationById(locationId);
            return ResponseEntity.ok(locationDto);
        }

        @GetMapping("/get")
        @ResponseStatus(HttpStatus.OK)
        @PreAuthorize("hasAuthority('ADMIN')")
        public ResponseEntity<List<LocationDto>> getAllLocations() {
            List<LocationDto> locations = locationService.getAllLocations();
            return ResponseEntity.ok(locations);
        }

        @PutMapping("/update/{id}")
        @ResponseStatus(HttpStatus.OK)
        @PreAuthorize("hasAuthority('ADMIN')")
        public ResponseEntity<LocationDto> updateLocation(@PathVariable("id") String locationId, @RequestBody LocationDto locationDto) {
            LocationDto updatedLocation = locationService.updateLocation(locationId, locationDto);
            return ResponseEntity.ok(updatedLocation);
        }

        @DeleteMapping("/delete/{id}")
        @ResponseStatus(HttpStatus.OK)
        @PreAuthorize("hasAuthority('ADMIN')")
        public ResponseEntity<String> deleteLocation(@PathVariable("id") String locationId) {
            locationService.deleteLocation(locationId);
            return ResponseEntity.ok("Location deleted successfully");
        }

}
