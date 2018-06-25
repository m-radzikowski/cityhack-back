package co.blastlab.cityhack.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {

	@JsonInclude
	private int id;

	@JsonInclude
	private int raportId;

	@JsonInclude
	private int opinion;

	@JsonInclude
	private String content;
}
