package ru.practicum;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MainTest {

    @Test
    void contextLoads() {
    }

    @Test
    void main() {
        Main.main(new String[]{"start"});
    }
}