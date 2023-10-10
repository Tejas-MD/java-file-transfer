# File Transfer Application

This is a simple file transfer application that allows you to send files from a client to a server using Java Swing and sockets.

## Highlight: Recognises and transmits any file type due to byte buffer TCP transfer. 

## Client (Client.java)

### Overview
- The `Client` class represents the client-side of the application.
- It provides a graphical user interface (GUI) for the user to input the server host, server port, and select a file to send.
- It establishes a socket connection with the server and sends the selected file to the server.

### Usage
1. Run the `Client` class to open the client UI.
2. Enter the server host (default is "localhost") and server port (default is 900).
3. Click the "Send File" button to select a file and send it to the server.
4. You will receive a confirmation message upon successful file transfer.

## Server (Server.java)

### Overview
- The `Server` class represents the server-side of the application.
- It provides a GUI for configuring the server's IP address, port, and the directory where received files will be saved.
- It listens for incoming client connections and receives files sent by clients.

### Usage
1. Run the `Server` class to open the server configuration UI.
2. Enter the desired IP address (default is "localhost"), port (default is 990), and save path (default is the current directory).
3. Click the "Start Server" button to start listening for client connections.
4. The server will display status messages and save received files in the specified directory.

## How to Run
- Make sure both the client and server are running on their respective machines.
- Ensure that the server is started before the client attempts to connect.
- You can customize the server's IP address, port, and save path as needed.

## Dependencies
- This project uses Java Swing for the user interface and Java sockets for communication.
- Java.io breaks any file into byte-wise buffer for transmission 

## Note
- This is a basic file transfer application, and there is room for improvement and error handling.
- Room for improvement towards adding a permission to accept file dialogue. 

Feel free to explore the code and make enhancements or modifications as necessary.
