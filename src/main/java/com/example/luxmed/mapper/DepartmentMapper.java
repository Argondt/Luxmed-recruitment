package com.example.luxmed.mapper;

import com.example.luxmed.dto.DepartmentDto;
import com.example.luxmed.dto.TeamDto;
import com.example.luxmed.model.Department;
import com.example.luxmed.model.Team;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface DepartmentMapper {
    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);

    Department toEntity(DepartmentDto departmentDto);

    DepartmentDto toDto(Department company);

    @AfterMapping
    default void addRelation(@MappingTarget Department department, DepartmentDto departmentDto) {
        if (departmentDto.teams() != null) {
            List<Team> teams = departmentDto.teams().stream()
                    .map(teamDto -> getTeam(department, teamDto))
                    .collect(Collectors.toList());
            department.setTeams(teams);
        }
    }

    private static Team getTeam(Department department, TeamDto teamDto) {
        Team team = TeamMapper.INSTANCE.toEntity(teamDto);
        team.setDepartment(department);
        return team;
    }
}
