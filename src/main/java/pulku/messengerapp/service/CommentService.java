package pulku.messengerapp.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pulku.messengerapp.database.DatabaseClass;
import pulku.messengerapp.model.Comment;

public class CommentService {

	DatabaseClass db = new DatabaseClass();
	List<Comment> comments = new ArrayList<>();
	
	public List<Comment> getAllComments(int messageId) {
		try {
			ResultSet rs = db.getAllDatas("comment where \"messageId\" = " + messageId);
			while(rs.next()){
				Comment comment = new Comment();
				comment.setId(rs.getInt("id"));
				comment.setCommentMessage(rs.getString("commentMessage"));
				comment.setAuthor(rs.getString("author"));
				comment.setCreated(rs.getDate("created"));
				comments.add(comment);
			}
		}  catch (SQLException e) {
			e.printStackTrace();
		}
		return comments;
	}
	
	public Comment getComment(String commentId){
		Comment comment = new Comment();
		try{
			ResultSet rs = db.getData("comment", "id", commentId);
			while(rs.next()){
				comment.setCommentMessage(rs.getString("commentMessage"));
				comment.setAuthor(rs.getString("author"));
				comment.setCreated(rs.getDate("created"));
			}
		}catch(SQLException e){
			System.err.println("Exception on getComment : " + e);;
		}
		return comment;
	}
	
	public Comment addComment(int messageId, Comment comment){
		String query = "insert into comment (\"commentMessage\", author, created, \"messageId\") values('" + comment.getCommentMessage() + "', '" + comment.getAuthor()+ "', '"+comment.getCreated()+ "', " + messageId + ")";
		db.addData(query);
		return comment;
	}
	
	public Comment updateComment(int messageId, Comment comment){
		String query = "update comment set \"commentMessage\" = '" + comment.getCommentMessage() + "', author = '" + comment.getAuthor() + "', created = '" + comment.getCreated() + "', \"messageId\" = '" + messageId +"' where id = '" + comment.getId()+ "'";
		db.addData(query);
		return comment;
	}
	
	public Comment removeComment(int commentId, Comment comment){
		String query = "delete from comment where id = '" + commentId + "'";
		db.addData(query);
		return comment;
	}
}
