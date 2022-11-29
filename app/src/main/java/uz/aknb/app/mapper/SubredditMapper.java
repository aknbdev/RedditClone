package uz.aknb.app.mapper;

import db.entity.EntSubreddit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import uz.aknb.app.dto.SubredditDto;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

    EntSubreddit dtoToEnt(SubredditDto source);
    SubredditDto entToDto(EntSubreddit source);

    @Mapping(target = "createdDate", ignore = true)
    void update(@MappingTarget EntSubreddit target, SubredditDto source);
}
