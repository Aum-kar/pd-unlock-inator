package aumkar.github.com.pdunlockinator;

import aumkar.github.com.pdunlockinator.exceptions.IncorrectPasswordException;
import aumkar.github.com.pdunlockinator.exceptions.NotAPdFileException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class MainServiceTest {
    MainService service;

    @BeforeEach
    void setup() {
        service = new MainService();
    }

    @Test
    void shouldUnlockPdf() throws IOException {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "sample.pdf",
                "application/pdf",
                Files.readAllBytes(Path.of("src/test/resources/password_protected.pdf"))
        );

        byte[] unlocked = service.unlockPdf(file, "1234");

        assertNotNull(unlocked);
        assertTrue(unlocked.length > 0);
    }

    @Test
    void shouldThrowExceptionForWrongPassword() throws IOException {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "sample.pdf",
                "application/pdf",
                Files.readAllBytes(Path.of("src/test/resources/password_protected.pdf"))
        );

        assertThrows(
                IncorrectPasswordException.class,
                ()-> service.unlockPdf(file, "5678")
        );
    }

    /*
    not needed in service test because empty password is being checked in controller.
    @Test
    void shouldRejectEmptyPassword() throws IOException {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "sample.pdf",
                "application/pdf",
                Files.readAllBytes(Path.of("src/test/resources/password_protected.pdf"))
        );

        byte[] unlocked = service.unlockPdf(file, "");
//        assertThrows();
    }

     */

    @Test
    void shouldRejectCorruptedPdf() throws IOException {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "sample.pdf",
                "application/pdf",
                Files.readAllBytes(Path.of("src/test/resources/corrupted.pdf"))
        );

        assertThrows(
                Exception.class,
                () -> service.unlockPdf(file, "5678")
        );
    }

    @Test
    void shouldRejectNonPdf() throws IOException {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "sample.pdf",
                "plain/text",
                Files.readAllBytes(Path.of("src/test/resources/not_pdf.txt"))
        );

        assertThrows(
                NotAPdFileException.class,
                () -> service.unlockPdf(file, "5678")
        );
    }
}
