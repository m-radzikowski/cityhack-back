package co.blastlab.cityhack.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "Comment")
@Table(name = "comment")
@Setter
@Getter
@NoArgsConstructor
public class Comment {

	static int POSITIVE = 1;
	static int NEUTRAL = 0;
	static int NEGATIVE = -1;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long raportId;

	private int opinion;

	private String content;
}
