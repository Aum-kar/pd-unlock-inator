package aumkar.github.com.pdunlockinator;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;

import aumkar.github.com.pdunlockinator.exceptions.IncorrectPasswordException;

@Service
public class MainService {

    public byte[] unlockPdf(byte[] pdfContent, String password) {

        ByteArrayOutputStream unlockedPdfContent = new ByteArrayOutputStream();

        try(PDDocument pdDocument = Loader.loadPDF(pdfContent, password)){
            pdDocument.setAllSecurityToBeRemoved(true);
            pdDocument.save(unlockedPdfContent);
        }
        catch(InvalidPasswordException e) {
            throw new IncorrectPasswordException("Incorrect password");
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return unlockedPdfContent.toByteArray();
    }
}
