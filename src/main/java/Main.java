import java.io.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int count = 1;
        int countLine = 0;
        int countGoogle = 0;
        int countYandex = 0;
        String userAgent;
        Statistics statistics = new Statistics();

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
                    LogEntry lg = new LogEntry(line);
                    statistics.addEntry(lg);
                    List<String> l = List.of(line.split(" "));
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
                    } catch (IndexOutOfBoundsException ignored) {
                    }
                }
            } catch (MaxLengthLineException ex) {
                System.out.println(ex);
                System.exit(0);
            } catch (Exception ex) {
                ex.printStackTrace();
                System.exit(0);
            }
            System.out.println("Путь указан верно");
            System.out.println("Это файл номер " + count);
            count++;
            System.out.println("Общее количество строк в файле: " + countLine);
            System.out.println("Средний объём трафика за час: " + statistics.getTrafficRate() + " Кбайт/ч");
            System.out.println("Доля запросов от Googlebot: " + (double) countGoogle / countLine);
            System.out.println("Доля запросов от YandexBot : " + (double) countYandex / countLine);
//            System.out.println("Список существующий страниц сайта: " + statistics.getAllExistPages());
            System.out.println("Доли запросов от разный ОС: " + statistics.getStatisticsOs());
//            System.out.println("Список несуществующий страниц сайта: " + statistics.getNonExistPages());
            System.out.println("Доли запросов от разных браузеров: " + statistics.getStatisticsBrowser());
            System.out.println("Среднее количество посещений за час: " + statistics.getAvgCountVisits());
            System.out.println("Среднее количество ошибок за час: " + statistics.getAvgCountErrors());
            System.out.println("Среднее количество посещений одним пользователом за час: " + statistics.getAvgVisitsOneUser());
            System.out.println("-----------------------------------------------------------");
            // удаление данных по текущему файлу
            statistics.clear();
            countLine = 0;
            countGoogle = 0;
            countYandex = 0;
        }
    }
}