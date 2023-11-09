import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ClientController {
    private ClientView  view;
    private Client client;
    private static int number = 0;

    public ClientController(ClientView view, Client client){
        this.view = view;
        this.client = client;
        
        this.view.AddButtonListener(new buttonlistener());
        this.view.display();
    }

    public void displayClientView(){
        this.view.display();
    }
    
    public void updateView(int number){
        this.view.SetText(number);
    }

    public void updateData(int number){
        this.number = number;
    }
    public static void main(String[] args){
        ClientView view = new ClientView();
        
        Client client = new Client("localhost", 8888);
        ClientController controller = new ClientController(view, client);
    }

    class buttonlistener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            client.sendData(number);
        }
    }
}
