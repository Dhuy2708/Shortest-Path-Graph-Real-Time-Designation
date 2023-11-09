package Client.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.Action;

import Client.Client;
import Client.Model.ClientModel;
import Client.View.InputForm;
import Shared.Model.Graph;
import Shared.Model.Node;
import Shared.Model.NodeInfo;
import View.InputGraph.DesignGraphPanel;


//Controller de tuong tac giua CLientView va CLientModel và gửi dữ liệu đến Client
public class ClientController {
    private ClientModel clientModel;
    private InputForm clientView;
    private Client client;

    public static void main(String[] args){
        ClientModel model = new ClientModel();
        InputForm view = new InputForm();
        Client client = new Client("localhost", 2708);

        ClientController controller = new ClientController(model, view, client);
        controller.DisplayInputForm();
    }


    //CONSTRUCTOR
    public ClientController(ClientModel model, InputForm view, Client client){
        this.clientModel = model;
        this.clientView = view;
        this.client = client;


        //thêm sự kiện vào view
        clientView.addMouseClickListener(new ClickListner());
        clientView.addGeneralButtonListener(new ButtonListener());
    }

    //hiện màn hình
    public void DisplayInputForm(){
        this.clientView.displayForm();
    }

    //lấy dữ liệu từ view (graph)
    public void RetrieveGraphFromView(Graph graph){
        this.clientModel.setGraphData(graph);
    }

    //trả dữ liệu về view
    public void SetGraphToView(Graph graph){
        this.clientView.setGraph(graph);
    }

    //cập nhật dữ liệu của model 
    public void UpdateDataToClient(Graph graph){
        this.clientModel.setGraphData(graph);
        this.SetGraphToView(graph);
    }
    
//=====================================================
    //EVENT HANDLER
    class ClickListner extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e){
             //lấy dữ liệu graph của frame
            RetrieveGraphFromView(clientView.getGraph());

            //đưa dữ liệu model từ client lên server
            client.SendGraphToServer(clientModel.getGraph());

            //nhận dữ liệu từ server 
            //clientView.setGraph(client.GetGraphFromServer());
        }
    }

    class ButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            RetrieveGraphFromView(clientView.getGraph());
            client.SendGraphToServer(clientModel.getGraph());

        }
    }
    
}
