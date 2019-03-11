package id.investree.news.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.investree.news.base.MetaResponse;
import id.investree.news.base.ResultResponse;
import id.investree.news.utilities.StatusCode;
import id.investree.news.utilities.log.LoggingUtils;
import io.sentry.Sentry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    private LoggingUtils logger = new LoggingUtils(CustomExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logException(ex);
//        Sentry.capture(ex);

        MetaResponse metaResponse = new MetaResponse();
        metaResponse.code = status.value();
        metaResponse.message = ex.getLocalizedMessage();

        ResultResponse resultResponse = new ResultResponse();
        resultResponse.status = StatusCode.ERROR.name();
        resultResponse.meta = metaResponse;

        printInfo(resultResponse);
        return new ResponseEntity<>(resultResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        logException(ex);
        Sentry.capture(ex);

        MetaResponse metaResponse = new MetaResponse();
        metaResponse.code = status.value();
        metaResponse.message = ex.getLocalizedMessage();

        ResultResponse resultResponse = new ResultResponse();
        resultResponse.status = StatusCode.ERROR.name();
        resultResponse.meta = metaResponse;

        printInfo(resultResponse);
        return new ResponseEntity<>(resultResponse, status);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ResultResponse> badRequest(AppException ex) {
        logException(ex);

        MetaResponse metaResponse = new MetaResponse();
        metaResponse.code = HttpStatus.BAD_REQUEST.value();
        metaResponse.message = ex.getLocalizedMessage();

        ResultResponse resultResponse = new ResultResponse();
        resultResponse.status = StatusCode.ERROR.name();
        resultResponse.meta = metaResponse;

        printInfo(resultResponse);
        return new ResponseEntity<>(resultResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResultResponse> generalError(Exception ex) {
        logException(ex);
        Sentry.capture(ex);

        MetaResponse metaResponse = new MetaResponse();
        metaResponse.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        metaResponse.message = messageSource.getMessage("response.general.error", null, LocaleContextHolder.getLocale());
        metaResponse.debugInfo = ex.getLocalizedMessage();

        ResultResponse resultResponse = new ResultResponse();
        resultResponse.status = StatusCode.ERROR.name();
        resultResponse.meta = metaResponse;

        printInfo(resultResponse);
        return new ResponseEntity<>(resultResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataExistException.class)
    public ResponseEntity<ResultResponse> userExist(DataExistException ex) {
        logException(ex);

        MetaResponse metaResponse = new MetaResponse();
        metaResponse.code = HttpStatus.BAD_REQUEST.value();
        metaResponse.message = ex.getLocalizedMessage();

        ResultResponse resultResponse = new ResultResponse();
        resultResponse.status = StatusCode.ERROR.name();
        resultResponse.meta = metaResponse;

        printInfo(resultResponse);
        return new ResponseEntity<>(resultResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ResultResponse> dataNotFound(DataNotFoundException ex) {
        logException(ex);

        MetaResponse metaResponse = new MetaResponse();
        metaResponse.code = HttpStatus.NOT_FOUND.value();
        metaResponse.message = ex.getLocalizedMessage();

        ResultResponse resultResponse = new ResultResponse();
        resultResponse.status = StatusCode.ERROR.name();
        resultResponse.meta = metaResponse;

        printInfo(resultResponse);
        return new ResponseEntity<>(resultResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ResultResponse> sqlException(SQLException ex) {
        logException(ex);
        Sentry.capture(ex);

        MetaResponse metaResponse = new MetaResponse();
        metaResponse.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        metaResponse.message = ex.getLocalizedMessage();

        ResultResponse resultResponse = new ResultResponse();
        resultResponse.status = StatusCode.ERROR.name();
        resultResponse.meta = metaResponse;

        printInfo(resultResponse);
        return new ResponseEntity<>(resultResponse, HttpStatus.NOT_FOUND);
    }

    private void logException(Exception ex) {
        logger.error(ex);
    }

    private void printInfo(Object object) {
        try {
            String message = String.format("[ERROR] <-- %s", new ObjectMapper().writeValueAsString(object));
            logger.info(message);
        } catch (JsonProcessingException ex) {
            logger.error(ex.getLocalizedMessage(), ex);
        }
    }
}
