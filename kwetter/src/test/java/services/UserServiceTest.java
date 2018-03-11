package services;

import dao.IKweetDao;
import dao.IUserDao;
import domain.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private IKweetDao kweetDao;

    @Mock
    private IUserDao userDao;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void getUserTest() {
        User user = new User("username", "password", User.Role.USER);
        long id = 1;
        when(userDao.findById(id)).thenReturn(user);
    }
}
