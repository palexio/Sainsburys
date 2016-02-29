package com.sainsburys.webpage.logic;

import java.io.IOException;
import java.io.StringWriter;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class use to serialize Json
 * @author Alessio
 *
 */
public class SerializeJson {

	final static Logger logger = LoggerFactory.getLogger(SerializeJson.class);
	
	private static SerializeJson serializeJson;
	
	private SerializeJson() {
	}
	
	public static SerializeJson getInstance() {
		if(serializeJson == null) {
			serializeJson = new SerializeJson();
		}
		return serializeJson;
	}
	
	/**
	 * Given an object returns the Json serialized version of it
	 * @param object
	 * @return
	 */
    public String toJsonString(Object object) {
        JsonFactory factory = new JsonFactory();
        ObjectMapper mapper = new ObjectMapper();
        StringWriter stringWriter = new StringWriter();

        JsonGenerator generator;
		try {
			generator = factory.createJsonGenerator(stringWriter);
	        generator.useDefaultPrettyPrinter();
	        mapper.writeValue(generator, object);
	        generator.close();
	        return stringWriter.toString();
	    } catch (IOException e) {
	    	logger.error(e.getMessage());
	    	return "";
		}
    }

}
