
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Thread {
	private static int SERVER_PORT = 9876;
	private Socket socket;
	private PrintWriter output;
	private BufferedReader input;
	private Quiz quiz;

	public Client(Quiz quiz) {
		this.quiz = quiz;
		try {
			InetAddress address = InetAddress.getByName("localhost"); // adresa klijenta
			socket = new Socket(address, SERVER_PORT); // povezan sa te oadrese na taj server
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
			System.out.println("Client connected!");

		} catch (IOException e) {
			try {
				System.out.println("io " + 1);
				closeResourses();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void run() {
		try {
			String serverMessage;
			while (true) {
				serverMessage = input.readLine(); // osluskuje i cita koju i da li postu dobija
				if (serverMessage == null)
					break;
				if (serverMessage.contains("?"))
					quiz.setQuestion(serverMessage);
				else 
					quiz.setText(serverMessage);
			}

		} catch (IOException e) {
			try {
				closeResourses();
				System.out.println("io " + 2);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void sendMessage(String message) {
		output.println(message);
	}

	public void closeResourses() throws IOException {
		input.close();
		output.close();
		socket.close();
		interrupt();
	}
}
