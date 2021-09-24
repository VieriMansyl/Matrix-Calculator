import java.util.Scanner;

public class Main {

    private static Scanner input;
    private static int menu, submenu1, submenu2, submenu3, inputChoice;
    private static String pathname;

    public static void main (String[] args) {
        input = new Scanner(System.in);
        Matrix m;

        printMenu();
        getMenu();

        if (menu == 1) {
            printSubmenu1();
            getSubmenu1();

            m = readMatrixMN();
            if (submenu1 == 1) m.solveGauss();
            else if (submenu1 == 2); //m.solveGaussJordan();
            else if (submenu1 == 3) m.solveInverse();
            else if (submenu1 == 4) m.solveCramer();
            else main(null);
        }

        else if (menu == 2) {
            printSubmenu2();
            getSubmenu2();

            m = readMatrixN();
            double det = 0;
            if (submenu2 == 1) det = m.detReduction();
            else if (submenu2 == 2) det = m.detCofactor(m.Mat,m.row);
            else main(null);
            System.out.printf("Nilai determinan matriks tersebut adalah %.4f\n",det);
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
                System.out.print("Tidak ada balikan. Determinan matrix = 0");
            } else {
                Mnew.displayMatrix();
            }

        }

        else if (menu == 4) {
            m = readMatrixInterpolasi();
            double x;
            System.out.print("Masukkan x:\n");
            x = input.nextDouble();
            double result;
            result = m.InterpolasiPolinom(x);
            System.out.printf("P(%f):%f\n", x, result);
        }

        else if (menu == 5) {
            m = readMatrixMN();
        }

        else {
            System.exit(0);
        }

        askRepeat();

    }

    public static void printMenu() {
        System.out.println();
        System.out.println("---------------------------------");
        System.out.println("              MENU               ");
        System.out.println("---------------------------------");
        System.out.println("1. Solusi sistem persamaan linier");
        System.out.println("2. Determinan matriks");
        System.out.println("3. Matriks balikan (invers)");
        System.out.println("4. Interpolasi polinom");
        System.out.println("5. Regresi linier berganda");
        System.out.println("6. Keluar");
        System.out.println("---------------------------------");
    }

    public static void printSubmenu1() {
        System.out.println();
        System.out.println("---------------------------------");
        System.out.println("            SUBMENU 1            ");
        System.out.println("---------------------------------");
        System.out.println("1. Metode eliminasi Gauss");
        System.out.println("2. Metode eliminasi Gauss-Jordan");
        System.out.println("3. Metode matriks balikan");
        System.out.println("4. Kaidah Cramer");
        System.out.println("5. Kembali ke menu utama");
        System.out.println("---------------------------------");
    }

    public static void printSubmenu2() {
        System.out.println();
        System.out.println("---------------------------------");
        System.out.println("            SUBMENU 2            ");
        System.out.println("---------------------------------");
        System.out.println("1. Metode reduksi baris");
        System.out.println("2. Metode ekspansi kofaktor");
        System.out.println("3. Kembali ke menu utama");
        System.out.println("---------------------------------");
    }

    public static void printSubmenu3() {
        System.out.println();
        System.out.println("---------------------------------");
        System.out.println("            SUBMENU 3            ");
        System.out.println("---------------------------------");
        System.out.println("1. Metode umum");
        System.out.println("2. Metode eliminasi Gauss-Jordan");
        System.out.println("3. Kembali ke menu utama");
        System.out.println("---------------------------------");
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
        while (submenu1 > 5 || submenu1 < 1)
        {
            System.out.print("Ulangi submenu yang ingin dipilih: ");
            submenu1 = input.nextInt();
        }
        System.out.println();
    }

    private static void getSubmenu2() {
        System.out.print("Submenu yang ingin dipilih: ");
        submenu2 = input.nextInt();
        while (submenu2 > 3 || submenu2 < 1)
        {
            System.out.print("Ulangi submenu yang ingin dipilih: ");
            submenu2 = input.nextInt();
        }
        System.out.println();
    }

    private static void getSubmenu3() {
        System.out.print("Submenu yang ingin dipilih: ");
        submenu3 = input.nextInt();
        while (submenu3>3 || submenu3<1)
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
        System.out.println();
        System.out.println("Metode untuk input matriks");
        System.out.println("1. Keyboard");
        System.out.println("2. File ~/test/*.txt");
        System.out.println("---------------------------------");
        System.out.print  ("Metode yang ingin dipilih: ");
    }

    private static void getInputChoice() {
        inputChoice = input.nextInt();
        while (!(inputChoice == 1 || inputChoice == 2)) {
            System.out.print("Ulangi metode yang ingin dipilih: ");
            inputChoice = input.nextInt();
        }
        System.out.println();
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
            System.out.print("Masukkan path file: ");
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
            System.out.print("Masukkan path file: ");
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
            System.out.print("Masukkan path file: ");
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
