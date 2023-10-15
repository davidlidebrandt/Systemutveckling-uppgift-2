package service;

import jakarta.ws.rs.WebApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CustomException extends WebApplicationException {
    public CustomException() {
        super();
    }
    public CustomException(String message) {
        super("Error: " + message);
    }
}
