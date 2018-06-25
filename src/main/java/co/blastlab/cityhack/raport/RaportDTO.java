package co.blastlab.cityhack.raport;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RaportDTO {

	@JsonInclude
	private int id;

	@JsonInclude
	private String name;

	@JsonInclude
	private String url;
}
