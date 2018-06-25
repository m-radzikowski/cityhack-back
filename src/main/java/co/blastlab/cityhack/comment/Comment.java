package co.blastlab.cityhack.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity(name = "Comment")
@Table(name = "comment")
@Setter
@Getter
@NoArgsConstructor
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long raportId;

	private int opinion;

	private String content;

	private String commentId;
	private ZonedDateTime createdTime;
	private String message;
	private String permalinkUrl;
	private int likeCount;

	private double confidence;
	private String value;
}
