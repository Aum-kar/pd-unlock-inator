package aumkar.github.com.pdunlockinator.controller;

import aumkar.github.com.pdunlockinator.service.MainService;
import aumkar.github.com.pdunlockinator.util.Utility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Slf4j
@Validated
@Controller
public class MainController {

    MainService service;

    public MainController(MainService service) {
        this.service = service;
    }

    @PostMapping("unlock")
    public ResponseEntity<byte[]> unlock(@NotNull @RequestParam("file") MultipartFile file,
                                         @NotBlank(message="Password not provided")
                                         @RequestParam("password") String password) {

        byte[] pdfContent = service.unlockPdf(file, password);
        String filename = Utility.generateFilename(Objects.requireNonNull(file.getOriginalFilename()));

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(pdfContent);
    }
}
