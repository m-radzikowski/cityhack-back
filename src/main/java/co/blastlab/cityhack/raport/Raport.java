package co.blastlab.cityhack.raport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "Raport")
@Table(name = "raport")
@Setter
@Getter
@NoArgsConstructor
public class Raport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String url;
}
