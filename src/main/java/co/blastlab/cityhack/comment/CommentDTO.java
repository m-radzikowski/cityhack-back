package co.blastlab.cityhack.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class CommentDTO {

	@JsonInclude
	private int id;

	@JsonInclude
	private Long raportId;

	@JsonInclude
	private String commentId;

	@JsonInclude
	private ZonedDateTime createdTime;

	@JsonInclude
	private String message;

	@JsonInclude
	private String permalinkUrl;

	@JsonInclude
	private int likeCount;

	@JsonInclude
	private double confidence;

	@JsonInclude
	private String value;
}
