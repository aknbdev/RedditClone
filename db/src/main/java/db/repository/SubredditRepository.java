package db.repository;

import db.entity.EntSubreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubredditRepository extends JpaRepository<EntSubreddit, Long> {
}
