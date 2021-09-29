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
        outTerminal = new PrintWriter(System.out);

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

            if (solution == null && (submenu1 == 3 || submenu1 == 4)) {
                outTerminal.println("SPL tidak dapat diselesaikan dengan metode ini.");
                outTerminal.flush();
                getOutFile();
                if (outFile != null){
                    outFile.println("SPL tidak dapat diselesaikan dengan metode ini.");
                    outFile.flush();
                }

            }
            else if (solution == null) {
                outTerminal.println("SPL tidak memiliki solusi.");
                outTerminal.flush();
                getOutFile();
                if (outFile != null){
                    outFile.println("SPL tidak memiliki solusi.");
                    outFile.flush();
                }

            }
            else {
                solution.displayMatrixSolution(outTerminal);
                getOutFile();
                if (outFile != null) {
                    solution.displayMatrixSolution(outFile);
                }
            }
        }

        else if (menu == 2) {
            printSubmenu2();
            getSubmenu2();

            m = readMatrixN();
            double det = 0;
            if (submenu2 == 1) det = m.detReduction();
            else if (submenu2 == 2) det = m.detCofactor(m.Mat,m.row);
            outTerminal.printf("Nilai determinan matriks adalah %.4f\n",det);
            outTerminal.flush();
            getOutFile();
            if (outFile != null){
                outFile.printf("Nilai determinan matriks adalah %.4f\n",det);
                outFile.flush();
            }

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

            boolean isInvalid = Mnew.isMatrixInvalid();
            if (isInvalid) {
                outTerminal.println("Tidak ada balikan. Determinan matriks = 0");
                outTerminal.flush();
                getOutFile();
                if (outFile != null){
                    outFile.println("Tidak ada balikan. Determinan matriks = 0");
                    outFile.flush();
                }

            } else {
                Mnew.displayMatrix(outTerminal);
                getOutFile();
                if (outFile != null){
                    Mnew.displayMatrix(outFile);
                }

            }

        }

        else if (menu == 4) {
            m = readMatrixInterpolasi();
            outTerminal.print("Masukkan x:\n");
            outTerminal.flush();
            double x = input.nextDouble();
            m.interpolasiPolinom(x, outTerminal);
            getOutFile();
            if (outFile != null){
                m.interpolasiPolinom(x, outFile);
            }

        }

        else if (menu == 5) {
            //asumsi : jumlah data selalu melebihi jumlah variabel
            m = readMatrixMN();
            Matrix solusi = m.regLinierBerganda();
            solusi.displayRegSPL(outTerminal);
            double taksiran = solusi.taksirReg();
            outTerminal.printf("taksiran y = %f\n",taksiran);
            outTerminal.flush();
            getOutFile();
            if (outFile != null){
                solusi.displayRegSPL(outFile);
                outFile.printf("tkasiran y = %f\n",taksiran);
                outFile.flush();
            }

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
        outTerminal.flush();
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
        outTerminal.println("---------------------------------");
        outTerminal.flush();
    }

    public static void printSubmenu2() {
        outTerminal.println();
        outTerminal.println("---------------------------------");
        outTerminal.println("            SUBMENU 2            ");
        outTerminal.println("---------------------------------");
        outTerminal.println("1. Metode reduksi baris");
        outTerminal.println("2. Metode ekspansi kofaktor");
        outTerminal.println("---------------------------------");
        outTerminal.flush();
    }

    public static void printSubmenu3() {
        outTerminal.println();
        outTerminal.println("---------------------------------");
        outTerminal.println("            SUBMENU 3            ");
        outTerminal.println("---------------------------------");
        outTerminal.println("1. Metode umum");
        outTerminal.println("2. Metode eliminasi Gauss-Jordan");
        outTerminal.println("---------------------------------");
        outTerminal.flush();
    }

    private static void getMenu() {
        System.out.print("Menu yang ingin dipilih: ");
        menu = input.nextInt();
        while (menu > 6 || menu < 1)
        {
            System.out.print("Ulangi menu yang ingin dipilih: ");
            menu = input.nextInt();
        }
        System.out.println();
    }

    private static void getSubmenu1() {
        System.out.print("Submenu yang ingin dipilih: ");
        submenu1 = input.nextInt();
        while (submenu1 > 4 || submenu1 < 1)
        {
            System.out.print("Ulangi submenu yang ingin dipilih: ");
            submenu1 = input.nextInt();
        }
        System.out.println();
    }

    private static void getSubmenu2() {
        System.out.print("Submenu yang ingin dipilih: ");
        submenu2 = input.nextInt();
        while (submenu2 > 2 || submenu2 < 1)
        {
            System.out.print("Ulangi submenu yang ingin dipilih: ");
            submenu2 = input.nextInt();
        }
        System.out.println();
    }

    private static void getSubmenu3() {
        System.out.print("Submenu yang ingin dipilih: ");
        submenu3 = input.nextInt();
        while (submenu3 > 2 || submenu3 < 1)
        {
            System.out.print("Ulangi submenu yang ingin dipilih: ");
            submenu3 = input.nextInt();
        }
        System.out.println();
    }

    private static void getPathname() {
        System.out.print("Filename: ");
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
        outTerminal.flush();
    }

    private static void getInputChoice() {
        inputChoice = input.nextInt();
        while (!(inputChoice == 1 || inputChoice == 2)) {
            System.out.print("Ulangi metode yang ingin dipilih: ");
            inputChoice = input.nextInt();
        }
        System.out.println();
    }

    private static void getOutFile() {
        System.out.println();
        System.out.println("Apakah anda ingin menyimpan luaran ke dalam file? (Y/N)");
        String outputChoice = input.next();
        while (!(outputChoice.equalsIgnoreCase("Y") || outputChoice.equalsIgnoreCase("N"))) {
            System.out.println("Mohon ulangi input yang valid; apakah anda ingin menyimpan luaran ke dalam file? (Y/N)");
            outputChoice = input.next();
        }

        if (outputChoice.equalsIgnoreCase("Y")) {
            getPathname();
            try {
                outFile = new PrintWriter(pathname);
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
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
            System.out.print("Masukkan baris N: ");
            m.row = input.nextInt();
            m.col = m.row;
            System.out.printf("Masukkan matriks %dx%d:\n", m.row, m.col);
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
            System.out.print("Masukkan baris M: ");
            m.row = input.nextInt();
            System.out.print("Masukkan kolom N: ");
            m.col = input.nextInt();
            System.out.printf("Masukkan matriks %dx%d:\n", m.row, m.col);
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
            System.out.print("Masukkan jumlah titik: ");
            m.row = input.nextInt();
            m.col = 2;
            System.out.printf("Masukkan matriks %dx%d:\n", m.row, m.col);
            m.readMatrixKeyboard();
        }
        else {
            getPathname();
            m.readMatrixFile(pathname);
        }
        return m;
    }

    private static void askRepeat() {
        System.out.println();
        System.out.println("Apakah ingin mengulangi program? (Y/N)");
        String repeat = input.next();

        while (!(repeat.equalsIgnoreCase("Y") || repeat.equalsIgnoreCase("N"))) {
            System.out.println("Mohon ulangi input yang valid; apakah anda ingin mengulangi program? (Y/N)");
            repeat = input.next();
        }

        if (repeat.equalsIgnoreCase("Y")) {
            main(null);
        }
        else {
            System.out.println("Terima kasih telah menggunakan program ini!");
            System.exit(0);
        }
    }

}
