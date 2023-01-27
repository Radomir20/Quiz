
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
	private Socket client;
	private String username;
	private Server server;
	private BufferedReader input;
	private PrintWriter output;

	public ClientThread(Socket client, Server server) {
		this.client = client;
		this.server = server;
		try {
			input = new BufferedReader(new InputStreamReader(client.getInputStream())); // TODO
			output = new PrintWriter(client.getOutputStream(), true);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			String message;
			while (true) {
				message = input.readLine();

				String action = message.split(" ")[0];
				if (action.equals("USERNAME")) {
					username = message.split(" ")[1];
					server.addToList(client);
				} else if (message.equals("QUESTION"))
					this.sendMessage(server.getPitanje(client));
				else if (message.equals("WIN")) {
					this.sendMessage("RESULT /Cestitam, pobijedili ste!");
					server.sendToOponent("RESULT /Nazalost, izgubili ste :(", client);
				} else if (message.equals("DISCONNECTED")) {
					server.clientDisconnected(this);
					closeAll();
				} else {
					if (message.equals(server.getOdgovor(client))) {
						this.sendMessage("ANSWER /1");
						server.sendToOponent("POINTS /1", client);
					} else {
						this.sendMessage("ANSWER /0");
						server.sendToOponent("POINTS /0", client);
					}
				}
			}
		} catch (IOException e) {
			closeAll();
		}
	}

	public void sendMessage(String message) {
		output.println(message);
	}

	public Socket getSocket() {
		return client;
	}

	public String getUsername() {
		return username;
	}

	public void closeAll() {
		try {
			input.close();
			output.close();
			client.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		interrupt();
	}
}