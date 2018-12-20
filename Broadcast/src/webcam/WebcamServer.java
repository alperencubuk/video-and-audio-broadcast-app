package webcam;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import com.github.sarxos.webcam.Webcam;

public class WebcamServer {
	
	private int PORT = 10002;
	
	private ArrayList<ObjectOutputStream> clients;
	private ServerSocket serverSocket;
	private Socket client;
	private ObjectOutputStream oos;
	private ObjectOutputStream video;
	private Webcam webcam;
	private BufferedImage bufferedImg;
	private ImageIcon drawImg;
	private JFrame frame;
	private JLabel label;
    
    public WebcamServer() {
        clients = new ArrayList<>();
    }
    
    public WebcamServer(int Port) {
        clients = new ArrayList<>();
        PORT=Port;
    }
	
	public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Webcam Server Started");
            new broadCast().start();

            while (true) {
                client = serverSocket.accept();
                oos = new ObjectOutputStream(new BufferedOutputStream(client.getOutputStream()));
                clients.add(oos);
                System.out.println("Connected from [" + client.getInetAddress() + "]");
                System.out.println("Webcam Clients : " + clients.size());
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
	
	class broadCast extends Thread {

        @Override
        public void run() {
            try {
                webcam = Webcam.getDefault();
                webcam.setViewSize(new Dimension(640, 480));
                webcam.open();
                    
                frame = new JFrame("[WEBCAM SERVER] - Host:" + InetAddress.getLocalHost().getHostAddress() + " - Port:" + PORT);
                frame.setSize(640, 480);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                label = new JLabel();
                label.setSize(640, 480);
                label.setVisible(true);
                    
                frame.add(label);
                frame.setVisible(true);
            }
            catch(Exception e) {
                e.printStackTrace();
            }

            while (true) {
                try {
                    bufferedImg = webcam.getImage();
                    drawImg = new ImageIcon(bufferedImg);
                    label.setIcon(drawImg);
                    int size = clients.size();
                    for (int i = 0; i < size; i++) {
                    	video = clients.get(i);
                    	video.writeObject(drawImg);
                    	video.reset();
                    }
                }
                catch(IOException e) {
                    clients.remove(video);
                    System.out.println("Someone Disconnected");
                    System.out.println("Webcam Clients : " + clients.size());
                }
                catch(NullPointerException n) {
                }
            }
        }
    }

	public static void main(String[] args) throws IOException {
		new WebcamServer().start();
	}
}