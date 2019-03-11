package id.investree.news.exception;

public class AppException extends RuntimeException {

    public String errorMessage;

    public AppException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}