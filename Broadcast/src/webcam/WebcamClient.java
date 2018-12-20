package webcam;

import java.io.BufferedInputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class WebcamClient {
	
	private String IP = "localhost";
	private int PORT = 10002;

	private Socket socket;
	private ObjectInputStream ois;
	private JFrame frame;
	private JLabel label;

	public WebcamClient() {
		
	}
	
	public WebcamClient(String Ip, int Port) {
		IP=Ip;
		PORT=Port;
	}
	
    public void start() {
    	try {
        	socket = new Socket(IP, PORT);
            ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));

            frame = new JFrame("[WEBCAM CLIENT] - Server:" + IP + " - Port:" + PORT);
            frame.setSize(640, 480);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            label = new JLabel();
            label.setSize(640, 480);
            label.setVisible(true);

            frame.add(label);
            frame.setVisible(true);
            
            while(true) {
            	label.setIcon((ImageIcon)ois.readObject());
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        new WebcamClient().start();
    }
}