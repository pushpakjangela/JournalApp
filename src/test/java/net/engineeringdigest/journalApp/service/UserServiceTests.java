package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserService userService;


    // another anotation for can intialization something before or after the test case :-
//    @BeforeAll
//    @BeforeEach
//    @AfterAll
//    @AfterEach
//    void setUp(){
//
//    }


    @ParameterizedTest
    @ArgumentsSource(UserArgumentProvider.class)
    public void testAddNewUser(User user){
        assertTrue(userService.saveNewUser(user));
    }
    @CsvSource({
            "1,1,2",
            "2,2,4",
            "2,3,5"
    })
    @ParameterizedTest
    public void test(int a,int b, int expected){
        assertEquals(expected,a+b);
    }
}
