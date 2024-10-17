package com.example.luxmed.mapper;

import com.example.luxmed.dto.ManagerDto;
import com.example.luxmed.model.Manager;
import com.example.luxmed.model.Project;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ManagerMapper {
    ManagerMapper INSTANCE = Mappers.getMapper(ManagerMapper.class);

    ManagerDto toDto(Manager team);

    Manager toEntity(ManagerDto teamDto);

    @AfterMapping
    default void addRelation(@MappingTarget Manager manager, ManagerDto managerDto) {
        if (managerDto.project() != null) {
            Project project = ProjectMapper.INSTANCE.toEntity(managerDto.project());
            project.setManager(manager);
            manager.setProject(project);
        }
    }
}
