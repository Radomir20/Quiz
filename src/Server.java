
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	private int SERVER_PORT = 9876;
	private ServerSocket serverSocket = null;
	private ArrayList<ClientThread> threads = new ArrayList<>();
	private ArrayList<ArrayList<Socket>> clients = new ArrayList<ArrayList<Socket>>();
	private String[] pitanje = { "What is 10 + 20?; A)10; B)20; C)30", "C" };

	public Server() throws IOException {
		serverSocket = new ServerSocket(SERVER_PORT);
		System.out.println("Server started on port " + SERVER_PORT);
		execute();
	}

	public void execute() {
		while (true) {
			Socket socket = null;
			try {
				socket = serverSocket.accept(); // novi soket na serveru za obradu i prihvatanje klijenata
				ClientThread client = new ClientThread(socket, this);
				client.start();
				threads.add(client);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// dodavanje u listu igraca, ukoliko je neuparen igrac upari ga, ako su svi
	// upareni onda ga samo dodaj
	public synchronized void addToList(Socket socket) {
		if (!clients.isEmpty() && clients.get(clients.size() - 1).size() == 1) {
			clients.get(clients.size() - 1).add(socket);
			Socket player1 = clients.get(clients.size() - 1).get(0);
			Socket player2 = clients.get(clients.size() - 1).get(1);
			sendStart(player1, player2);

		} else {
			ArrayList<Socket> pairOfClients = new ArrayList<>();
			pairOfClients.add(socket);
			clients.add(pairOfClients);
		}
	}

	private void sendStart(Socket player1, Socket player2) {
		String message = "START ";
		String name1 = getUsername(player1);
		String name2 = getUsername(player2);

		for (ClientThread thread : threads) {
			if (thread.getSocket() == player1)
				thread.sendMessage(message + name2 + " " + 0);
			else if (thread.getSocket() == player2)
				thread.sendMessage(message + name1 + " " + 1);
		}
	}

	// oce da vrati username ako ga ne nadje u nitima vraca prazno
	private String getUsername(Socket player) {
		for (ClientThread thread : threads) {
			if (thread.getSocket() == player)
				return thread.getUsername();
		}
		return "";
	}

	public String getPitanje() {
		return "QUESTION /" + pitanje[0];
	}

	public String getOdgovor() {
		return pitanje[1];
	}

	public static void main(String[] args) throws IOException {
		new Server();
	}
}
