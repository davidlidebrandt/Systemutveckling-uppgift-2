package service;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Provider
public class CustomExceptionMapper  implements ExceptionMapper<CustomException> {
    private static final Logger logger = LoggerFactory.getLogger(CustomExceptionMapper.class);


    @Override
    public Response toResponse(CustomException exception) {
        logger.error(exception.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).build();
    }
}
