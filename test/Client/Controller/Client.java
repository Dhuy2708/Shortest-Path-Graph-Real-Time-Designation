
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private Socket clientSocket;

    private ObjectOutputStream out;
    private ObjectInputStream in;

    private ClientController controller;

    public static void main(String[] args){
        Client client = new Client("localhost", 8888);

        
    }

    public Client(String addressString , int port){
        try{
            clientSocket = new Socket(addressString, port);
            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());

            ClientView view = new ClientView();
            this.controller = new ClientController(view, this);

            while(true){
                int number = (int)in.readObject();
                this.controller.updateView(number);
                this.controller.updateData(number);
            }

        }
        catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

    }


    public void sendData(int number){
        try{
            out.writeObject(number);
            out.flush();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }



}
