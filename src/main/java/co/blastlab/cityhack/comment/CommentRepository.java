package co.blastlab.cityhack.comment;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

	List<Comment> findCommentsByRaportId(Long raportId);
}
