package ru.geekbrains.core.lesson2;

import java.util.Random;
import java.util.Scanner;

public class Program {

    private static final char DOT_HUMAN = 'X';
    private static final char DOT_AI = '0';
    private static final char DOT_EMPTY = '*';
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();
    private static char[][] field;
    private static int fieldSizeX;
    private static int fieldSizeY;

    private static final int WIN_COUNT = 3; // Выигрышная комбинация

    /**
     * Инициализация объектов игры
     */
    static void initialize(){
        fieldSizeX = 5;
        fieldSizeY = 5;
        field = new char[fieldSizeX][fieldSizeY];

        for (int x = 0; x < fieldSizeX; x++){
            for (int y = 0; y < fieldSizeY; y++){
                field[x][y] = DOT_EMPTY;
            }
        }
    }

    /**
     * Печать текущего состояния игрового поля
     */
    static void printField(){
        System.out.print("+");
        for (int i = 0; i < fieldSizeX; i++){
            System.out.print("-" + (i + 1));
        }
        System.out.println("-");

        for (int x = 0; x < fieldSizeX; x++){
            System.out.print(x + 1 + "|");
            for (int y = 0; y < fieldSizeY; y++){
                System.out.print(field[x][y] + "|");
            }
            System.out.println();
        }

        for (int i = 0; i < fieldSizeX * 2 + 2; i++){
            System.out.print("-");
        }
        System.out.println();
    }

    /**
     * Ход игрока (человека)
     */
   static void humanTurn(){
        int x;
        int y;
        do {
            System.out.print("Введите координаты хода X и Y\n(от 1 до 3) через пробел: ");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        }
        while (!isCellValid(x, y) || !isCellEmpty(x, y));
        field[x][y] = DOT_HUMAN;
    }

    /**
     * Ход игрока (компьютера)
     */
    static void aiTurn(){
        int x;
        int y;
        do{
            x = random.nextInt(fieldSizeX);
            y = random.nextInt(fieldSizeY);
        }
        while (!isCellEmpty(x, y));
        field[x][y] = DOT_AI;
    }

    /**
     * Проверка, является ли ячейка игрового поля пустой
     * @param x координата
     * @param y координата
     * @return результат проверки
     */
    static boolean isCellEmpty(int x, int y){
        return field[x][y] == DOT_EMPTY;
    }

    /**
     * Проверка валидности координат хода
     * @param x координата
     * @param y координата
     * @return результат проверки
     */
    static boolean isCellValid(int x, int y){
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    /**
     * Поверка на ничью (все ячейки игрового поля заполнены фишками человека или компьютера)
     * @return
     */
    static boolean checkDraw(){
        for (int x = 0; x < fieldSizeX; x++){
            for (int y = 0; y < fieldSizeY; y++){
                if (isCellEmpty(x, y)) return false;
            }
        }
        return true;
    }

    /**
     * TODO: Переработать в рамках домашней работы
     * Метод проверки победы
     * @param dot фишка игрока
     * @return результат проверки победы
     */
//    static boolean checkWin(char dot){
//        // Проверка по трем горизонталям
//        if (field[0][0] == dot && field[0][1] == dot && field[0][2] == dot) return true;
//        if (field[1][0] == dot && field[1][1] == dot && field[1][2] == dot) return true;
//        if (field[2][0] == dot && field[2][1] == dot && field[2][2] == dot) return true;
//
//        // Проверка по трем вертикалям
//        if (field[0][0] == dot && field[1][0] == dot && field[2][0] == dot) return true;
//        if (field[0][1] == dot && field[1][1] == dot && field[2][1] == dot) return true;
//        if (field[0][2] == dot && field[1][2] == dot && field[2][2] == dot) return true;
//
//        // Проверка по двум диагоналям
//        if (field[0][0] == dot && field[1][1] == dot && field[2][2] == dot) return true;
//        if (field[0][2] == dot && field[1][1] == dot && field[2][0] == dot) return true;
//
//        return false;
//    }


    /**
     * Переделанная проверка на победу
     * @param dot
     * @param win
     * @return
     */
    static boolean checkWinV2(char dot, int win){
        for (int i = 0; i < fieldSizeX; i++) {
            for (int j = 0; j < fieldSizeY; j++) {
                if (check1(i,j,dot,win))
                    return true;
                if (check2(i,j,dot, win))
                    return true;
                if (check3(dot))
                    return true;
            }
        }
        return false;
    }

    /**
     * Проверка горизонтали
     * @param x
     * @param dot
     * @param win
     * @return
     */
    static boolean check1(int x, int y, char dot, int win){
        int count = 0;
        for (; y < fieldSizeX; y++) {
            if (field[x][y] == dot)
                count++;
            if (count == win)
                return true;
        }
        return false;
    }

    /**
     * Проверка вертикали
     * @param y
     * @param dot
     * @param win
     * @return
     */
    static boolean check2(int x, int y, char dot, int win) {
        int count = 0;
        for (; x < fieldSizeX; x++) {
            if (field[x][y] == dot)
                count++;
            if (count == win)
                return true;
        }
        return false;
    }

    /**
     * Проверка диагоналий
     * @param dot
     * @return
     */
    static boolean check3(char dot) {
        int Count;
        int x = fieldSizeX - WIN_COUNT;
        int y = fieldSizeY - WIN_COUNT;
        int k = 0;

        for (int i = y; i >= 0; i--) {
            k = 0;
            Count = 0;
            for (int j = i; j < fieldSizeY; j++) {
                if (field[j][k] == dot) {
                    Count++;
                } else {
                    Count = 0;
                }
                if (Count >= WIN_COUNT) return true;
                k++;
            }
        }

        for (int i = x; i > 0; i--) {
            k = 0;
            Count = 0;
            for (int j = i; j < fieldSizeX; j++) {
                if (field[k][j] == dot) {
                    Count++;
                } else {
                    Count = 0;
                }
                if (Count >=WIN_COUNT) return true;
                k++;
            }
        }

        for (int i = fieldSizeY - y - 1; i < fieldSizeY; i++) {
            k = 0;
            Count = 0;
            for (int j = i; j >= 0; j--) {
                if (field[j][k] == dot) {
                    Count++;
                } else {
                    Count = 0;
                }
                if (Count >= WIN_COUNT) return true;
                k++;
            }
        }

        for (int i = x; i > 0; i--) {
            k = fieldSizeY - 1;
            Count = 0;
            for (int j = i; j < fieldSizeX; j++) {
                if (field[k][j] == dot) {
                    Count++;
                } else {
                    Count = 0;
                }
                if (Count >= WIN_COUNT) return true;
                k--;
            }
        }

        return false;
    }

    /**
     * Проверка состояния игры
     * @param dot фишка игрока
     * @param s победный слоган
     * @return состояние игры
     */
    static boolean checkState(char dot, int win, String s){
        if (checkWinV2(dot, win)){
            System.out.println(s);
            return true;
        }
        else if (checkDraw()){
            System.out.println("Ничья!");
            return true;
        }
        // Игра продолжается
        return false;
    }

    public static void main(String[] args) {
        while (true) {
            initialize();
            printField();
            while (true) {
                humanTurn();
                printField();
                if (checkState(DOT_HUMAN, WIN_COUNT, "Вы победили!"))
                    break;
                aiTurn();
                printField();
                if (checkState(DOT_AI, WIN_COUNT, "Вы победили!"))
                    break;
            }
            System.out.print("Желаете сыграть еще раз? (Y - да): ");
            if (!scanner.next().equalsIgnoreCase("Y"))
                break;

        }
    }

}


