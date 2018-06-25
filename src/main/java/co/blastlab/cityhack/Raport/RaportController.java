package co.blastlab.cityhack.Raport;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api(value = "cityhack-backend", description = "Operations pertaining to Raports and Raport Generations in CityHack Backend")
public class RaportController {

	private RaportRepository raportRepository;

	private ModelMapper modelMapper;

	@Autowired
	public RaportController(ModelMapper modelMapper, RaportRepository raportRepository) {
		this.raportRepository = raportRepository;
		this.modelMapper = modelMapper;
	}

	@ApiOperation(value = "View a list of all raports", response = RaportDTO.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Successfully retrieved list"),
		@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
		@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	}
	)
	@RequestMapping(value = "/getAllRaports", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<RaportDTO> getAllRaports() {
		List<RaportDTO> raportDTOList = new ArrayList<>();
		for (Raport raport : raportRepository.findAll()) {
			raportDTOList.add(modelMapper.map(raport, RaportDTO.class));
		}
		return raportDTOList;
	}
}
