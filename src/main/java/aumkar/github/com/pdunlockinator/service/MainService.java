package aumkar.github.com.pdunlockinator.service;

import aumkar.github.com.pdunlockinator.exceptions.NotAPdFileException;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;

import aumkar.github.com.pdunlockinator.exceptions.IncorrectPasswordException;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class MainService {

    public byte[] unlockPdf(MultipartFile file, String password) {
        ByteArrayOutputStream unlockedPdfContent = new ByteArrayOutputStream();

        log.info("File received\nFile name={}\nSize={}\nContent Type={}",
                file.getOriginalFilename(),
                file.getSize(),
                file.getContentType()
        );

        // Prechecks
        if(file.isEmpty()) {
            log.error("File does not have any content");
            throw new IllegalArgumentException("File has no content");
        }
        if (!("application/pdf".equals(file.getContentType()))) {
            log.error("File is not a PDF format");
            throw new NotAPdFileException("File is not PDF");
        }

        try(PDDocument pdDocument = Loader.loadPDF(file.getBytes(), password)){
            pdDocument.setAllSecurityToBeRemoved(true);
            pdDocument.save(unlockedPdfContent);
        }
        catch(InvalidPasswordException e) {
            log.error("Invalid password provided");
            throw new IncorrectPasswordException("Incorrect password");
        }
        catch (Exception e) {
            log.error("Unexpected error while unlocking PDF: {}\n{}", e.getClass(), e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        return unlockedPdfContent.toByteArray();
    }
}
