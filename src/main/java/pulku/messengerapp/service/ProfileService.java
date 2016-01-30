package pulku.messengerapp.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pulku.messengerapp.database.DatabaseClass;
import pulku.messengerapp.model.Profile;

public class ProfileService {

	DatabaseClass db = new DatabaseClass();
	List<Profile> profiles = new ArrayList<>();
	public ProfileService(){
	
	}
	
	public List<Profile> getAllProfiles(){
		ResultSet rs = db.getAllDatas("profile");
		try {
			while(rs.next()){
				Profile profile = new Profile();
				profile.setId(rs.getInt("id"));
				profile.setProfileName(rs.getString("profileName"));
				profile.setFirstName(rs.getString("firstName"));
				profile.setLastName(rs.getString("lastName"));
				profile.setCreated(rs.getDate("created"));
				profiles.add(profile);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return profiles;
	}
	
	public Profile getProfile(String profileName){
		
		Profile profile = new Profile();
		try {
			ResultSet rs = db.getData("profile", "\"profileName\"", profileName);
			if(rs.next()){
				profile.setId(rs.getInt("id"));
				profile.setFirstName(rs.getString("firstName"));
				profile.setLastName(rs.getString("lastName"));
				profile.setCreated(rs.getDate("created"));
			} else {
				System.out.println("No data");
			}
		} catch (SQLException e) {
				e.printStackTrace();
		}
		return profile;	
	}
	
	public Profile addProfile(Profile profile){
		profile.setCreated(new Date());
		String query = "insert into profile(\"profileName\", \"firstName\", \"lastName\", created) values('" + profile.getProfileName() +"', '" + profile.getFirstName() + "', '" + profile.getLastName() + "', '" + profile.getCreated() + "');";
		db.addData(query);
		return profile;
	}
	
	public Profile updateProfile(Profile profile) {
		if(profile.getId() <= 0) {
			return null;
		}
		int id = profile.getId();
		String query = "update profile set \"profileName\" = '" +profile.getProfileName() + "', \"firstName\" = '" + profile.getFirstName() + "', \"lastName\" = '" + profile.getLastName() + "', created = '"+ profile.getCreated() + "' where id = " + id ;
		db.addData(query);
		return profile;
	}
	
	public void removeProfile(String profileName){
		String query = "delete from profile where \"profileName\" = '" + profileName + "'";
		db.addData(query);
		
	}
	
}
