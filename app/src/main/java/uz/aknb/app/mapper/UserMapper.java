package uz.aknb.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import uz.aknb.app.dto.RegisterRequest;
import db.entity.EntUser;

@Mapper(componentModel = "spring")
public interface UserMapper {


    EntUser fromRegisterRequestToEntUser (RegisterRequest source);

    @Mapping(target = "password", ignore = true)
    void update(@MappingTarget EntUser target, RegisterRequest source);
}
