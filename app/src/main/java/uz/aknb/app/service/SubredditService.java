package uz.aknb.app.service;

import db.entity.EntSubreddit;
import db.repository.SubredditRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.aknb.app.dto.SubredditDto;
import uz.aknb.app.mapper.SubredditMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubredditService {

    private final SubredditMapper mapper;
    private final SubredditRepository repository;

    public SubredditService(SubredditMapper mapper,
                            SubredditRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Transactional
    public EntSubreddit save(SubredditDto dto) {

        EntSubreddit subreddit = new EntSubreddit();
        if (dto.getId() != null) {

            mapper.update(subreddit, dto);
        } else {

            subreddit = mapper.dtoToEnt(dto);
        }
        return repository.save(subreddit);
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return repository
                .findAll().stream()
                .map(mapper::entToDto)
                .collect(Collectors.toList());
    }
}
