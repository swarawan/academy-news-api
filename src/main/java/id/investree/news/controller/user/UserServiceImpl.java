package id.investree.news.controller.user;

import id.investree.news.entity.User;
import id.investree.news.model.request.UserRequest;
import id.investree.news.repository.UserRepository;
import id.investree.news.security.HmacAlgorithm;
import id.investree.news.utilities.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HmacAlgorithm algorithm;

    @Override
    public User save(UserRequest request) throws Exception {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(algorithm.generate(request.getPassword()));
        user.setUpdatedDate(new Date());
        user.setCreatedDate(new Date());

        return userRepository.save(user);
    }

    @Override
    public User update(Long id, UserRequest request) throws Exception {
        User oldUser = userRepository.getOne(id);
        if (null == oldUser) return null;

        oldUser.setName(StringUtils.isEmptyOrNull(request.getName()) ?
                oldUser.getName() : request.getName());

        oldUser.setUsername(StringUtils.isEmptyOrNull(request.getUsername()) ?
                oldUser.getUsername() : request.getUsername());

        oldUser.setEmail(StringUtils.isEmptyOrNull(request.getEmail()) ?
                oldUser.getEmail() : request.getEmail());

        oldUser.setPassword(StringUtils.isEmptyOrNull(request.getPassword()) ?
                oldUser.getPassword() : algorithm.generate(request.getPassword()));

        oldUser.setUpdatedDate(new Date());

        return userRepository.save(oldUser);
    }

    @Override
    public User findBy(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByUsernameOrEmail(String username, String email) {
        return userRepository.findByUsernameOrEmail(username, email).orElse(null);
    }
}
