package services;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import beans.Message;
import beans.User;
import dao.MessageDAO;
import dao.UserDAO;

@Path("")
public class MessageService {
	@Context
	ServletContext context;
	
	@PostConstruct
	public void init() {
		if(context.getAttribute("MessageDAO") == null) {
			context.setAttribute("MessageDAO", new MessageDAO());
		}
	}
	
	@GET
	@Path("/message/{id}")
	@Consumes(MediaType.APPLICATION_JSON)	  
	@Produces(MediaType.APPLICATION_JSON)
	public Message getMessageInfo(@PathParam ("id") String messageId, @Context HttpServletRequest request) {
		MessageDAO messages = (MessageDAO) context.getAttribute("MessageDAO");
		
		 return messages.getMessages().get(messageId);		
	}
	
	@POST
	@Path("/send_message")
	@Consumes(MediaType.APPLICATION_JSON)	  
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendMessage(Message msg, @Context HttpServletRequest request) {
		MessageDAO messages = (MessageDAO) context.getAttribute("MessageDAO");
		
		messages.getMessages().put(msg.getId(), msg);
		context.setAttribute("MessageDAO", messages);
		
		return Response.ok().build();
	}
	
	@GET
	@Path("/sent_messages/{username}")
	@Consumes(MediaType.APPLICATION_JSON)	  
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Message> getSentMessages(@PathParam("username") String username, @Context HttpServletRequest request){
		MessageDAO messages = (MessageDAO) context.getAttribute("MessageDAO");		
		return messages.getMessages().values().stream().filter(msg -> msg.getSender().equals(username) && !msg.getIsDeletedSender()).collect(Collectors.toList());
	}
	
	@GET
	@Path("/received_messages/{username}")
	@Consumes(MediaType.APPLICATION_JSON)	  
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Message> getReceivedMessages(@PathParam("username") String username, @Context HttpServletRequest request){
		MessageDAO messages = (MessageDAO) context.getAttribute("MessageDAO");		
		return messages.getMessages().values().stream().filter(msg -> msg.getRecipient().equals(username) && !msg.getIsDeletedRecipient()).collect(Collectors.toList());
	}
	
	@POST
	@Path("/set_message_read/{id}")
	@Consumes(MediaType.APPLICATION_JSON)	  
	@Produces(MediaType.APPLICATION_JSON)
	public Response setMessageAsRead(@PathParam("id") String id, @Context HttpServletRequest request) {
		MessageDAO messages = (MessageDAO) context.getAttribute("MessageDAO");
		
		messages.getMessages().get(id).setIsRead(true);
		
		context.setAttribute("MessageDAO", messages);
		
		return Response.ok().build();
	}
	
	@POST
	@Path("/delete_message/{id}")
	@Consumes(MediaType.APPLICATION_JSON)	  
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteMessageFromInbox(@PathParam("id") String id, String accessToken, @Context HttpServletRequest request) {
		MessageDAO messages = (MessageDAO) context.getAttribute("MessageDAO");
		UserDAO users = (UserDAO) context.getAttribute("UserDAO");
		
		Message message = messages.getMessages().get(id);
		
		User user = users.findBySessionID(accessToken);
		
		if(user.getUsername().equals(message.getSender())) {
			message.setIsDeletedSender(true);
		}
		if(user.getUsername().equals(message.getRecipient())) {
			message.setIsDeletedRecipient(true);
		}		
		
		context.setAttribute("MessageDAO", messages);
		
		return Response.ok().build();
	}
}
