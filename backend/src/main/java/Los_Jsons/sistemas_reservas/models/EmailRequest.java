package Los_Jsons.sistemas_reservas.models;

public class EmailRequest {
    private String to;
    private String subject;
    private String text;

    // Getters y setters
    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
}
