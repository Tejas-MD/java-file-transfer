// UI Libs
//import javax.swing.JFrame;
//import javax.swing.JTextField;
//import javax.swing.JButton;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.SwingUtilities;
//import java.swing.fileChooser;
//import java.swing.JOptionPanel;

import javax.swing.*;


import java.awt.FlowLayout;
import java.awt.GridLayout;

// Comms libs
import java.io.*;
import java.net.Socket;

public class Client extends JFrame {

    // UI properties
    private static JTextField serverHostField;
    private static JTextField serverPortField;
    private JButton sendButton;



    //    Comms properties
    private static DataOutputStream dataOutputStream = null;
//    private static DataInputStream dataInputStream = null;

    public static void main(String[] args) {

//      UI Code
        SwingUtilities.invokeLater(() -> {
            Client ui = new Client();
            ui.setTitle("File Sender");
            ui.setSize(300, 250);
            ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ui.initComponents(); // initialises components and attaches action function
            ui.setVisible(true);
        });

//        Code for comms


    }

    private void initComponents() {
        serverHostField = new JTextField("localhost", 15);
        serverPortField = new JTextField("900", 15);
        sendButton = new JButton("Send File");

        JFileChooser fileChooser = new JFileChooser();


        sendButton.addActionListener(e -> {
            String serverHost = serverHostField.getText();
            int serverPort = Integer.parseInt(serverPortField.getText());
            try (Socket socket = new Socket(serverHost, serverPort)) {

//                dataInputStream = new DataInputStream(
//                        socket.getInputStream());.
                int fileChooserResult = fileChooser.showOpenDialog(this);  // Show the file chooser dialog

                String filePath = null;
                if (fileChooserResult == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();  // Get the selected file
                    filePath = selectedFile.getAbsolutePath();
                }

                dataOutputStream = new DataOutputStream(
                        socket.getOutputStream());
                System.out.println(
                        "Sending the File to the Server");
                // Call SendFile Method
                sendFile(
                        filePath);

//                dataInputStream.close();
                dataOutputStream.close();

                JOptionPane.showMessageDialog(this, "File sent successfully!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception l) {
                JOptionPane.showMessageDialog(this, "File send failure!\n" + l.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

                l.printStackTrace();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        panel.add(new JLabel("Server Host:"));
        panel.add(serverHostField);
        panel.add(new JLabel("Server Port:"));
        panel.add(serverPortField);
        panel.add(new JLabel("Select File:"));
        panel.add(sendButton);
        panel.add(new JLabel());
        panel.add(new JLabel());

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

        // Extract the file type (extension)
        String fileType = getFileExtension(file);

        // Send the file type to the server
        dataOutputStream.writeUTF(fileType);

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


    // Utility method to get the file extension
    private static String getFileExtension(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }



}

