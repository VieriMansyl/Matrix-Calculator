import java.util.Scanner;

public class Main {

    private static Scanner input;
    private static int menu, submenu;

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

            if (submenu == 1) {

            }
            else if (submenu == 2) {

            }
            else if (submenu == 3) {

            }
            else if (submenu == 4) {
                System.out.print("Masukkan jumlah persamaan SPL: ");
                m.row = input.nextInt();
                m.col = m.row+1;
                System.out.printf("Masukkan matriks augmented %dx%d:\n", m.row, m.col);
                m.readMatrixKeyboard();
                m.solveCramer();
            }
            else {
                main(args);
            }

        }

        else if (menu == 2) {
            printSubmenu2();
            getSubmenu2();

            if (submenu == 1) {
                System.out.print("Masukkan n: ");
                m.row = input.nextInt();
                m.col = m.row;
                System.out.printf("Masukkan matriks persegi %dx%d:\n", m.row, m.col);
                m.readMatrixKeyboard();
                double det = m.detReduction();
                System.out.printf("Nilai determinan matriks tersebut adalah %.2f", det);

            }
            else if (submenu == 2) {
                System.out.print("Masukkan n: ");
                m.row = input.nextInt();
                m.col = m.row;
                m.readMatrixKeyboard();
                double det = m.detCofactor(m.Mat,m.row);
                System.out.printf("Nilai determinan matriks tersebut adalah %.2f",det);
            }
            else {
                main(args);
            }
        }

        else if (menu == 3) {

        }

        else if (menu == 4) {

        }

        else if (menu == 5) {

        }

        else {
            System.exit(0);
        }


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
        System.out.println("7. File matriks");  // Ingat dihapus
        System.out.println("---------------------------------");
        System.out.print  ("Menu yang ingin dipilih: ");
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
        System.out.print  ("Submenu yang ingin dipilih: ");
    }

    public static void printSubmenu2() {
        System.out.println();
        System.out.println("---------------------------------");
        System.out.println("            SUBMENU 2            ");
        System.out.println("---------------------------------");
        System.out.println("1. Metode reduksi baris");
        System.out.println("2. Metode Eliminasi Gauss-Jordan");
        System.out.println("3. Kembali ke menu utama");
        System.out.println("---------------------------------");
        System.out.print  ("Submenu yang ingin dipilih: ");
    }

    private static void getMenu() {
        menu = input.nextInt();
        while (menu>7 || menu<1)
        {
            System.out.print("Ulangi menu yang ingin dipilih: ");
            getMenu();
        }
        System.out.println();
    }

    private static void getSubmenu1() {
        submenu = input.nextInt();
        while (submenu>5 || submenu<1)
        {
            System.out.print("Ulangi menu yang ingin dipilih: ");
            getSubmenu1();
        }
        System.out.println();
    }

    private static void getSubmenu2() {
        submenu = input.nextInt();
        while (submenu>3 || submenu<1)
        {
            System.out.print("Ulangi menu yang ingin dipilih: ");
            getSubmenu2();
        }
        System.out.println();
    }

}
