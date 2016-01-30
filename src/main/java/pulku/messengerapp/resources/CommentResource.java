package pulku.messengerapp.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import pulku.messengerapp.model.Comment;
import pulku.messengerapp.service.CommentService;

@Path("/")
@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
public class CommentResource {

	private CommentService commentService = new CommentService();

	@GET
	public List<Comment> getAllComments(@PathParam("messageId") int messageId){
		return commentService.getAllComments(messageId);
	}
	
	@POST
	public Comment addComment(@PathParam("messageId") int messageId, Comment comment){
		return commentService.addComment(messageId, comment);
	}
	
	@PUT
	@Path("/{commentId}")
	public Comment updateComment(@PathParam("messageId") int messageId, @PathParam("commentId")int commentId, Comment comment){
		comment.setId(commentId);
		return commentService.updateComment(messageId, comment);
	}
	@DELETE
	@Path("/{commentId}")
	public void deleteComment(@PathParam("commentId") int commentId, Comment comment){
		commentService.removeComment(commentId, comment);
	}
	
	@GET
	@Path("/{commentId}")
	public Comment getComment(@PathParam("messageId") int messageId,@PathParam("commentId") String commentId){
		return commentService.getComment(commentId);
	}
	
}
