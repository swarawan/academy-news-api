package id.investree.news.base;

import id.investree.news.exception.AppException;
import id.investree.news.utilities.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public abstract class AbstractResponseHandler {

    @Autowired
    private MessageSource messageSource;

    public ResponseEntity<ResultResponse> getResult(String message) {
        return getResult(message, HttpStatus.OK);
    }

    public ResponseEntity<ResultResponse> getResult(String message, HttpStatus httpStatus) {
        ResultResponse resultResponse = new ResultResponse();
        Object processResponse = processResponse();
        if (processResponse instanceof AppException) {
            MetaResponse metaResponse = new MetaResponse();
            metaResponse.code = httpStatus.value();
            metaResponse.message = messageSource.getMessage("response.general.error", null, LocaleContextHolder.getLocale());
            metaResponse.debugInfo = ((AppException) processResponse).errorMessage;

            resultResponse.status = StatusCode.ERROR.name();
            resultResponse.meta = new MetaResponse();
            return generateResponseEntity(resultResponse, HttpStatus.BAD_REQUEST);
        } else {
            MetaResponse metaResponse = new MetaResponse();
            metaResponse.code = HttpStatus.OK.value();
            metaResponse.message = message;

            resultResponse.status = StatusCode.OK.name();
            resultResponse.data = processResponse;
            resultResponse.meta = metaResponse;
            return generateResponseEntity(resultResponse, httpStatus);
        }
    }

    private ResponseEntity<ResultResponse> generateResponseEntity(ResultResponse result, HttpStatus code) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return new ResponseEntity<>(result, headers, code);
    }

    protected abstract Object processResponse();
}
