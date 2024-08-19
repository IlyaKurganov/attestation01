package repositories;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryImplTest {

    private Path testFilePath;
    private UserRepositoryImpl userRepository;

    @BeforeEach
    void setUp() throws IOException {
        testFilePath = Files.createTempFile("test_users", ".txt");
        userRepository = new UserRepositoryImpl();
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(testFilePath);
    }

    @Test
    void findAll_ShouldReturnListOfUsers_WhenFileIsNotEmpty() throws Exception {
        try (FileWriter writer = new FileWriter(testFilePath.toFile())) {
            writer.write("1|2023-08-17T15:22:45|testUser|password|password|Ivanov|Ivan|Patronymic|25|true");
            writer.write("2|2023-08-17T15:22:45|testUser2|password2|password2|Petrov|Petr|Patronymic|30|false");
        }

        List<User> users = userRepository.findAll();

        assertEquals(2, users.size());
    }

    @Test
    void findById_ShouldReturnUser_WhenUserExists() throws Exception {
        try (FileWriter writer = new FileWriter(testFilePath.toFile())) {
            writer.write("1|2023-08-17T15:22:45|testUser|password|password|Ivanov|Ivan|Patronymic|25|true");
        }

        User user = userRepository.findById("1");

        assertNotNull(user);
        assertEquals("testUser", user.getLogin());
    }

    @Test
    void findById_ShouldThrowException_WhenUserDoesNotExist() {
        Executable executable = () -> userRepository.findById("invalidId");

        Exception exception = assertThrows(Exception.class, executable);
        assertTrue(exception.getMessage().contains("Пользователя с таким ID"));
    }

    @Test
    void deleteById_ShouldThrowException_WhenUserDoesNotExist() {
        Executable executable = () -> userRepository.deleteById("invalidId");

        Exception exception = assertThrows(Exception.class, executable);
        assertTrue(exception.getMessage().contains("Невозможно удалить"));
    }
}

