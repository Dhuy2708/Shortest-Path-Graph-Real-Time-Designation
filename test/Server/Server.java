import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private ServerSocket serverSocket;

    private static int sharedNumber;
    
    private static ArrayList<ClietnHandler> clients = new ArrayList<>();

    public static void main(String[] args){
        Server server = new Server(8888);
        server.run();
    }

    public Server(int port){
        try{

            serverSocket = new ServerSocket(port);
            System.out.println("Server is running on port " + port);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void run(){
        try{
            
            while(true){
                Socket clienSocket = serverSocket.accept();
                System.out.println(clienSocket.getLocalAddress());
                ClietnHandler clietnHandler = new ClietnHandler(clienSocket);
    
    
                synchronized(clients){
                    clients.add(clietnHandler);
                }
    
                new Thread(clietnHandler).start();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    private synchronized void DataHandler(int number){
        sharedNumber = ++number;
        BroadCastData();
    } 

    private void BroadCastData(){
        synchronized(clients){
            for(ClietnHandler client : clients){
                client.sendDataToClient(sharedNumber);
            }
        }
    }


    //CLIENT HANDLER FOR THREAD
    private class ClietnHandler implements Runnable{
        private Socket clientSocket;
        private ObjectOutputStream out;
        private ObjectInputStream in;

        public ClietnHandler(Socket socket){
            this.clientSocket = socket;
        }

        @Override
        public void run(){
            try{
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new ObjectInputStream(clientSocket.getInputStream());

                Integer dataFromClient;
                while((dataFromClient = (Integer)in.readObject()) != null){
                    DataHandler(dataFromClient);
                }

            }
            catch(IOException | ClassNotFoundException e){
                e.printStackTrace();
            }
        }

        public void sendDataToClient(int data){
            try{

                out.writeObject(data);
                out.flush();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}

