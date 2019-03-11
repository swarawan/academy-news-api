package id.investree.news.exception;

public class DataExistException extends RuntimeException {

    public DataExistException() {
        super("Data already exist");
    }

    public DataExistException(String message) {
        super("Data already exist: " + message);
    }
}
