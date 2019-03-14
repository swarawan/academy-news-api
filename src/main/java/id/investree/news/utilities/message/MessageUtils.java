package id.investree.news.utilities.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageUtils {

    @Autowired
    private MessageSource messageSource;

    public String generalError() {
        return messageSource.getMessage("response.general.error", null, LocaleContextHolder.getLocale());
    }

    public String dataNotValid(String name) {
        String message = messageSource.getMessage("data.invalid", null, LocaleContextHolder.getLocale());
        return String.format(message, name);
    }

    public String insertSuccess() {
        return messageSource.getMessage("insert.success", null, LocaleContextHolder.getLocale());
    }

    public String insertFailed() {
        return messageSource.getMessage("insert.failed", null, LocaleContextHolder.getLocale());
    }

    public String updateSuccess() {
        return messageSource.getMessage("update.success", null, LocaleContextHolder.getLocale());
    }

    public String updateFailed() {
        return messageSource.getMessage("update.failed", null, LocaleContextHolder.getLocale());
    }

    public String dataFetched() {
        return messageSource.getMessage("data.fetched", null, LocaleContextHolder.getLocale());
    }
}