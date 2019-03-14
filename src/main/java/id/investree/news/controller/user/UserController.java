package id.investree.news.controller.user;

import id.investree.news.base.BaseController;
import id.investree.news.entity.User;
import id.investree.news.exception.AppException;
import id.investree.news.exception.DataExistException;
import id.investree.news.exception.DataNotFoundException;
import id.investree.news.model.request.UserRequest;
import id.investree.news.base.ResultResponse;
import id.investree.news.utilities.StringUtils;
import id.investree.news.utilities.message.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator validator;

    @Autowired
    private MessageUtils messageUtils;

    @PostMapping
    public ResponseEntity<ResultResponse> save(@RequestBody UserRequest request) {

        String validationField = validator.isFormValid(request);
        if (!StringUtils.isEmptyOrNull(validationField)) {
            throw new AppException(messageUtils.dataNotValid(validationField));
        }

        User existingUser = userService.findByUsernameOrEmail(request.getUsername(), request.getEmail());
        if (null != existingUser) {
            throw new DataExistException();
        }

        User newUser = userService.save(request);
        if (null == newUser) {
            throw new AppException(messageUtils.insertFailed());
        }

        return abstractResponseHandler(newUser).getResult(messageUtils.insertSuccess());
    }

    @PutMapping("{id}")
    public ResponseEntity<ResultResponse> update(@PathVariable("id") Long id,
                                                 @RequestBody UserRequest request) {

        String validationField = validator.isFormValid(request);
        if (!StringUtils.isEmptyOrNull(validationField)) {
            throw new AppException(messageUtils.dataNotValid(validationField));
        }

        User existingUser = userService.findByUsernameOrEmail(request.getUsername(), request.getEmail());
        if (null == existingUser) {
            throw new DataNotFoundException();
        }

        User updatedUser = userService.update(id, request);
        if (null == updatedUser) {
            throw new AppException(messageUtils.insertFailed());
        }

        return abstractResponseHandler(updatedUser).getResult(messageUtils.updateSuccess());
    }

    @GetMapping("{id}")
    public ResponseEntity<ResultResponse> findOne(@PathVariable("id") Long id) {

        if (null == id) {
            throw new AppException(messageUtils.dataNotValid("id"));
        }

        User existingUser = userService.findBy(id);
        if (null == existingUser) {
            throw new DataNotFoundException();
        }

        return abstractResponseHandler(existingUser).getResult(messageUtils.updateSuccess());
    }
}
