import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Class
class DownloadThread extends Thread {

    String linkFile;
    String pathFile;

    @Constructor
    DownloadThread(String link, String path) {
        Thread thread = new Thread();
        this.linkFile = link;
        this.pathFile = path;
    }

    @Override
    public void run() {
        try {
            //Скачивание файла за счет запуска потока
            InputStream url = new URL(linkFile).openStream();
            Files.copy(url, Path.of(pathFile), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Скачивание завершено");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}