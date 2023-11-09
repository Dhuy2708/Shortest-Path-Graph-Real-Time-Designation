package Server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import Server.Model.ServerModel;
import Shared.Model.Graph;

//Mô hình phía Server nhận dữ liệu từ CLient và trả về graph với đường đi ngắn nhất
public class Server {
    private ServerSocket serverSocket;

    private static ServerModel model = new ServerModel();

    private static ArrayList<ObjectOutputStream> clients = new ArrayList<>();

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server is running on port: " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Server sever = new Server(2708);
        sever.run();

    }

    public void run(){
        try{

            while(true){
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());

                synchronized(clients){
                    clients.add(out);
                }
                
                new Thread(clientHandler).start();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    // GỬI DATA ĐÊN TẤT CẢ CÁC CLIENT
    public static void SendGraphToAllClients() {
        synchronized (clients) {
            try {
                for (ObjectOutputStream client : clients) {
                    client.writeObject(model.getGraph());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //CLIENT HANDLER CHO MULTITHREAD
    class ClientHandler implements Runnable {
        private Socket clientSocket;
        private ObjectInputStream in;
        private ObjectOutputStream out;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run(){
            try{
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new ObjectInputStream(clientSocket.getInputStream());

                Graph dataFromClient;

                //SERVER NHẬN DỮ LIỆU VÀ XỬ LÍ Ở ĐÂY
                while((dataFromClient = (Graph)in.readObject()) != null){
                    model.setGraph(dataFromClient);
                    SendGraphToAllClients();
                }
            }
            catch(IOException | ClassNotFoundException ex){
                ex.printStackTrace();
            }
        }

        public void SendGraphToCLient(Graph graph) {
            try {
                out.writeObject(graph);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
