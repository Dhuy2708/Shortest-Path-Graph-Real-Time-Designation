package Client;

import java.io.*;
import java.net.Socket;


import Client.Controller.ClientController;
import Client.View.InputForm;
import Shared.Model.DataExchangeModel;

//Mô hình client để tương tác với Server (gửi graph đến Server)
public class Client {
    private Socket socket;

    private static final int port = 2708;

    private PrintWriter out;
    private BufferedReader in;

    private ClientController controller;

    public static void main(String[] args) {
        Client client = new Client("localhost", port);
    }

    public Client(String serverAddress, int serverPort) {
        try {
            socket = new Socket(serverAddress, serverPort);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            this.controller = new ClientController(new DataExchangeModel(), new InputForm(), this);
            this.controller.DisplayInputForm();

            this.ListeningFromServer();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void SendDataToServer(String message) {
        try {
            out.println(message);
            System.out.println("Data sent: " + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ListeningFromServer() throws IOException, ClassNotFoundException {
        Thread listenThread = new Thread(() -> {
            try {
                while (true) {
                    String messageFromServer = in.readLine();
        
                    if (messageFromServer != null) {
                        this.controller.UpdateDataToClient(messageFromServer);

                        System.out.println("Data recieved: " + messageFromServer);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        listenThread.start();

    }

    public BufferedReader getInputStream() {
        return this.in;
    }

    public void close() throws IOException {
        out.close();
        in.close();
        socket.close();
    }
}
