package Client;

import java.io.*;
import java.net.Socket;

import Client.Controller.ClientController;
import Client.Model.ClientModel;
import Client.View.InputForm;
import Shared.Helper.GraphHelper.GraphShortestPath;
import Shared.Model.Graph;

//Mô hình client để tương tác với Server (gửi graph đến Server)
public class Client {
    private Socket socket;

    private ObjectOutputStream out;
    private ObjectInputStream in;

    private ClientController controller;

    public static void main(String[] args){
        Client client = new Client("localhost", 2708);
    } 

    public Client(String serverAddress, int serverPort) {
        try {
            socket = new Socket(serverAddress, serverPort);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            this.controller = new ClientController(new ClientModel(), new InputForm(), this);
            this.controller.DisplayInputForm();

            this.ListeningFromServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SendGraphToServer(Graph graph){
        try{
            out.writeObject(graph);
            out.flush();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void ListeningFromServer(){
        try{
            while(true){
                Graph dataFromServer = (Graph)in.readObject();
                if(dataFromServer != null && dataFromServer instanceof Graph){
                    this.controller.UpdateDataToClient(dataFromServer);
                }
            }
        }
        catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public ObjectInputStream getInputStream(){
        return this.in;
    }

    public void close(){
        try {
            out.close();
            in.close();
            socket.close();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
