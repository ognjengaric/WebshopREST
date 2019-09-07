package helpers;

import java.io.File;


import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;

import beans.User;
import dao.UserDAO;


public class AttributeListener implements ServletContextAttributeListener {

	@Override
	public void attributeAdded(ServletContextAttributeEvent event) {
		String path = "";
		Object objectDAO;
		Class<?> classDAO = event.getServletContext().getAttribute(event.getName()).getClass();
		
		path = event.getServletContext().getRealPath("") + File.separator + event.getName() + ".json";
		objectDAO = ReadWriteHelper.read(path, classDAO);
		
		if(objectDAO != null) {
			//special case
			if(classDAO == UserDAO.class) {
				for (User u : ((UserDAO)objectDAO).getUsers().values()) {
					ReadWriteHelper.convertToCertainRoleObject(u); 
				}
			}			
			
			event.getServletContext().setAttribute(event.getName(), objectDAO);
		}		
	}

	@Override
	public void attributeRemoved(ServletContextAttributeEvent event) {
		// TODO Auto-generated method stub
	}
	

	@Override
	public void attributeReplaced(ServletContextAttributeEvent event) {
		Object objectDAO;
		String path = "";
		
		objectDAO = (Object) event.getServletContext().getAttribute(event.getName());
		path = event.getServletContext().getRealPath("") + File.separator + event.getName() + ".json";	
		ReadWriteHelper.write(objectDAO, path);
	}	

}
