package Client.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Client.Client;
import Client.View.InputForm;
import Shared.Helper.StringExcute;
import Shared.Model.DataExchangeModel;
import Shared.Model.Graph;


//Controller de tuong tac giua CLientView va CLientModel và gửi dữ liệu đến Client
public class ClientController {
    private DataExchangeModel dataExchangeModel;
    private InputForm clientView;
    private Client client;

    private static StringExcute stringHelper = new StringExcute();

    public static void main(String[] args){
        DataExchangeModel model = new DataExchangeModel();
        InputForm view = new InputForm();
        Client client = new Client("localhost", 2708);

        ClientController controller = new ClientController(model, view, client);
        controller.DisplayInputForm();
    }


    //CONSTRUCTOR
    public ClientController(DataExchangeModel model, InputForm view, Client client){
        this.dataExchangeModel = model;
        this.clientView = view;
        this.client = client;

        //thêm sự kiện vào view
        clientView.addMouseClickListener(new ClickListner());
        clientView.addGeneralButtonListener(new ButtonListener());
    }

    //hiện view
    public void DisplayInputForm(){
        this.clientView.displayForm();
    }

    //lấy dữ liệu từ view (graph)
    public void RetrieveGraphFromView(Graph graph){
        this.dataExchangeModel.setGraphData(graph);
    }

    //update view
    public void UpdateView(){
        this.clientView.setGraph(dataExchangeModel.getGraphData());
    }

    //trả dữ liệu về view
    public void SetGraphToView(Graph graph){
        this.clientView.setGraph(graph);
    }

    //cập nhật dữ liệu của model 
    public void UpdateDataToClient(String message){
        //lấy ra graph
        Graph graph = this.dataExchangeModel.getGraphData();

        //xử lí graph
        stringHelper.translateString(graph, message);

        //set dữ liệu mới cho client
        this.dataExchangeModel.setGraphData(graph);

        //đổ lên view
        this.SetGraphToView(graph);
    }
    

//=====================================================
    //EVENT HANDLER
    class ClickListner extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent e){
            dataExchangeModel.setGraphData(clientView.getGraph());
            //gửi dữ liệu đến server
            String messageToSend = clientView.getStatus();

            if(!messageToSend.equals("")){
                client.SendDataToServer(clientView.getStatus());
            }

        }
    }

    class ButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            //gửi dữ liệu đến server
            client.SendDataToServer(clientView.getStatus());

        }
    }
    
}
