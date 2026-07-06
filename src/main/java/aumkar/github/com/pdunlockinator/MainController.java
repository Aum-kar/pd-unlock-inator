package aumkar.github.com.pdunlockinator;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MainController {

    @PostMapping("upload")
    public ResponseEntity<String> unlockPDF(@RequestParam("file") MultipartFile file, @RequestParam("password") String password) {

        return ResponseEntity.accepted().body(file.getOriginalFilename() + "File uploaded successfully");
    }
}
