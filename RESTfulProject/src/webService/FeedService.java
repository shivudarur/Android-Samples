package webService;

import java.io.IOException;
import java.util.ArrayList;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import model.ProjectManager;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.gson.Gson;

import dto.FeedObjects;
import dto.User;

@Path("/WebService")
public class FeedService {
	
	// Put your Google API Server Key here
	private static final String GOOGLE_SERVER_KEY = "AIzaSyCCUu0mX80DuMILyZu14Qry35m0NTLITBI";// "AIzaSyBoR4eUEdYwVNMjzMBU5Pz-XMtpQM8FU6M";
	static final String MESSAGE_KEY = "MessageText";
	public static final String MESSAGE_ACTION = "MessageAction";
	public static final String MESSAGE_STATE = "MessageState";

	@GET
	@Path("/GetFeeds")
	@Produces("application/json")
	public String feed() {
		String feeds = null;
		try {
			ArrayList<FeedObjects> feedData = null;
			ProjectManager projectManager = new ProjectManager();
			feedData = projectManager.GetFeeds();
			// StringBuffer sb = new StringBuffer();
			Gson gson = new Gson();
			System.out.println(gson.toJson(feedData));
			feeds = gson.toJson(feedData);

		} catch (Exception e) {
			System.out.println("error");
		}
		return feeds;
	}

	@SuppressWarnings("null")
	@GET
	@Path("/GetUsers")
	@Produces("application/json")
	public String getUsers() {
		System.out.println("Am in");
		String userJson = null;
		try {
			ArrayList<User> users = null;
//			ProjectManager projectManager = new ProjectManager();
//			users = projectManager.GetUsers();
			for (int i = 0; i < 10; i++) {
				users.add(new User(i, "Name"+i, "password"+i));				
			}
			// StringBuffer sb = new StringBuffer();
			Gson gson = new Gson();
			System.out.println(gson.toJson(users));
			userJson = gson.toJson(users);

		} catch (Exception e) {
			System.out.println("error");
		}
		return userJson;
	}

	@GET
	@Path("/getName")
	@Produces(MediaType.TEXT_PLAIN)
	public String getName(
			@DefaultValue("Name") @QueryParam("name") String userName) {

		String output = "Jersey say : " + userName;

		return output;

	}

	@GET
	@Path("/GetUser")
	@Produces("application/json")
	public String getUser(@QueryParam("name") String name,
			@QueryParam("password") String password) {
		System.out.println("Am in.....");
		String userJson = null;
		try {
//			ProjectManager projectManager = new ProjectManager();
			int id = 10; //projectManager.GetUser(name,password);
			userJson="{id:"+id+"}";
			System.out.println(userJson);
		} catch (Exception e) {
			System.out.println("Error....>>>>>>>>>>>>>>>>");
			e.printStackTrace();
		}
		
		


		try {
			// BufferedReader br = new BufferedReader(new FileReader(
			// "GCMRegId.txt"));
			// regId =
			// "APA91bFMDZHZXJCgKOS49ciVq4o9GJSJZClX6r_bRgckx91WB2nq94_lN1H3vSw8eVydoOREUt-PiiYalHWB4KkOEILsUlUnIBTKz-hFPXydWfVzNxt3K17ETybA-W22NZzhTKJ0V1xaUK-ZALN4X5GrXf1FrfFwOQ";
			String regId = "APA91bG4526zQfqODeQ83_jWs9_fMGV58Nj2n2v8WY-AmT8aW8RhCcp0p55EFBeNkCC5CHWmZZcyDtHoiK1eXbFzBuPXMYzQKSPlHyXKXStTXmv6Y0mLOnmetLo4KI9Bkh76h_SgwxZcUrB1ftjUU980AZojkPPDnw";// "APA91bECtDhTWvgH6TAEcDsCARSbO4sdW7m5EBACrwfUDL_NVVerDUdte9slFuM687VfUUQVyD-IA9Jo90QAsB9OtvP1FFjAYOxkZFuBSeGyllWNRsGKignQ0_eRNPkQerXZkuCGNtYpPwLsYsqfyNvZCGAe7Yh8jg";
			// br.close();
			String userMessage = "Hello there"; //request.getParameter("message");
			Sender sender = new Sender(GOOGLE_SERVER_KEY);
			Message message = new Message.Builder().timeToLive(30)
					.delayWhileIdle(true).addData(MESSAGE_KEY, userMessage)
					.addData(MESSAGE_ACTION, "Open_useful_Stuff")
					.addData(MESSAGE_STATE, "343")
					.addData("MessageAlias", "How-to-be-happy").build();
			System.out.println("regId: " + regId);
			Result result = sender.send(message, regId, 1);
			System.out.println("result: " + result);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
		return userJson;
	}
}
