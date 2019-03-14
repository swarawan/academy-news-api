package id.investree.news.controller.user;

import id.investree.news.model.request.UserRequest;
import id.investree.news.utilities.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    public String isFormValid(UserRequest request) {
        if (StringUtils.isEmptyOrNull(request.getName())) return "name";
        else if (StringUtils.isEmptyOrNull(request.getUsername())) return "username";
        else if (StringUtils.isEmptyOrNull(request.getEmail())) return "email";
        else if (StringUtils.isEmptyOrNull(request.getPassword())) return "password";
        else return "";
    }
}
