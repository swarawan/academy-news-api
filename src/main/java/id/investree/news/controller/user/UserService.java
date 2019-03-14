package id.investree.news.controller.user;

import id.investree.news.entity.User;
import id.investree.news.model.request.UserRequest;

public interface UserService {

    User save(UserRequest request) throws Exception;

    User update(Long id, UserRequest request) throws Exception;

    User findBy(Long id);

    User findByUsernameOrEmail(String username, String email);
}
