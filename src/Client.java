// UI Libs

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Comms libs
import java.io.*;
import java.net.Socket;

public class Client extends JFrame {
    // UI properties
    private static JTextField serverHostField;
    private static JTextField serverPortField;
    private JButton sendButton;
//    private JProgressBar progressBar;
    private JLabel statusLabel;

    //    Comms properties
    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;

    public static void main(String[] args) {

//      UI Code
        SwingUtilities.invokeLater(() -> {
            Client ui = new Client();
            ui.setTitle("File Sender");
            ui.setSize(300, 250);
            ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ui.initComponents();
            ui.setVisible(true);
        });

//        Code for comms


    }

    private void initComponents() {
        serverHostField = new JTextField("localhost", 15);
        serverPortField = new JTextField("900", 15);
        sendButton = new JButton("Send File");
//        progressBar = new JProgressBar();
        statusLabel = new JLabel();

        sendButton.addActionListener(e -> {
            try (Socket socket = new Socket("localhost", 900)) {

                dataInputStream = new DataInputStream(
                        socket.getInputStream());
                dataOutputStream = new DataOutputStream(
                        socket.getOutputStream());
                System.out.println(
                        "Sending the File to the Server");
                // Call SendFile Method
                sendFile(
                        "C:\\\\Users\\\\home\\\\Downloads\\\\Module3_MCES_CSE_Ash.pptx");

                dataInputStream.close();
                dataInputStream.close();
            } catch (Exception l) {
                l.printStackTrace();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        panel.add(new JLabel("Server Host:"));
        panel.add(serverHostField);
        panel.add(new JLabel("Server Port:"));
        panel.add(serverPortField);
        panel.add(sendButton);
        panel.add(new JLabel());
//        panel.add(progressBar);
        panel.add(new JLabel());
        panel.add(statusLabel);

        setLayout(new FlowLayout());
        add(panel);
    }

    private static void sendFile(String path) throws Exception {
        String serverHost = serverHostField.getText();
        int serverPort = Integer.parseInt(serverPortField.getText());
        // Get the file path to send

        int bytes = 0;
        // Open the File where he located in your pc
        File file = new File(path);
        FileInputStream fileInputStream
                = new FileInputStream(file);

        // Here we send the File to Server
        dataOutputStream.writeLong(file.length());
        // Here we  break file into chunks
        byte[] buffer = new byte[4 * 1024];
        while ((bytes = fileInputStream.read(buffer))
                != -1) {
            // Send the file to Server Socket
            dataOutputStream.write(buffer, 0, bytes);
            dataOutputStream.flush();
        }
        // close the file here
        fileInputStream.close();
    }
}

