package ru.practicum.common.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.repository.entity.EndpointHit;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IpAddressValidatorTest {
    @Autowired
    private Validator validator;

    @Test
    @DisplayName("Test custom ip validation with valid ip: after 28.12.1895")
    void testIsValidTrue() {
        EndpointHit endpointHit = EndpointHit.builder()
                .id(1L)
                .uri("test")
                .app("test")
                .ip("200.200.200.200")
                .build();
        Set<ConstraintViolation<EndpointHit>> violations = validator.validate(endpointHit);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Test custom validation with not valid ip: 3 numbers")
    void testWithNotValidIp1() {
        EndpointHit endpointHit = EndpointHit.builder()
                .id(1L)
                .uri("test")
                .app("test")
                .ip("200.200.200")
                .build();
        Set<ConstraintViolation<EndpointHit>> violations = validator.validate(endpointHit);
        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("{Invalid ip address}", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Test custom validation with not valid ip: more than 255")
    void testWithNotValidIp2() {
        EndpointHit endpointHit = EndpointHit.builder()
                .id(1L)
                .uri("test")
                .app("test")
                .ip("200.200.200.600")
                .build();
        Set<ConstraintViolation<EndpointHit>> violations = validator.validate(endpointHit);
        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("{Invalid ip address}", violations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("Test custom validation with not valid ip: negative number")
    void testWithNotValidIp3() {
        EndpointHit endpointHit = EndpointHit.builder()
                .id(1L)
                .uri("test")
                .app("test")
                .ip("200.200.200.-60")
                .build();
        Set<ConstraintViolation<EndpointHit>> violations = validator.validate(endpointHit);
        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("{Invalid ip address}", violations.iterator().next().getMessage());
    }
}