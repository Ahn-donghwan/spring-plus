package org.example.expert.domain.user.service;
import org.example.expert.config.JwtUtil;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.stream.IntStream;
import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {
    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private JwtUtil jwtUtil;
    private static final int TOTAL_USERS = 10000;
//    @BeforeEach
//    void setUp() {
//        userRepository.deleteAll();
//    }
    @Test
    void generateUsers() {
        IntStream.range(0, TOTAL_USERS).forEach(i -> {
            String nickname = "user_" + i; // 고유한 닉네임을 위해 인덱스 사용
            User user = new User("test" + i + "@example.com", "password1234!", UserRole.USER, nickname);
            userRepository.save(user);
        });
        long count = userRepository.count();
        assertEquals(TOTAL_USERS, count, "User generation count should match the total.");
    }
}
