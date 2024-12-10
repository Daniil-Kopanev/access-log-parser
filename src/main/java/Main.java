import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int count = 1;
        while (true) {
            System.out.println("Введите путь до файла:");
            String path = new Scanner(System.in).nextLine();
            File file = new File(path);
            boolean fileExists = file.exists();
            boolean isDirectory = file.isDirectory();
            if (fileExists == false || isDirectory == true) {
                System.out.println("Введен несуществующий путь до файла или путь ведет до папки\n");
                continue;
            }
            System.out.println("Путь указан верно");
            System.out.println("Это файл номер " + count + "\n");
            count++;
        }
    }
}