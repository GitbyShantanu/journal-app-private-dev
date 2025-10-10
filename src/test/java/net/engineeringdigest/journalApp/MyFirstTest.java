package net.engineeringdigest.journalApp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class MyFirstTest {
    @Test
    public void myFirstTestMethod() {
        // Arrange
        int a = 5, b = 3;
        // Act
        int sum = a + b;
        // Assert
        assertEquals(8, sum);
    }

    @BeforeAll
    public static void setup() {
        System.out.println("JUnit Testing started....");
    }

    @BeforeEach
    public void setup2() {
        System.out.println("Setting up before each test...");
    }

    @Test
    public void arithmetic() {
        assertEquals(10, 5+5);
    }

    @Test
    @CsvSource("shan")
    public void stringNotNullTest() {
        String str = "shan";
        assertNotNull(str);
    }

    @AfterEach
    public void teardown() {
        System.out.println("Cleaning up after each test");
    }

}
