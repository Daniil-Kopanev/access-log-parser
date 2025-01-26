import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int count = 1;
        int countLine = 0;
        int countGoogle = 0;
        int countYandex = 0;

        String ipAddress, date, pathRequest, requestCode, sizeData, pathUrl, userAgent;

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
                    countLine++;
                    List<String> l = new ArrayList<String>();
                    l = List.of(line.split(" "));
                    ipAddress = l.get(0);
                    date = l.get(3) + l.get(4);
                    pathRequest = l.get(5) + l.get(6);
                    requestCode = l.get(8);
                    sizeData = l.get(9);
                    pathUrl = l.get(10);
                    userAgent = "";
                    if (!l.get(11).equals("\"-\"")) {
                        for (int i = 0; i < l.size() - 11; i++) {
                            userAgent = userAgent + l.get(11 + i);
                        }
                    } else userAgent = l.get(11);
                    try {
                        if (!(userAgent.equals("\"-\""))) {
                            userAgent = userAgent.substring(userAgent.indexOf('('));
                            String[] parts = userAgent.split(";");
                            if (parts.length >= 2) {
                                String fragment = parts[1];
                                fragment = fragment.trim().substring(0, fragment.indexOf('/'));
                                if (fragment.equals("Googlebot")) {
                                    countGoogle++;
                                }
                                if (fragment.equals("YandexBot")) {
                                    countYandex++;
                                }
                            }
                        }
                    } catch (IndexOutOfBoundsException ignored) {}
                }
            } catch (MaxLengthLineException ex) {
                System.out.println(ex);
                System.exit(0);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.println("Путь указан верно");
            System.out.println("Это файл номер " + count);
            count++;
            System.out.println("Общее количество строк в файле: " + countLine);
            System.out.println("Доля запросов от Googlebot: " + (double) countGoogle / countLine);
            System.out.println("Доля запросов от YandexBot : " + (double) countYandex / countLine);
            System.out.println("-----------------------------------------------------------");
        }
    }
}