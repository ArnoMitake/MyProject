package Example;

import javax.mail.*;
import java.util.Properties;

public class OutlookEmailReader {

    public static void main(String[] args) {
        String username = "arno@mitake.com.tw";
        String password = "Aa@0926916602";

        // Outlook IMAP settings
        String outlookImapHost = "imap-mail.outlook.com";
        int outlookImapPort = 993;

        // Connect to Outlook IMAP server
        Properties outlookProps = new Properties();
        outlookProps.put("mail.store.protocol", "imaps");
        outlookProps.put("mail.imaps.host", outlookImapHost);
        outlookProps.put("mail.imaps.port", outlookImapPort);
        outlookProps.setProperty("mail.imap.ssl.enable", "true");
        outlookProps.setProperty("mail.imap.ssl.trust", "10.3.2.114"); // 或指定您信任的主機

        try {
            Session session = Session.getInstance(outlookProps);
            Store store = session.getStore("imaps");
            store.connect(username, password);

            // Open the Inbox folder
            Folder inboxFolder = store.getFolder("INBOX");
            inboxFolder.open(Folder.READ_ONLY);

            // Get all messages in the Inbox
            Message[] messages = inboxFolder.getMessages();

            // Iterate over the messages and display their content
            for (Message message : messages) {
                String subject = message.getSubject();
                String from = message.getFrom()[0].toString();
                String content = message.getContent().toString();

                System.out.println("Subject: " + subject);
                System.out.println("From: " + from);
                System.out.println("Content: " + content);
                System.out.println("----------------------------------");
            }

            // Close the Inbox folder and disconnect from Outlook
            inboxFolder.close(false);
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
