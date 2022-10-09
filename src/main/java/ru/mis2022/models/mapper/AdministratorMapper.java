package ru.mis2022.models.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mis2022.models.dto.administrator.AdministratorDto;
import ru.mis2022.models.entity.Administrator;
import ru.mis2022.models.entity.Role;

import java.util.ArrayList;

@Mapper(componentModel = "spring")
public interface AdministratorMapper {

    @Mapping(target = "birthday", dateFormat = "dd.MM.yyyy")
    @Mapping(target = "password", expression = "java(null)")
    @Mapping(source = "role.name", target = "role")
    AdministratorDto toDto(Administrator administrator);

    @Mapping(source = "role", target = "role")
    @Mapping(source = "administratorDto.id", target = "id")
    @Mapping(target = "birthday", dateFormat = "dd.MM.yyyy")
    Administrator toEntity(AdministratorDto administratorDto, Role role);

}
