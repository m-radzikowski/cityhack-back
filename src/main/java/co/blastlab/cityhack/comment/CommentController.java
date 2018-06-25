package co.blastlab.cityhack.comment;

import co.blastlab.cityhack.raport.Raport;
import co.blastlab.cityhack.raport.RaportDTO;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api(value = "cityhack-backend", description = "Operations pertaining to Comments in CityHack Backend")
public class CommentController {

	private CommentRepository commentRepository;

	private ModelMapper modelMapper;

	@Autowired
	public CommentController(ModelMapper modelMapper, CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
		this.modelMapper = modelMapper;
	}

	@ApiOperation(value = "View a list of all comments", response = RaportDTO.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Successfully retrieved list"),
		@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
		@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	}
	)
	@RequestMapping(value = "/getAllComments", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<CommentDTO> getAllComments() {
		List<CommentDTO> commentDTOList = new ArrayList<>();
		for (Comment comment : commentRepository.findAll()) {
			commentDTOList.add(modelMapper.map(comment, CommentDTO.class));
		}
		return commentDTOList;
	}

	@ApiOperation(value = "View a list of all comments for selected raport", response = RaportDTO.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Successfully retrieved list"),
		@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
		@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	}
	)
	@RequestMapping(value = "/getAllCommentsForRaport/{raportId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<CommentDTO> getAllCommentsForRaport(@ApiParam(value = "Get raport by id.", required = true) @PathVariable("raportId") long eventId) {
		List<CommentDTO> commentDTOList = new ArrayList<>();
		for (Comment comment : commentRepository.findAll()) {
			commentDTOList.add(modelMapper.map(comment, CommentDTO.class));
		}
		return commentDTOList;
	}
}
