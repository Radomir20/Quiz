import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	private int SERVER_PORT = 9876;
	private ServerSocket serverSocket = null;
	private ArrayList<ClientThread> threads = new ArrayList<>();
	private ArrayList<ArrayList<Socket>> clients = new ArrayList<ArrayList<Socket>>();
	private ArrayList<Question> questions = new ArrayList<>();
	private String odgovor;

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
	public synchronized void addToList(Socket socket) throws IOException {
		if (!clients.isEmpty() && clients.get(clients.size() - 1).size() == 1) {
			clients.get(clients.size() - 1).add(socket);
			Question q = new Question(clients.get(clients.size()-1));
			questions.add(q);
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
		String message = "START /";
		String name1 = getUsername(player1);
		String name2 = getUsername(player2);

		for (ClientThread thread : threads) {
			if (thread.getSocket() == player1)
				thread.sendMessage(message + name2);
			else if (thread.getSocket() == player2)
				thread.sendMessage(message + name1);
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

	public void sendToOponent(String message, Socket client) {
		for (ArrayList<Socket> list : clients) {
			if (list.size() == 2 && (client == list.get(0) || client == list.get(1))) {
				Socket oponent = (client == list.get(0) ? list.get(1) : list.get(0));
				for (ClientThread thread : threads) {
					if (thread.getSocket() == oponent)
						thread.sendMessage(message);
				}
				break;
			}
		}
	}

	public synchronized String getPitanje(Socket client) {
		String s;
			for (ClientThread thread : threads) {
				if(thread.getSocket() == client){
					for(Question ques: questions){
						if(ques.getClient1() == client){
							s = ques.sendQuestion();
							odgovor = ques.getCorrectAnswer();
							return s;
							
						} else if(ques.getClient2() == client){
							s = ques.sendQuestion();
							odgovor = ques.getCorrectAnswer();
							return s;
						} 
					}

				}
			}
		return "greskica";

	}

	public String getOdgovor() {
		return odgovor;
	}

	public synchronized void clientDisconnected(ClientThread client) {
		threads.remove(client);
		int index = -1;
		for (int i = 0; i < clients.size(); i++) {
			if (client.getSocket().equals(clients.get(i).get(0))
					|| (clients.get(i).size() == 2 && client.getSocket().equals(clients.get(i).get(1)))) {
				index = i;
			}
		}
		if (index != -1) {
			clients.remove(index);
		}
	}

	public static void main(String[] args) throws IOException {
		new Server();
	}
}
