import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int count = 1;
        int countLine = 0;
        int minLength = 0;
        int maxLength = 0;

        while (true) {
            System.out.println("Введите путь до файла:");
            String path = new Scanner(System.in).nextLine();
            File file = new File(path);
            boolean fileExists = file.exists();
            boolean isDirectory = file.isDirectory();
            if (fileExists == false || isDirectory == true) {
                System.out.println("Введен несуществующий путь до файла или путь ведет до папки");
                System.out.println("-----------------------------------------------------------");
                continue;
            }
            try {
                FileReader fileReader = new FileReader(path);
                BufferedReader reader = new BufferedReader(fileReader);
                String line;
                while ((line = reader.readLine()) != null) {
                    int length = line.length();
                    if (length > 1024) {
                        throw new MaxLengthLineException("Maximum line length exceeded 1024");
                    }
                    if (minLength == 0) {
                        minLength = length;
                    }
                    if (minLength > length) {
                        minLength = length;
                    }
                    if (maxLength < length) {
                        maxLength = length;
                    }
                    countLine++;
                }
            } catch (MaxLengthLineException ex) {
                System.out.println(ex);
                System.exit(0);
            } catch (Exception  ex) {
                ex.printStackTrace();
            }
            System.out.println("Путь указан верно");
            System.out.println("Это файл номер " + count);
            count++;
            System.out.println("Общее количество строк в файле: " + countLine);
            System.out.println("Длина самой длинной строки в файле: " + maxLength);
            System.out.println("Длина самой короткой строки в файле: " + minLength);
            System.out.println("-----------------------------------------------------------");
        }

    }
}