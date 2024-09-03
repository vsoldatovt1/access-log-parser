import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите первое число:");
        int firstNumber = new Scanner(System.in).nextInt();
        System.out.println("Введите второе число:");
        int secondNumber = new Scanner(System.in).nextInt();
        System.out.println("Сумма чисел = " + (firstNumber + secondNumber) +
                System.lineSeparator() + "Разность чисел = " + (firstNumber - secondNumber) +
                System.lineSeparator() + "Произведение чисел = " + (firstNumber * secondNumber) +
                System.lineSeparator() + "Частное чисел = " + ((double) firstNumber / secondNumber));
    }
}
