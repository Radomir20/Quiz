

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	private int SERVER_PORT = 9876;
	private ServerSocket serverSocket = null;
	private ArrayList<ClientThread> threads = new ArrayList<>();
	private String[] pitanje = {"What is 10 + 20?", "30"};

	public Server() throws IOException {
		serverSocket = new ServerSocket(SERVER_PORT);
		System.out.println("Server started on port " + SERVER_PORT);
		execute();
	}

	public void execute() {
		while (true) {
			Socket socket = null;
			try {
				socket = serverSocket.accept();  // novi soket na serveru za obradu i prihvatanje klijenata
				ClientThread client = new ClientThread(socket, this);
				client.start();
				threads.add(client);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String getPitanje() {
		return pitanje[0];
	}
	public String getOdgovor(){
		return pitanje[1];
	}

	public static void main(String[] args) throws IOException {
		new Server();
	}
}
