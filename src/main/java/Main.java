import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
       System.out.println("Введите первое число:");
       int firstNumber = new Scanner(System.in).nextInt();
       System.out.println("Введите второе число:");
       int secondNumber = new Scanner(System.in).nextInt();
       int sum = firstNumber + secondNumber;
       int subt = firstNumber - secondNumber;
       int multipl = firstNumber * secondNumber;
       double quotient = (double)firstNumber / secondNumber;
       System.out.println("Операции над введенными числами:");
       System.out.println("Сумма: " + sum);
       System.out.println("Разность: " + subt);
       System.out.println("Произведение: " + multipl);
       System.out.println("Частное: " + quotient);
    }
}