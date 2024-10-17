package com.example.luxmed.mapper;

import com.example.luxmed.dto.ProjectDto;
import com.example.luxmed.model.Manager;
import com.example.luxmed.model.Project;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProjectMapper {
    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    ProjectDto toDto(Project team);

    Project toEntity(ProjectDto teamDto);

    @AfterMapping
    default void addRelation(@MappingTarget Project project, ProjectDto projectDto) {
        if (projectDto.manager() != null) {
            Manager manager = ManagerMapper.INSTANCE.toEntity(projectDto.manager());
            manager.setProject(project);
            project.setManager(manager);
        }
    }
}