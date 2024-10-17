package com.example.luxmed.mapper;

import com.example.luxmed.dto.TeamDto;
import com.example.luxmed.model.Project;
import com.example.luxmed.model.Team;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TeamMapper {
    TeamMapper INSTANCE = Mappers.getMapper(TeamMapper.class);

    TeamDto toDto(Team team);

    Team toEntity(TeamDto teamDto);

    @AfterMapping
    default void addRelation(@MappingTarget Team team, TeamDto teamDto) {
        if (teamDto.project() != null) {
            Project project = ProjectMapper.INSTANCE.toEntity(teamDto.project());
            project.setTeam(team);
            team.setProject(project);
        }
    }
}