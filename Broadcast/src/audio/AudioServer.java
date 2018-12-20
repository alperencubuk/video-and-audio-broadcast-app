package audio;

import javax.sound.sampled.*;
import javax.swing.JFrame;
import java.io.ObjectOutputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class AudioServer {

	private int PORT = 10001;
	
    private ArrayList<ObjectOutputStream> clients;
    private ServerSocket serverSocket;
    private Socket client;
    private ObjectOutputStream oos;
    private AudioFormat format;
    private DataLine.Info info;
    private TargetDataLine microphone;
    private byte[] data;
    private int dsize;
	private JFrame frame;
    
    public AudioServer() {
        clients = new ArrayList<>();
    }
    
    public AudioServer(int Port) {
        clients = new ArrayList<>();
        PORT=Port;
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Audio Server Started");
            new broadCast().start();

            while (true) {
                client = serverSocket.accept();
                oos = new ObjectOutputStream(new BufferedOutputStream(client.getOutputStream()));
                clients.add(oos);
                System.out.println("Connected from [" + client.getInetAddress() + "]");
                System.out.println("Audio Clients : " + clients.size());
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
                format = new AudioFormat(48000.0f, 16, 2, true, false);
                microphone = AudioSystem.getTargetDataLine(format);
                info = new DataLine.Info(TargetDataLine.class, format);
                data = new byte[1024];
                
                microphone = (TargetDataLine) AudioSystem.getLine(info);
                microphone.open(format);
                microphone.start();
                
                frame = new JFrame("[AUDIO SERVER] - Host:" + InetAddress.getLocalHost().getHostAddress() + " - Port:" + PORT);
                frame.setSize(640, 480);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                //frame.setVisible(true);
                
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            while (true) {
                try {
                    dsize = microphone.read(data, 0, data.length);
                    int size = clients.size();
                    for (int i = 0; i < size; i++) {
                        oos = clients.get(i);
                        oos.write(data, 0, dsize);
                        oos.reset();
                    }
                }
                catch(IOException e) {
                        clients.remove(oos);
                        System.out.println("Someone Disconnected");
                        System.out.println("Audio Clients : " + clients.size());
                }
            }
        }
    }
    
    public static void main(String[] args) {
        new AudioServer().start();
    }
}