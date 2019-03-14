package id.investree.news.controller.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void whenSaveUserData_ReturnSavedData() {
//        String randomUUID = UUID.randomUUID().toString();
//        User mockUser = new User();
//        mockUser.setName("User: " + randomUUID);
//        mockUser.setEmail("Email: " + randomUUID);
//        mockUser.setUsername("Username: " + randomUUID);
//        mockUser.setPassword("Password: " + randomUUID);
//
//        User savedMockUser = userService.save(mockUser);
//
//        Assert.assertNotNull(savedMockUser.getId());
//        Assert.assertEquals(savedMockUser.getName(), "User: " + randomUUID);
//        Assert.assertEquals(savedMockUser.getEmail(), "Email: " + randomUUID);
//        Assert.assertEquals(savedMockUser.getUsername(), "Username: " + randomUUID);
//        Assert.assertEquals(savedMockUser.getPassword(), "Password: " + randomUUID);
    }
}