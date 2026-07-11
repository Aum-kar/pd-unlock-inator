package aumkar.github.com.pdunlockinator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MainController.class)
public class MainControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    MainService service;

    @Test
    void shouldRejectGetRequest() throws Exception {
        mockMvc
                .perform(get("/unlock")                )
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void shouldAcceptPdfWithPass() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "password_protected.pdf",
                "application/pdf",
                Files.readAllBytes(Path.of("src/test/resources/password_protected.pdf"))
        );
        mockMvc
                .perform(multipart("/unlock").param("password", "1234").file(file))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRejectMissingPassword() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "password_protected.pdf",
                "application/pdf",
                Files.readAllBytes(Path.of("src/test/resources/password_protected.pdf"))
        );
        mockMvc
                .perform(multipart("/unlock").file(file))
                .andExpect(status().isBadRequest());
    }
}
