package interceptors;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
@Logging
public class LoggingInterceptor {

    Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);


    @AroundInvoke
    public Object logMethodEntry(InvocationContext context) throws Exception {
        logger.info("Call to method: " + context.getMethod().getName() + " in "
                    + context.getMethod().getDeclaringClass());
        try {
            context.proceed();
        }
        catch (Exception e) {
            logger.error("error");
        }
        return context.proceed();

    }



}
