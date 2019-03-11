package id.investree.news.exception;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException() {
        super("Data not found");
    }

    public DataNotFoundException(String message) {
        super("Data not found: " + message);
    }
}
