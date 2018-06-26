package co.blastlab.cityhack.comment;

import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/comments")
@CrossOrigin
@Api(value = "cityhack-backend", description = "Operations pertaining to Comments in CityHack Backend")
public class CommentController {

	private CommentRepository commentRepository;

	private ModelMapper modelMapper;

	@Autowired
	public CommentController(ModelMapper modelMapper, CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
		this.modelMapper = modelMapper;
	}

	@ApiOperation(value = "Updates existing comment in database and returns that comment", response = CommentDTO.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Successfully updated comment"),
		@ApiResponse(code = 400, message = "Incorrect data/body"),
		@ApiResponse(code = 500, message = "Failed to add new resource")
	}
	)
	@RequestMapping(method = RequestMethod.PATCH, produces = "application/json")
	public @ResponseBody
	CommentDTO updateComment(@RequestBody CommentDTO data) {
		Comment updatedComment = commentRepository.save(modelMapper.map(data, Comment.class));
		return modelMapper.map(updatedComment, CommentDTO.class);
	}

	@ApiOperation(value = "View a list of all comments", response = CommentDTO.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Successfully retrieved list"),
		@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
		@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	}
	)
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<CommentDTO> getAllComments() {
		List<CommentDTO> commentDTOList = new ArrayList<>();
		for (Comment comment : commentRepository.findAll()) {
			commentDTOList.add(modelMapper.map(comment, CommentDTO.class));
		}
		return commentDTOList;
	}

	@ApiOperation(value = "View a list of all comments for selected raport", response = CommentDTO.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Successfully retrieved list"),
		@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
		@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	}
	)
	@RequestMapping(path = "/{raportId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<CommentDTO> getAllCommentsForRaport(@ApiParam(value = "Get raport by id.", required = true) @PathVariable("raportId") long raportId) {
		List<CommentDTO> commentDTOList = new ArrayList<>();
		for (Comment comment : commentRepository.findCommentsByRaportId(raportId)) {
			commentDTOList.add(modelMapper.map(comment, CommentDTO.class));
		}
		return commentDTOList;
	}

	@ApiOperation(value = "Removes comment by ID", response = CommentDTO.class)
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "Successfully removed comment"),
		@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
		@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	}
	)
	@RequestMapping(path = "/{commentId}", method = RequestMethod.DELETE, produces = "application/json")
	public @ResponseBody
	void deleteRaport(@ApiParam(value = "Get comment by id.", required = true) @PathVariable("commentId") long commentId) {
		Comment comment = commentRepository.findById(commentId).get();
		commentRepository.delete(comment);
	}
}
