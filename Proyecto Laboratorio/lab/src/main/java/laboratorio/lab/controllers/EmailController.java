package laboratorio.lab.controllers;

import laboratorio.lab.models.EmailRequest;
import laboratorio.lab.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;



    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) {
        try {
            emailService.sendSimpleEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getText());
            return ResponseEntity.ok("Correo enviado exitosamente!");
        } catch (MailException e) {
            return ResponseEntity.status(500).body("Error al enviar el correo: " + e.getMessage());
        }
    }
}

