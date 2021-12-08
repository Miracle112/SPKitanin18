import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import java.io.*;
import java.util.Scanner;

@Class
class start {
    @Main
    public static void main(String[] args) {
        try {
            DownloadThread pop = null;
            File file = new File("E:\\Intellij IDEA Java\\Music_and_pictures\\src\\Path.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String mas;
            String[] link_path;

            while ((mas = br.readLine()) != null){
                link_path = mas.split(" ");
                pop = new DownloadThread(link_path[0], link_path[1]);
                pop.start();
            }
            pop.join();
            try (FileInputStream inputStream = new FileInputStream("src\\music.mp3")) {
                try {
                    System.out.println("Вы хотите прослушать музыку? (Да/Нет)");
                    Scanner in = new Scanner(System.in);
                    String play = in.nextLine();
                    if (play.equals("Да")) {
                        Player player = new Player(inputStream);
                        player.play();
                    }
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Возникла ошибочка");
            e.printStackTrace();
        }
    }
}