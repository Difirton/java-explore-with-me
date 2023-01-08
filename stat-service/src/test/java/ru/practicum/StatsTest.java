package ru.practicum;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StatsTest {

    @Test
    void contextLoads() {
    }

    @Test
    void main() {
        Stats.main(new String[]{"start"});
    }
}