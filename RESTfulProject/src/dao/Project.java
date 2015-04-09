package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dto.FeedObjects;
import dto.User;


public class Project {
	
	
	public ArrayList<FeedObjects> GetFeeds(Connection connection) throws Exception
	{
		ArrayList<FeedObjects> feedData = new ArrayList<FeedObjects>();
		try
		{
			PreparedStatement ps = connection.prepareStatement("SELECT title,description,url FROM website ORDER BY id DESC");
			//String uname = request.getParameter("uname");			
			//ps.setString(1,uname);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				FeedObjects feedObject = new FeedObjects();
				feedObject.setTitle(rs.getString("title"));
				feedObject.setDescription(rs.getString("description"));
				feedObject.setUrl(rs.getString("url"));
				feedData.add(feedObject);
			}
			return feedData;
		}
		catch(Exception e)
		{
			throw e;
		}
	}
	
	public ArrayList<User> GetUsers(Connection connection) throws Exception
	{
		ArrayList<User> users = new ArrayList<User>();
		try
		{
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM user");
			//String uname = request.getParameter("uname");			
			//ps.setString(1,uname);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				User user=new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				users.add(user);
			}
			return users;
		}
		catch(Exception e)
		{
			throw e;
		}
	}


	public int GetUser(Connection connection, String name, String password) throws Exception {
		
		try
		{
			PreparedStatement ps = connection.prepareStatement("SELECT id FROM user where name=? and password=?");		
			ps.setString(1,name);
			ps.setString(2,password);
			ResultSet rs = ps.executeQuery();
			System.out.println("sql=>"+ps.toString());
			if(rs.next())
			{
				return rs.getInt("id");
			}
			else
				return -1;
		}
		catch(Exception e)
		{
			throw e;
		}
	}
}
