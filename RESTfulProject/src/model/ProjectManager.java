package model;

import java.sql.Connection;
import java.util.ArrayList;

import dao.Database;
import dao.Project;
import dto.FeedObjects;
import dto.User;

public class ProjectManager {

	public ArrayList<FeedObjects> GetFeeds() throws Exception {
		ArrayList<FeedObjects> feeds = null;
		Connection connection = null;
		try {
			Database database = new Database();
			connection = database.Get_Connection("website");
			Project project = new Project();
			feeds = project.GetFeeds(connection);

		} catch (Exception e) {
			throw e;
		}
		finally
		{
			if(connection!=null)
				connection.close();
		}
		return feeds;
	}

	public ArrayList<User> GetUsers() throws Exception {
		ArrayList<User> users = null;
		Connection connection = null;
		try {
			Database database = new Database();
		    connection = database.Get_Connection("test");
			Project project = new Project();
			users = project.GetUsers(connection);

		} catch (Exception e) {
			throw e;
		}
		finally
		{
			if(connection!=null)
				connection.close();
		}
		return users;
	}


	public int GetUser(String name, String password) throws Exception{
		int id=0;
		Connection connection = null;
		try {
			Database database = new Database();
			connection = database.Get_Connection("test");
			Project project = new Project();
			id = project.GetUser(connection, name, password);

		} catch (Exception e) {
			throw e;
		}
		finally
		{
			if(connection!=null)
				connection.close();
		}
		return id;
	}

}
