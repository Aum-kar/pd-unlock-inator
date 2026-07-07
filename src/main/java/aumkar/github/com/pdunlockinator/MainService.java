package aumkar.github.com.pdunlockinator;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;

@Service
public class MainService {

    public byte[] unlockPdf(byte[] pdfContent, String password) {

        ByteArrayOutputStream unlockedPdfContent = new ByteArrayOutputStream();

        try(PDDocument pdDocument = Loader.loadPDF(pdfContent, password)){
            pdDocument.setAllSecurityToBeRemoved(true);
            pdDocument.save(unlockedPdfContent);
        }
        catch(InvalidPasswordException e) {
            throw new IllegalArgumentException("Incorrect password");
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Something went wrong");
        }

        return unlockedPdfContent.toByteArray();
    }
}
