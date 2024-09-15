import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int rightnum = 0;
        String path;
        File file;
        boolean fileExists;
        boolean isDirectory;
        while (true) {
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
        }
    }
}
