package helpers;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import beans.Administrator;
import beans.Buyer;
import beans.Seller;
import beans.User;

public class ReadWriteHelper {
		
	public ReadWriteHelper(){
	}
	
	public static void write(Object object, String path) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		File file = new File(path);

		try {
			objectMapper.writeValue(file, object);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Object read(String path, Class<?> cls) {
		ObjectMapper objectMapper = new ObjectMapper();
		
		File file = new File(path);
		
		try {				
			return objectMapper.readValue(file, cls);
		} catch (JsonParseException e) {
				e.printStackTrace();
		} catch (JsonMappingException e) {
			return null;
		} catch (IOException e) {
			return null;
		}		
		return null;
	}
	
	
	//Jackson converts role object to linkeHashMap - this method will convert linkeHashMap object to certain role object properly
	public static void convertToCertainRoleObject(User u) {
		ObjectMapper o = new ObjectMapper();
		o.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, true);
		
		try {
			Buyer b = o.convertValue(u.getRole(), Buyer.class); 
			u.setRole(b);
		} catch (IllegalArgumentException e1) {
			try {
				Seller s = o.convertValue(u.getRole(), Seller.class); 
				u.setRole(s);
			} catch (IllegalArgumentException e2) {
				try {
					Administrator a = o.convertValue(u.getRole(), Administrator.class); 
					u.setRole(a);
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
		}
	}	
	
}
