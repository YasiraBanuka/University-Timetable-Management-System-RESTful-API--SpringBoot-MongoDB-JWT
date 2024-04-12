package sliit.af.assignment.mappers;

import sliit.af.assignment.dtos.ResourceDto;
import sliit.af.assignment.entities.Resource;

public class ResourceMapper {

    public static ResourceDto mapToResourceDto(Resource resource) {
        return ResourceDto.builder()
                .id(resource.getId())
                .resourceName(resource.getResourceName())
                .availability(resource.getAvailability())
                .build();
    }

    public static Resource mapToResource(ResourceDto resourceDto) {
        return Resource.builder()
                .id(resourceDto.getId())
                .resourceName(resourceDto.getResourceName())
                .availability(resourceDto.getAvailability())
                .build();
    }

}
