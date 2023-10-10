import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends JFrame {

    private JTextField ipAddressField;
    private JTextField portField;
    private JTextField savePathField;
    private JLabel statusLabel;

    private static DataInputStream dataInputStream = null;

    // Constructor
    public Server() {
        setTitle("Server Configuration");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        JLabel ipAddressLabel = new JLabel("IP Address:");
        ipAddressField = new JTextField("localhost");
        JLabel portLabel = new JLabel("Port:");
        portField = new JTextField("990");
        JLabel savePathLabel = new JLabel("Save Path:");
        savePathField = new JTextField(System.getProperty("user.dir"));
        statusLabel = new JLabel("Status: Not Connected");

        JButton startButton = new JButton("Start Server");

        panel.add(ipAddressLabel);
        panel.add(ipAddressField);
        panel.add(portLabel);
        panel.add(portField);
        panel.add(savePathLabel);
        panel.add(savePathField);
        panel.add(statusLabel);
        panel.add(startButton);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startServer();
            }
        });

        add(panel);
    }

    private void startServer() {
        Thread serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String ipAddress = ipAddressField.getText();
                    int port = Integer.parseInt(portField.getText());
                    String savePath = savePathField.getText();

                    ServerSocket serverSocket = new ServerSocket(port);
                    statusLabel.setText("Status: Listening on " + ipAddress + ":" + port);

                    while (true) {
                        Socket clientSocket = serverSocket.accept();
                        System.out.println("Connected");

                        dataInputStream = new DataInputStream(clientSocket.getInputStream());

                        // Receive the file type (extension)
                        String fileType = dataInputStream.readUTF();

                        // Generate a unique file name based on current timestamp
                        String fileName = savePath + "/" + System.currentTimeMillis() + "." + fileType;

                        // Here we call receiveFile to save the file
                        receiveFile(fileName);

                        dataInputStream.close();
                        clientSocket.close();
                        System.out.println("File saved as: " + fileName);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    statusLabel.setText("Status: Error - " + ex.getMessage());
                }
            }
        });

        serverThread.start();
    }

    private void receiveFile(String fileName) throws Exception {
        int bytes = 0;
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);

        long size = dataInputStream.readLong(); // read file size
        byte[] buffer = new byte[4 * 1024];
        while (size > 0 && (bytes = dataInputStream.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
            // Here we write the file using write method
            fileOutputStream.write(buffer, 0, bytes);
            size -= bytes; // read up to file size
        }
        // Here we received the file
        System.out.println("File is Received");
        JOptionPane.showMessageDialog(this, "File received");
        fileOutputStream.close();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Server serverUI = new Server();
            serverUI.setVisible(true);
        });
    }
}



//    public class Server {
//
////    private static DataOutputStream dataOutputStream = null;
//    private static DataInputStream dataInputStream = null;
//
//    public static void main(String[] args)
//    {
//        // Here we define Server Socket running on port 900
//        try (ServerSocket serverSocket
//                     = new ServerSocket(990)) {
//            System.out.println(
//                    "Server is Starting in Port 990");
//            // Accept the Client request using accept method
//            Socket clientSocket = serverSocket.accept();
//            System.out.println("Connected");
//
//            dataInputStream = new DataInputStream(
//                    clientSocket.getInputStream());
//
//            // Receive the file type (extension)
//            String fileType = dataInputStream.readUTF();
//
//            // Generate a unique file name based on current timestamp
//            String fileName = System.currentTimeMillis() + "." + fileType;
//
////            dataOutputStream = new DataOutputStream(
////                    clientSocket.getOutputStream());
//
//            // Here we call receiveFile define new for that file
//            receiveFile(fileName);
//
//            dataInputStream.close();
////            dataOutputStream.close();
//            clientSocket.close();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    // receive file function starts here
//    private static void receiveFile(String fileName)
//            throws Exception
//    {
//        int bytes = 0;
//        FileOutputStream fileOutputStream
//                = new FileOutputStream(fileName);
//
//        long size
//                = dataInputStream.readLong(); // read file size
//        byte[] buffer = new byte[4 * 1024];
//        while (size > 0
//                && (bytes = dataInputStream.read(
//                buffer, 0,
//                (int)Math.min(buffer.length, size)))
//                != -1) {
//            // Here we write the file using write method
//            fileOutputStream.write(buffer, 0, bytes);
//            size -= bytes; // read upto file size
//        }
//        // Here we received file
//        System.out.println("File is Received");
//        fileOutputStream.close();
//    }
//}