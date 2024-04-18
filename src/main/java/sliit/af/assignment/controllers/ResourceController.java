package sliit.af.assignment.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sliit.af.assignment.dtos.ResourceDto;
import sliit.af.assignment.services.ResourceService;

import java.util.List;

@RestController
@RequestMapping("/api/resource")
@AllArgsConstructor
public class ResourceController {

    private ResourceService resourceService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResourceDto> createResource(@RequestBody ResourceDto resourceDto) {
        ResourceDto savedResource = resourceService.saveResource(resourceDto);
        return new ResponseEntity<>(savedResource, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResourceDto> getResourceById(@PathVariable("id") String resourceId) {
        ResourceDto resourceDto = resourceService.getResourceById(resourceId);
        return ResponseEntity.ok(resourceDto);
    }

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<ResourceDto>> getAllResources() {
        List<ResourceDto> resources = resourceService.getAllResources();
        return ResponseEntity.ok(resources);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResourceDto> updateResource(@PathVariable("id") String resourceId, @RequestBody ResourceDto resourceDto) {
        ResourceDto updatedResource = resourceService.updateResource(resourceId, resourceDto);
        return ResponseEntity.ok(updatedResource);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteResource(@PathVariable("id") String resourceId) {
        resourceService.deleteResource(resourceId);
        return ResponseEntity.ok("Resource deleted successfully");
    }

}
