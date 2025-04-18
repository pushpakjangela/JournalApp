package net.engineeringdigest.journalApp.UserShedulerTest;

import net.engineeringdigest.journalApp.sheduler.UserSheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserShedulerTest {

    @Autowired
    private UserSheduler userSheduler;

    @Test
    public void fetchUserShedulerAndSendTest(){
        userSheduler.fetchUserAndSendMail();
    }
}
