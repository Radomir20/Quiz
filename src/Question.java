import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Question {

     private String question;
     private String correctAnswer;
     private Socket client1;
     private Socket client2;
     private boolean questionSent;
     private ArrayList<String[]> pitanja = new ArrayList<>();

     public Socket getClient1() {
          return client1;
     }

     public Socket getClient2() {
          return client2;
     }

     public Question(ArrayList<Socket> clients) throws IOException {
          this.client1 = clients.get(0);
          this.client2 = clients.get(1);
          questionSent = false;
          readQuestions();
     }

     public String sendQuestion() {
          if (questionSent == false){
               if (!pitanja.isEmpty()) {
                    this.question = "QUESTION /" + pitanja.get(0)[0];
                    this.correctAnswer = pitanja.get(0)[1];
                    this.pitanja.remove(0);
                    this.questionSent = true;
                    return this.question;
               } else
                    return "Nema vise pitanja";
          } else {
               this.questionSent = false;
               return this.question;
          }
     }

     private void readQuestions() throws IOException {

          BufferedReader br = new BufferedReader(new FileReader("questions.txt"));

          for (String line = br.readLine(); line != null; line = br.readLine()) {
               String[] temp = line.split("-");
               pitanja.add(temp);
          }

          br.close();

     }

     public void setQuestion(String question) {
          this.question = question;
     }

     public void setCorrectAnswer(String correctAnswer) {
          this.correctAnswer = correctAnswer;
     }

     public String getQuestion() {
          return question;
     }

     public String getCorrectAnswer() {
          return correctAnswer;
     }

}
