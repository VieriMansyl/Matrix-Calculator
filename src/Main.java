import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    private static Scanner input;
    private static PrintWriter outTerminal, outFile;
    private static int menu, submenu1, submenu2, submenu3, inputChoice;
    private static String pathname;

    public static void main (String[] args) {
        input = new Scanner(System.in);
        outTerminal = new PrintWriter(outTerminal);

        Matrix m, solution = null;

        printMenu();
        getMenu();

        if (menu == 1) {
            printSubmenu1();
            getSubmenu1();

            m = readMatrixMN();
            if (submenu1 == 1) solution = m.solveGauss();
            else if (submenu1 == 2) solution = m.solveGaussJordan();
            else if (submenu1 == 3) solution = m.solveInverse();
            else if (submenu1 == 4) solution = m.solveCramer();
            else main(null);

            if (solution == null) {
                outTerminal.println("SPL tidak memiliki solusi.");
            }
            else {
                solution.displayMatrixSolution(outTerminal);
                getOutFile();
                solution.displayMatrixSolution(outFile);
            }
        }

        else if (menu == 2) {
            printSubmenu2();
            getSubmenu2();

            m = readMatrixN();
            double det = 0;
            if (submenu2 == 1) det = m.detReduction();
            else if (submenu2 == 2) det = m.detCofactor(m.Mat,m.row);
            else main(null);
            outTerminal.printf("Nilai determinan matriks adalah %.4f\n",det);
            getOutFile();
            outFile.printf("Nilai determinan matriks adalah %.4f\n",det);
        }

        else if (menu == 3) {
            printSubmenu3();
            getSubmenu3();

            m = readMatrixN();
            Matrix Mnew = new Matrix();

            if (submenu3 == 1) {
                Mnew.row = m.row;
                Mnew.col = m.col;
                Mnew = m.InverseAdjoin();
            }
            else if (submenu3 == 2) {
                Mnew.row = m.row;
                Mnew.col = m.col * 2;
                Mnew = m.InverseIdentity();
            }
            else {
                main(args);
            }

            boolean isInvalid = Mnew.isMatrixInvalid();
            if (isInvalid) {
                outTerminal.println("Tidak ada balikan. Determinan matriks = 0");
                getOutFile();
                outFile.println("Tidak ada balikan. Determinan matriks = 0");
            } else {
                Mnew.displayMatrix(outTerminal);
                getOutFile();
                Mnew.displayMatrix(outFile);
            }

        }

        else if (menu == 4) {
            m = readMatrixInterpolasi();
            outTerminal.print("Masukkan x:\n");
            double x = input.nextDouble();
            m.interpolasiPolinom(x, outTerminal);
            getOutFile();
            m.interpolasiPolinom(x, outFile);
        }

        else if (menu == 5) {
            m = readMatrixMN();
            m.regLinearBerganda();
        }

        else {
            System.exit(0);
        }

        askRepeat();

    }

    public static void printMenu() {
        outTerminal.println();
        outTerminal.println("---------------------------------");
        outTerminal.println("              MENU               ");
        outTerminal.println("---------------------------------");
        outTerminal.println("1. Solusi sistem persamaan linier");
        outTerminal.println("2. Determinan matriks");
        outTerminal.println("3. Matriks balikan (invers)");
        outTerminal.println("4. Interpolasi polinom");
        outTerminal.println("5. Regresi linier berganda");
        outTerminal.println("6. Keluar");
        outTerminal.println("---------------------------------");
    }

    public static void printSubmenu1() {
        outTerminal.println();
        outTerminal.println("---------------------------------");
        outTerminal.println("            SUBMENU 1            ");
        outTerminal.println("---------------------------------");
        outTerminal.println("1. Metode eliminasi Gauss");
        outTerminal.println("2. Metode eliminasi Gauss-Jordan");
        outTerminal.println("3. Metode matriks balikan");
        outTerminal.println("4. Kaidah Cramer");
        outTerminal.println("5. Kembali ke menu utama");
        outTerminal.println("---------------------------------");
    }

    public static void printSubmenu2() {
        outTerminal.println();
        outTerminal.println("---------------------------------");
        outTerminal.println("            SUBMENU 2            ");
        outTerminal.println("---------------------------------");
        outTerminal.println("1. Metode reduksi baris");
        outTerminal.println("2. Metode ekspansi kofaktor");
        outTerminal.println("3. Kembali ke menu utama");
        outTerminal.println("---------------------------------");
    }

    public static void printSubmenu3() {
        outTerminal.println();
        outTerminal.println("---------------------------------");
        outTerminal.println("            SUBMENU 3            ");
        outTerminal.println("---------------------------------");
        outTerminal.println("1. Metode umum");
        outTerminal.println("2. Metode eliminasi Gauss-Jordan");
        outTerminal.println("3. Kembali ke menu utama");
        outTerminal.println("---------------------------------");
    }

    private static void getMenu() {
        outTerminal.print("Menu yang ingin dipilih: ");
        menu = input.nextInt();
        while (menu > 6 || menu < 1)
        {
            outTerminal.print("Ulangi menu yang ingin dipilih: ");
            menu = input.nextInt();
        }
        outTerminal.println();
    }

    private static void getSubmenu1() {
        outTerminal.print("Submenu yang ingin dipilih: ");
        submenu1 = input.nextInt();
        while (submenu1 > 5 || submenu1 < 1)
        {
            outTerminal.print("Ulangi submenu yang ingin dipilih: ");
            submenu1 = input.nextInt();
        }
        outTerminal.println();
    }

    private static void getSubmenu2() {
        outTerminal.print("Submenu yang ingin dipilih: ");
        submenu2 = input.nextInt();
        while (submenu2 > 3 || submenu2 < 1)
        {
            outTerminal.print("Ulangi submenu yang ingin dipilih: ");
            submenu2 = input.nextInt();
        }
        outTerminal.println();
    }

    private static void getSubmenu3() {
        outTerminal.print("Submenu yang ingin dipilih: ");
        submenu3 = input.nextInt();
        while (submenu3>3 || submenu3<1)
        {
            outTerminal.print("Ulangi submenu yang ingin dipilih: ");
            submenu3 = input.nextInt();
        }
        outTerminal.println();
    }

    private static void getPathname() {
        outTerminal.print("Filename: ");
        String filename = input.next();
        pathname = "test/" + filename;
    }

    private static void printInputChoice() {
        outTerminal.println();
        outTerminal.println("Metode untuk input matriks");
        outTerminal.println("1. Keyboard");
        outTerminal.println("2. File ~/test/*.txt");
        outTerminal.println("---------------------------------");
        outTerminal.print  ("Metode yang ingin dipilih: ");
    }

    private static void getInputChoice() {
        inputChoice = input.nextInt();
        while (!(inputChoice == 1 || inputChoice == 2)) {
            outTerminal.print("Ulangi metode yang ingin dipilih: ");
            inputChoice = input.nextInt();
        }
        outTerminal.println();
    }

    private static void getOutFile() {
        outTerminal.println();
        outTerminal.println("Apakah anda ingin menyimpan luaran ke dalam file? (Y/N)");
        String outputChoice = input.next();
        while (!(outputChoice.equalsIgnoreCase("Y") || outputChoice.equalsIgnoreCase("N"))) {
            outTerminal.println("Mohon ulangi input yang valid; apakah anda ingin menyimpan luaran ke dalam file? (Y/N)");
            outputChoice = input.next();
        }

        if (outputChoice.equalsIgnoreCase("Y")) {
            getPathname();
            try {
                outFile = new PrintWriter(pathname);
            } catch (FileNotFoundException e) {
                outTerminal.println("An error occurred.");
                e.printStackTrace();
            }
        }
        else outFile = null;
    }

    private static Matrix readMatrixN() {
        printInputChoice();
        getInputChoice();
        Matrix m = new Matrix();
        if (inputChoice == 1) {
            outTerminal.print("Masukkan baris N: ");
            m.row = input.nextInt();
            m.col = m.row;
            outTerminal.printf("Masukkan matriks %dx%d:\n", m.row, m.col);
            m.readMatrixKeyboard();
        }
        else {
            getPathname();
            m.readMatrixFile(pathname);
        }
        return m;
    }

    private static Matrix readMatrixMN() {
        printInputChoice();
        getInputChoice();
        Matrix m = new Matrix();
        if (inputChoice == 1) {
            outTerminal.print("Masukkan baris M: ");
            m.row = input.nextInt();
            outTerminal.print("Masukkan kolom N: ");
            m.col = input.nextInt();
            outTerminal.printf("Masukkan matriks %dx%d:\n", m.row, m.col);
            m.readMatrixKeyboard();
        }
        else {
            getPathname();
            m.readMatrixFile(pathname);
        }
        return m;
    }

    private static Matrix readMatrixInterpolasi() {
        printInputChoice();
        getInputChoice();
        Matrix m = new Matrix();
        if (inputChoice == 1) {
            outTerminal.print("Masukkan jumlah titik: ");
            m.row = input.nextInt();
            m.col = 2;
            outTerminal.printf("Masukkan matriks %dx%d:\n", m.row, m.col);
            m.readMatrixKeyboard();
        }
        else {
            getPathname();
            m.readMatrixFile(pathname);
        }
        return m;
    }

    private static void askRepeat() {
        outTerminal.println();
        outTerminal.println("Apakah ingin mengulangi program? (Y/N)");
        String repeat = input.next();

        while (!(repeat.equalsIgnoreCase("Y") || repeat.equalsIgnoreCase("N"))) {
            outTerminal.println("Mohon ulangi input yang valid; apakah anda ingin mengulangi program? (Y/N)");
            repeat = input.next();
        }

        if (repeat.equalsIgnoreCase("Y")) {
            main(null);
        }
        else {
            outTerminal.println("Terima kasih telah menggunakan program ini!");
            System.exit(0);
        }
    }

}
