package Server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import Shared.Helper.StringExcute;
import Shared.Model.DataExchangeModel;
import Shared.Model.Graph;


//Mô hình phía Server nhận dữ liệu từ CLient và trả về graph với đường đi ngắn nhất
public class Server {
    private ServerSocket serverSocket;

    private static final int port = 2708;

    private static DataExchangeModel model = new DataExchangeModel();

    private static ArrayList<ClientHandler> clients = new ArrayList<>();

    private static StringExcute stringHelper = new StringExcute();

    public static void main(String[] args) {
        Server sever = new Server(port);
        sever.run();

    }

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server is running on port: " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println(clientSocket.getInetAddress() + " has connected");
                ClientHandler clientHandler = new ClientHandler(clientSocket);

                synchronized (clients) {
                    clients.add(clientHandler);
                }

                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // GỬI DATA ĐÊN TẤT CẢ CÁC CLIENT
    public static void SendDataToAllClients(String dataFromClient, ClientHandler exceptionalClient) throws IOException{
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if(!client.equals(exceptionalClient)){

                    client.SendDataToCLient(dataFromClient);                
                }

                
            }
        }
    }

    // CLIENT HANDLER CHO MULTITHREAD
    class ClientHandler implements Runnable {
        private Socket clientSocket;

        private BufferedReader in;
        private PrintWriter out;

        public ClientHandler(Socket client) {
            try{
                this.clientSocket = client;
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
               
                String dataFromClient;

                //SERVER NHẬN DỮ LIỆU VÀ XỬ LÍ Ở ĐÂY
                while ((dataFromClient = in.readLine()) != null) {
                    System.out.println("Data recieved: " + dataFromClient);

                    DataHandler(dataFromClient);
                    SendDataToAllClients(dataFromClient, this);
                    
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public void DataHandler(String data){
            Graph graph = model.getGraphData();

            stringHelper.translateString(graph, data);

            model.setGraphData(graph);
        }

        public void SendDataToCLient(String messageToSend) {
            try {
                out.println(messageToSend);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public PrintWriter getPrintWriter(){
            return this.out;
        }

    }

}
