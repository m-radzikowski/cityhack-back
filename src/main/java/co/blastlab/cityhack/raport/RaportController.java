package co.blastlab.cityhack.raport;

import co.blastlab.cityhack.Application;
import co.blastlab.cityhack.comment.Comment;
import co.blastlab.cityhack.comment.CommentDTO;
import co.blastlab.cityhack.comment.CommentRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/raports")
@Api(value = "cityhack-backend", description = "Operations pertaining to Raports and Raport Generations in CityHack Backend")
public class RaportController {

	private RaportRepository raportRepository;
	private CommentRepository commentRepository;
	private ModelMapper modelMapper;

	@Autowired
	public RaportController(ModelMapper modelMapper, RaportRepository raportRepository, CommentRepository commentRepository) {
		this.raportRepository = raportRepository;
		this.commentRepository = commentRepository;
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
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<RaportDTO> getAllRaports() {
		List<RaportDTO> raportDTOList = new ArrayList<>();
		for (Raport raport : raportRepository.findAll()) {
			raportDTOList.add(modelMapper.map(raport, RaportDTO.class));
		}
		return raportDTOList;
	}

	@ApiOperation(value = "View a single raport", response = RaportDTO.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Successfully retrieved raport"),
		@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
		@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	}
	)
	@RequestMapping(path = "/{raportId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	RaportDTO getRaport(@ApiParam(value = "Get raport by id.", required = true) @PathVariable("raportId") long raportId) {
		return modelMapper.map(raportRepository.findById(raportId).get(), RaportDTO.class);
	}

	@ApiOperation(value = "Adds new raport to database and returns that raport", response = RaportDTO.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Successfully added raport"),
		@ApiResponse(code = 400, message = "Incorrect data/body"),
		@ApiResponse(code = 500, message = "Failed to add new resource")
	}
	)
	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	RaportDTO addEvent(@RequestBody RaportDTO data) {
		Raport newRaport = raportRepository.save(modelMapper.map(data, Raport.class));
		return modelMapper.map(newRaport, RaportDTO.class);
	}

	@ApiOperation(value = "Removes rapport by ID", response = RaportDTO.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Successfully removed raport"),
		@ApiResponse(code = 400, message = "Incorrect data/body"),
		@ApiResponse(code = 500, message = "Failed to add new resource")
	}
	)
	@RequestMapping(path = "/{raportId}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody
	void deleteRaport(@ApiParam(value = "Get raport by id.", required = true) @PathVariable("raportId") long raportId) {
		Raport raport = raportRepository.findById(raportId).get();
		raportRepository.delete(raport);
	}

	@ApiOperation(value = "Generates rapport by ID", response = RaportDTO.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Successfully generated raport data"),
		@ApiResponse(code = 400, message = "Incorrect data/body"),
		@ApiResponse(code = 500, message = "Failed to add new resource")
	}
	)
	@RequestMapping(path = "/generate/{raportId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	void generateRaport(@ApiParam(value = "Generate raport by id.", required = true) @PathVariable("raportId") long raportId) {
		Raport raport = raportRepository.findById(raportId).get();
		generateRaport(raport);
	}

	void generateRaport(Raport raport) {
		final String uri = Application.FACEBK_URI + "/?postUrl=" + raport.getUrl();

		RestTemplate restTemplate = new RestTemplateBuilder()
			.rootUri(uri)
			.build();

		String json = restTemplate.getForObject(uri, String.class);
		try {
			List<CommentDTO> comments = new ObjectMapper().readValue(json, new TypeReference<List<CommentDTO>>() {});
			for (CommentDTO commentDTO : comments) {
				Comment comment = modelMapper.map(commentDTO, Comment.class);
				comment.setRaportId(raport.getId());

				commentRepository.save(comment);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
