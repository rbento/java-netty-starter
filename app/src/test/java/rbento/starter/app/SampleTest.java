
/* Copyright (c) 2023 Rodrigo Bento */

package rbento.starter.app;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

@Slf4j
class SampleTest {

    List<String> strings = new ArrayList<>();

    @BeforeAll
    static void setup() {
        log.info("@BeforeAll - Executes once before all test methods in this class");
    }

    @BeforeEach
    void init() {
        log.info("@BeforeEach - Executes before each test method in this class");
    }

    @AfterEach
    void cleanup() {
        log.info("@AfterEach - Executes after each test method in this class");
    }

    @AfterAll
    static void teardown() {
        log.info("@AfterAll - Executes once after all test methods in this class");
    }

    @Test
    @DisplayName("Test adding one element to a list of strings")
    void testAddOneElementent() {
        strings.add("Foo");
        Assertions.assertThat(strings.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Test adding two elements to a list of strings")
    void testAddTwoElements() {
        strings.add("Bar");
        strings.add("Baz");
        Assertions.assertThat(strings.size()).isEqualTo(2);
    }

    @Test
    @Disabled
    @DisplayName("Test adding three elements to a list of strings")
    void testAddThreeElements() {
        strings.add("Bag");
        strings.add("Bar");
        strings.add("Baz");
        Assertions.assertThat(strings.size()).isEqualTo(3);
    }
}
