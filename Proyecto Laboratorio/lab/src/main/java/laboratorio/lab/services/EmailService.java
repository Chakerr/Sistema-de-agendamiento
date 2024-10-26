package laboratorio.lab.services;

import laboratorio.lab.models.Laboratorista;
import laboratorio.lab.repositories.LaboratoristaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private final LaboratoristaRepository laboratoristaRepository;

    @Autowired
    public EmailService(JavaMailSender mailSender, LaboratoristaRepository laboratoristaRepository) {
        this.mailSender = mailSender;
        this.laboratoristaRepository = laboratoristaRepository;
    }

    public void sendEmailFromDatabase(String correo, String subject, String text) {
        Optional<Laboratorista> laboratoristaOpt = laboratoristaRepository.findByCorreo(correo);
        if (laboratoristaOpt.isPresent()) {
            String emailTo = laboratoristaOpt.get().getCorreo();
            sendSimpleEmail(emailTo, subject, text);
        } else {
            throw new RuntimeException("Laboratorista no encontrado con el correo: " + correo);
        }
    }

    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
