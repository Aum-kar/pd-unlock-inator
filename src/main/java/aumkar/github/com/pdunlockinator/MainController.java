package aumkar.github.com.pdunlockinator;

import aumkar.github.com.pdunlockinator.exceptions.NotAPdFileException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Validated
@Controller
public class MainController {

    MainService service;

    public MainController(MainService service) {
        this.service = service;
    }

    @PostMapping("upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file, @RequestParam("password") String password) {

        return ResponseEntity.accepted().body(file.getOriginalFilename() + "File uploaded successfully");
    }

    @PostMapping("unlock")
    public ResponseEntity<byte[]> unlock(@NotNull @RequestParam("file") MultipartFile file,
                                         @NotBlank(message="Password not provided") @RequestParam("password") String password) throws IOException {

        // Prechecks
        if(file.isEmpty()) {
            throw new IllegalArgumentException("File has no content");
        }
        if (!("application/pdf".equals(file.getContentType()))) {
            throw new NotAPdFileException("File is not PDF");
        }

        // Unlocking PDF file
        byte[] pdfContent = service.unlockPdf(file.getBytes(), password);

        // getting new filename
        String original = Objects.requireNonNull(file.getOriginalFilename());

        int dot = original.lastIndexOf('.');

        String filename = (dot == -1)
                ? original + "_unlocked"
                : original.substring(0, dot)
                + "_unlocked"
                + original.substring(dot);

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(pdfContent);
    }
}
