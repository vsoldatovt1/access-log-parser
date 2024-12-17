import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    /*public static void main(String[] args) {
        int rightnum = 0;
        String path;
        File file;
        boolean fileExists;
        boolean isDirectory;
        while (true) {
            int numberOfYandex = 0;
            int numberOfGoogle = 0;
            int numberOfLinesInFile = 0;
            System.out.println("Введите путь к существующему файлу.");
            path = new Scanner(System.in).nextLine();
            file = new File(path);
            fileExists = file.exists();
            isDirectory = file.isDirectory();
            if (isDirectory) {
                System.out.println("Вы ввели адрес директории, а не файла.");
                continue;
            }
            if (!fileExists) {
                System.out.println("Указанный файл по данному пути не существует.");
                continue;
            }
            if (fileExists && !isDirectory) {
                rightnum++;
                System.out.println("Путь указан верно. Это файл номер " + rightnum);
            }
            try {
                FileReader fileReader = new FileReader(path);
                BufferedReader reader = new BufferedReader(fileReader);
                String line;
                Pattern pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+)\\s-\\s-\\s\\[(.*?)\\]\\s\"(\\w+)\\s([^\"]+)\\sHTTP/1.0\"\\s(\\d+)\\s(\\d+)\\s\"([^\"]*)\"\\s\"([^\"]*)\"");
                while ((line = reader.readLine()) != null) {
                    numberOfLinesInFile++;
                    int length = line.length();
                    if (length > 1024) throw new LongStringException("Line in file is too long");
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        String ipClient = matcher.group(1);
                        DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("dd/MMM/yyyy:HH:mm:ss Z").toFormatter(Locale.ENGLISH);
                        LocalDateTime dateRequest = LocalDateTime.parse(matcher.group(2),formatter);
                        HttpMethod method = HttpMethod.valueOf(matcher.group(3));
                        String pathOfUrl = matcher.group(4);
                        String httpCode = matcher.group(5);
                        String responseSize = matcher.group(6);
                        String refererPage = matcher.group(7);
                        String userAgent = matcher.group(8);
                        System.out.println(dateRequest);
                        System.out.println(userAgent);
                        System.out.println(method);
                        String regexForUserAgent = "\\(([^)]+)\\)";
                        Pattern patternForUserAgent = Pattern.compile(regexForUserAgent);
                        Matcher matcherForUserAgent = patternForUserAgent.matcher(userAgent);
                        if (matcherForUserAgent.find()) {
                            String[] parts = matcherForUserAgent.group(1).split(";");
                            String[] newParts = new String[parts.length];
                            for (int i = 0; i < parts.length; i++) {
                                parts[i] = parts[i].replace(" ", "");
                            }
                            if (parts.length >= 2) {
                                String fragment = parts[1];
                                String[] fragmentOfSearchBot = fragment.split("/");
                                if (fragmentOfSearchBot.length >= 2) {
                                    String searchbot = fragmentOfSearchBot[0];
                                    if (searchbot.equals("YandexBot")) {
                                        numberOfYandex++;
                                        continue;
                                    }
                                    if (searchbot.equals("Googlebot")) numberOfGoogle++;
                                }
                            }
                        }
                    }
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("Количество строк в файле равно " + numberOfLinesInFile);
            System.out.println("Доля запросов от Yandexbot равно " + (double) numberOfYandex / numberOfLinesInFile);
            System.out.println("Доля запросов от Googlebot равно " + (double) numberOfGoogle / numberOfLinesInFile);

        }
    }*/
    public static void main(String[] args) {
        int rightnum = 0;
        String path;
        File file;
        boolean fileExists;
        boolean isDirectory;
        while (true) {
            int numberOfYandex = 0;
            int numberOfGoogle = 0;
            int numberOfLinesInFile = 0;
            System.out.println("Введите путь к существующему файлу.");
            path = new Scanner(System.in).nextLine();
            file = new File(path);
            fileExists = file.exists();
            isDirectory = file.isDirectory();
            if (isDirectory) {
                System.out.println("Вы ввели адрес директории, а не файла.");
                continue;
            }
            if (!fileExists) {
                System.out.println("Указанный файл по данному пути не существует.");
                continue;
            }
            if (fileExists && !isDirectory) {
                rightnum++;
                System.out.println("Путь указан верно. Это файл номер " + rightnum);
            }
            try {
                FileReader fileReader = new FileReader(path);
                BufferedReader reader = new BufferedReader(fileReader);
                String line;
                Statistics statistics = new Statistics();
                while ((line = reader.readLine()) != null) {
                    numberOfLinesInFile++;
                    int length = line.length();
                    if (length > 1024) throw new LongStringException("Line in file is too long");
                    LogEntry logEntry = new LogEntry(line);
                    System.out.println(logEntry.getMethod());
                    statistics.addEntry(logEntry);
                }
                System.out.println(statistics.getTrafficRate());
            } catch (IOException ex) {
                ex.printStackTrace();
            }


        }
    }
}
