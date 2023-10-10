import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

//    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;

    public static void main(String[] args)
    {
        // Here we define Server Socket running on port 900
        try (ServerSocket serverSocket
                     = new ServerSocket(990)) {
            System.out.println(
                    "Server is Starting in Port 990");
            // Accept the Client request using accept method
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connected");

            dataInputStream = new DataInputStream(
                    clientSocket.getInputStream());

            // Receive the file type (extension)
            String fileType = dataInputStream.readUTF();

            // Generate a unique file name based on current timestamp
            String fileName = System.currentTimeMillis() + "." + fileType;

//            dataOutputStream = new DataOutputStream(
//                    clientSocket.getOutputStream());

            // Here we call receiveFile define new for that file
            receiveFile(fileName);

            dataInputStream.close();
//            dataOutputStream.close();
            clientSocket.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // receive file function starts here
    private static void receiveFile(String fileName)
            throws Exception
    {
        int bytes = 0;
        FileOutputStream fileOutputStream
                = new FileOutputStream(fileName);

        long size
                = dataInputStream.readLong(); // read file size
        byte[] buffer = new byte[4 * 1024];
        while (size > 0
                && (bytes = dataInputStream.read(
                buffer, 0,
                (int)Math.min(buffer.length, size)))
                != -1) {
            // Here we write the file using write method
            fileOutputStream.write(buffer, 0, bytes);
            size -= bytes; // read upto file size
        }
        // Here we received file
        System.out.println("File is Received");
        fileOutputStream.close();
    }
}