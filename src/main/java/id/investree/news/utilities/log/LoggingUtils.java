package id.investree.news.utilities.log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Enumeration;

public class LoggingUtils {

    private Logger logger;

    public LoggingUtils(Class<?> clazz) {
        logger = LoggerFactory.getLogger(clazz);
    }

    public void info(LoggerModel model, LogEnum logEnum) throws JsonProcessingException {
        String log;
        if (null == model.getRequestBody()) {
            log = String.format("[%s] %s %s",
                    model.getMeta().getMethod().toUpperCase(),
                    logEnum,
                    model.getMeta().getRequestURI());
        } else {
            String jsonRequestBody = new ObjectMapper().writeValueAsString(model.getRequestBody());
            log = String.format("[%s] %s %s %s",
                    model.getMeta().getMethod().toUpperCase(),
                    logEnum,
                    model.getMeta().getRequestURI(),
                    jsonRequestBody);
        }

        Enumeration headerNames = model.getMeta().getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = model.getMeta().getHeader(key);
            String logHeader = String.format("[%s] %s : %s",
                    model.getMeta().getMethod().toUpperCase(),
                    key, value);
            logger.info(logHeader);
        }

        logger.info(log);
    }

    public void info(String message) {
        logger.info(message);
    }

    public void error(Throwable throwable) {
        logger.error("", throwable);
    }

    public void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

}
