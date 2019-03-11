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
}