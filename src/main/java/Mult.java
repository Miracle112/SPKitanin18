import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Mult {
        public int read() {
            try {
                BufferedReader reader = new BufferedReader(new FileReader("mult.txt"));
                String line = reader.readLine();
                System.out.println(line);
                String[] znach = line.split(" ?\\* ?");
                System.out.printf("Ответ >> %d", (int) Math.round(multiply(znach)));
                return (int) Math.round(multiply(znach));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return 0;
        }
        public static double multiply(String[] arr) {
            double res = 1;
            for (String i : arr) {
                if (Integer.parseInt(i) != 0) {
                    res /= 1 / (double) Integer.parseInt(i);
                } else return 0;
            }
            return res;
        }
}
