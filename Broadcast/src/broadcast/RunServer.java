package broadcast;
import audio.*;
import webcam.*;

public class RunServer {
	
	private static int PORT_A = 10001;
	private static int PORT_V = 10002;

	public static void main(String[] args) {
		new Thread(() -> {
			new AudioServer(PORT_A).start();
		}).start();
		new Thread(() -> {
			new WebcamServer(PORT_V).start();
		}).start();
	}
}