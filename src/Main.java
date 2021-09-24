import java.util.Scanner;

public class Main {

    private static Scanner input;
    private static int menu, submenu1, submenu2, submenu3, inputChoice;
    private static String pathname;

    public static void main (String[] args) {
        input = new Scanner(System.in);
        Matrix m = new Matrix();

        printMenu();
        getMenu();

        if (menu == 7) {
            System.out.print("filename: ");
            String filename = input.next();
            m.readMatrixFile(filename);
            m.displayMatrix();
        }

        if (menu == 1) {
            printSubmenu1();
            getSubmenu1();

            if (submenu1 == 1) {

            }
            else if (submenu1 == 2) {

            }
            else if (submenu1 == 3) {

            }
            else if (submenu1 == 4) {
                System.out.print("Masukkan jumlah persamaan SPL: ");
                m.row = input.nextInt();
                m.col = m.row + 1;
                System.out.printf("Masukkan matriks augmented %dx%d:\n", m.row, m.col);
                m.readMatrixKeyboard();
                m.solveCramer();
            }
            else {
                main(null);
            }

        }

        else if (menu == 2) {
            printSubmenu2();
            getSubmenu2();

            if (submenu2 == 1) {
                printInputChoice();
                getInputChoice();

                if (inputChoice == 1) {
                    System.out.print("Masukkan n: ");
                    m.row = input.nextInt();
                    m.col = m.row;
                    System.out.printf("Masukkan matriks persegi %dx%d:\n", m.row, m.col);
                    m.readMatrixKeyboard();
                }

                else {
                    getPathname();
                    m.readMatrixFile(pathname);
                }

                double det = m.detReduction();
                System.out.printf("Nilai determinan matriks tersebut adalah %.4f", det);
                System.out.println();

            }
            else if (submenu2 == 2) {
                printInputChoice();
                getInputChoice();
                if(inputChoice == 1){
                    System.out.print("Masukkan n: ");
                    m.row = input.nextInt();
                    m.col = m.row;
                    System.out.printf("Masukkan matriks persegi %dx%d:\n", m.row, m.col);
                    m.readMatrixKeyboard();
                    double det = m.detCofactor(m.Mat,m.row);
                    System.out.printf("Nilai determinan matriks tersebut adalah %.4f\n",det);
                }
                else {
                    System.out.print("Masukkan path file: ");
                    getPathname();
                    m.readMatrixFile(pathname);
                }
            }
            else {
                main(null);
            }
        }

        else if (menu == 3) {
            printSubmenu3();
            getSubmenu3();

            if (submenu3 == 1) {
                printInputChoice();
                getInputChoice();
                if(inputChoice == 1){
                    System.out.print("Masukkan n: ");
                    m.row = input.nextInt();
                    m.col = m.row;
                    System.out.printf("Masukkan matriks persegi %dx%d:\n", m.row, m.col);
                    m.readMatrixKeyboard();
                    Matrix Mnew = new Matrix();
                    Mnew.row = m.row;
                    Mnew.col = m.col;
                    Mnew = m.InverseAdjoin();
                    boolean isInvalid = Mnew.isMatrixInvalid();
                    if (isInvalid){
                        System.out.printf("Tidak ada balikan. Determinan matrix = 0");
                    }
                    else{
                        Mnew.displayMatrix();
                    }
                } else {
                    System.out.print("Masukkan path file: ");
                    getPathname();
                    m.readMatrixFile(pathname);
                }
            }

            else if (submenu3 == 2) {
                printInputChoice();
                getInputChoice();
                if(inputChoice == 1) {
                    System.out.print("Masukkan n: ");
                    m.row = input.nextInt();
                    m.col = m.row;
                    System.out.printf("Masukkan matriks persegi %dx%d:\n", m.row, m.col);
                    m.readMatrixKeyboard();
                    Matrix Mnew = new Matrix();
                    Mnew.row = m.row;
                    Mnew.col = m.col * 2;
                    Mnew = m.InverseIdentity();
                    boolean isInvalid = Mnew.isMatrixInvalid();
                    if (isInvalid) {
                        System.out.printf("Tidak ada balikan. Determinan matrix = 0");
                    } else {
                        Mnew.displayMatrix();
                    }
                }
                else {
                    System.out.print("Masukkan path file: ");
                    getPathname();
                    m.readMatrixFile(pathname);
                }

            }

            else {
                main(args);
            }
        }

        else if (menu == 4) {
            printInputChoice();
            getInputChoice();
            if(inputChoice == 1){
                System.out.print("Masukkan n: ");
                m.row = input.nextInt();
                m.col = 2;
                System.out.printf("Masukkan point:\n");
                m.readMatrixKeyboard();
                double x;
                System.out.printf("Masukkan x:\n");
                x = input.nextDouble();
                double result;
                result = m.InterpolasiPolinom(x);
                System.out.printf("P(%f):%f\n",x,result);
            }
            else {
                System.out.print("Masukkan path file: ");
                getPathname();
                m.readMatrixFile(pathname);
            }
        }

        else if (menu == 5) {

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
        while (menu > 7 || menu < 1)
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
