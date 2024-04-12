package sliit.af.assignment.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sliit.af.assignment.dtos.ResourceDto;
import sliit.af.assignment.entities.Resource;
import sliit.af.assignment.exceptions.ResourceNotFoundException;
import sliit.af.assignment.mappers.ResourceMapper;
import sliit.af.assignment.repositories.ResourceRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ResourceService {

    private ResourceRepository resourceRepository;

    public ResourceDto saveResource(ResourceDto resourceDto) {
        Resource resource = ResourceMapper.mapToResource(resourceDto);
        Resource savedResource = resourceRepository.save(resource);
        return ResourceMapper.mapToResourceDto(savedResource);
    }

    public ResourceDto getResourceById(String resourceId) {
        Resource resource = resourceRepository.findById(resourceId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Resource not found with id: " + resourceId)
                );
        return ResourceMapper.mapToResourceDto(resource);
    }

    public List<ResourceDto> getAllResources() {
        List<Resource> resources = resourceRepository.findAll();
        return resources.stream()
                .map(ResourceMapper::mapToResourceDto)
                .toList();
    }

    public ResourceDto updateResource(String resourceId, ResourceDto resourceDto) {
        Resource resource = resourceRepository.findById(resourceId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Resource not found with id: " + resourceId)
                );

        resource.setResourceName(resourceDto.getResourceName());
        resource.setAvailability(resourceDto.getAvailability());

        Resource updatedResource = resourceRepository.save(resource);
        return ResourceMapper.mapToResourceDto(updatedResource);
    }

    public void deleteResource(String resourceId) {
        Resource resource = resourceRepository.findById(resourceId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Resource not found with id: " + resourceId)
                );
        resourceRepository.delete(resource);
    }

}
