

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	private int SERVER_PORT = 9876;
	private ServerSocket serverSocket = null;
	private ArrayList<ClientThread> threads = new ArrayList<>();
	private ArrayList<String[]> pitanja = new ArrayList<>();
	private String[] pitanje1 = {"What is 10 + 20?; A)10; B)20; C)30", "C"};
	private String[] pitanje2 = {"What is 13 + 2120?; A)3210; B)2133; C)3320", "B"};


	public Server() throws IOException {
		serverSocket = new ServerSocket(SERVER_PORT);
		System.out.println("Server started on port " + SERVER_PORT);
		pitanja.add(pitanje1);
		pitanja.add(pitanje2);
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
		return "QUESTION /" + pitanja.get(0)[0];
	}
	public String getOdgovor(){
		return pitanja.get(0)[1];
	}

	public static void main(String[] args) throws IOException {
		new Server();
	}
}
