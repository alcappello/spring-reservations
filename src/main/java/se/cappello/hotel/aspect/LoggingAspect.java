package se.cappello.hotel.aspect;

import com.sun.corba.se.spi.ior.ObjectKey;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Component
@Aspect
public class LoggingAspect {

    public static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("@annotation(Loggable)")
    public void executeLogging() {

    }

    @Around("executeLogging()")
    private Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();
        Object returnValue = joinPoint.proceed();
        long totalTime = System.currentTimeMillis() - startTime;

        StringBuilder message = new StringBuilder("Method: ");
        message.append(joinPoint.getSignature().getName());

        message.append(" Time: ")
               .append(totalTime)
               .append("ms ");

        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            message.append(", Args: [");
            Arrays.asList(args).forEach(message::append);
            message.append("]");
        }

        if (returnValue instanceof String) {
            message.append(" Returning a string of ")
                   .append(((String) returnValue).length())
                   .append(" chars.");
        }
        else {
            message.append(" Returning [ ")
                   .append(returnValue)
                   .append(" ]");
        }

        LOGGER.info(message.toString());

        return returnValue;
    }
}
