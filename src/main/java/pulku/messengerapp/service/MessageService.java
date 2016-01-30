package pulku.messengerapp.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pulku.messengerapp.database.DatabaseClass;
import pulku.messengerapp.model.Message;

public class MessageService {

	DatabaseClass db = new DatabaseClass();
	List<Message> messages = new ArrayList<>();
	public MessageService(){
	
	}
	
	public List<Message> getAllMessages(){
		ResultSet rs = db.getAllDatas("message");
		try {
			while(rs.next()){
				Message message = new Message();
				message.setId(rs.getInt("id"));
				message.setMessage(rs.getString("message"));
				message.setAuthor(rs.getString("author"));
				message.setCreated(rs.getDate("created"));
				messages.add(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return messages;
	}
	
	public List<Message> getAllMessagesForYear(int year){
		List<Message> messagesForYear = new ArrayList<Message>();
		Calendar cal = Calendar.getInstance();
		getAllMessages();
		for(Message message:messages){
			cal.setTime(message.getCreated());
			if(cal.get(Calendar.YEAR) == year){
				messagesForYear.add(message);
			}
		}
		return messagesForYear;
	}
	
	public List<Message> getAllMessagesPaginated(int start, int size){
		getAllMessages();
		ArrayList<Message> list = new ArrayList<>(messages);
		if(start + size > list.size()) return new ArrayList<Message>();
		return list.subList(start, start + size);
	}
	
	public Message getMessage(String id){
		ResultSet rs = db.getData("message", "id", id);
		Message message = new Message();
		try {
			while(rs.next()){
				message.setId(rs.getInt("id"));
				message.setAuthor(rs.getString("author"));
				message.setMessage(rs.getString("message"));
				message.setCreated(rs.getDate("created"));
			} 
		} catch (SQLException e) {
				e.printStackTrace();
		}
		return message;	
	}
	
	public Message addMessage(Message message){
		String query = "insert into message(\"message\", \"author\", created) values('" + message.getMessage() +"', '" + message.getAuthor() + "', '" + message.getCreated() + "')";
		db.addData(query);
		return message;
	}
	
	public Message updateMessage(Message message) {
		if(message.getId() <= 0) {
			return null;
		}
		int id = message.getId();
		String query = "update message set message = '" +message.getMessage() + "', author = '" + message.getAuthor() + "', created = '"+ message.getCreated() + "' where id = " + id ;
		db.addData(query);
		return message;
	}
	
	public void removeMessage(int id){
		String query = "delete from message where id = " + id;
		db.addData(query);
	}
}
