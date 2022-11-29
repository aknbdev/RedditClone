package db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import db.entity.EntComment;

@Repository
public interface CommentRepository extends JpaRepository<EntComment, Long> {
}
