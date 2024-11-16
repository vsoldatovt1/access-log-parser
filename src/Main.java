import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int rightnum = 0;
        String path;
        File file;
        boolean fileExists;
        boolean isDirectory;
        while (true) {
            int numberOfLinesInFile = 0;
            int shortestLine = 0;
            int longestLine = 0;
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
                while ((line = reader.readLine()) != null) {
                    numberOfLinesInFile++;
                    int length = line.length();
                    if (numberOfLinesInFile == 1) shortestLine = length;
                    if (length > 1024) throw new LongStringException("Line in file is too long");
                    if (length > longestLine) longestLine = length;
                    if (length < shortestLine) ;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("Number of lines in file is " + numberOfLinesInFile);
            System.out.println("Length of longest line is " + longestLine);
            System.out.println("Length of shortest line is " + shortestLine);
        }
    }
}
