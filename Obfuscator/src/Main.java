import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args){

        try {
            if (!Files.exists(Path.of("key.txt"))){
                Files.createFile(Path.of("key.txt"));
            } //создание файла ключа если его нет
        } catch (IOException e) {
            e.printStackTrace();
        }

        Obfuscator file1 = new Obfuscator("src\\DownloadThread.txt", "src\\code.txt");
        file1.deleteCommitSpace();
        try {
            file1.classFinder();
            file1.renameVariables();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
