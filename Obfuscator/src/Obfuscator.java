import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Obfuscator {
    private String pathCode;
    private String pathReadyCode;

    Obfuscator(String path1, String path2) {
        this.pathCode = path1;
        this.pathReadyCode = path2;
    }

    public void deleteCommitSpace() {
        // удаление пробелов и переходов на новую строку и удаление коментариев
        try(BufferedReader br = new BufferedReader (new FileReader(pathCode));
            BufferedWriter bw = new BufferedWriter(new FileWriter(pathReadyCode))) {
            String text;
            while((text=br.readLine()) != null){
                text = text.trim();
                if (text.equals("\n")) {
                    continue;
                }
                if (text.equals("")) {
                    continue;
                }
                if (text.contains("//")) {
                    text = text.trim();
                    if (text.indexOf("//") == 0) {
                        continue;
                    } else {
                        text = text.substring(0, text.indexOf("//"));
                    }
                }
                if (text.contains("\n")) {
                    text = text.substring(0, text.indexOf("\n"));
                }
                if (text.contains("@")) {
                    text = text + " ";
                }

                // запись в файл
                bw.write(text);
                bw.flush();
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void classFinder() throws IOException {
        String className = pathCode;
        while (className.contains("\\")) {
            className = className.substring(className.indexOf("\\") + 2);
        }
        className = className.substring(0 ,className.indexOf(".txt"));
        className = className + "#" + className.substring(0,1)+ "\n"; // форматирование имени для записи в файл
        Files.write(Path.of("key.txt"),className.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND); // добавление в файл
    }

    public void renameVariables() {
        try {
            String lineRenameAll = new String(Files.readAllBytes(Path.of(pathReadyCode)), StandardCharsets.UTF_8);//считываем из файла где все в 1 строку
            String lineRenameVars = lineRenameAll.substring(lineRenameAll.indexOf("class"));//срезаем часть с импортами, чтобы ничего в них не поменять

            // регулярное выражение для классов
            Pattern email_pattern = Pattern.compile("[A-Z]\\w*.. (\\w*)[ ;.]");
            Matcher matcher = email_pattern.matcher(lineRenameVars);

            while (matcher.find()) {
                String str = matcher.group(1);//выборка имени переменной по регулярке
                if (str.length() > 2 && (!(str.equals("new"))&&!(str.equals("extends")) &&!(str.equals("InterruptedException")))) {
                    System.out.println(str);
                    str = str + "#" + str.charAt(0) + "1" + str.substring(str.length() - 1) + "\n";
                    //формат для записи в файл
                    Files.write(Path.of("key.txt"), str.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
                }
            }
            String line;
            BufferedReader br = new BufferedReader(new FileReader("key.txt"));
            while ((line = br.readLine()) != null) {
                String[] mas = line.split("#");
                if (lineRenameAll.contains(mas[0])) {
                    lineRenameVars = lineRenameVars.replaceAll(mas[0], mas[1]);//смена имен
                }
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(pathReadyCode));
            bw.write(lineRenameAll.substring(0, lineRenameAll.indexOf("class")));//вписывание импортов
            bw.write(lineRenameVars);//вписывание всего после импортов с замененными именами
            bw.flush();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
