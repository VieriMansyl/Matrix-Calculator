import java.util.Scanner;

public class driver_matrix {

    public static void main (String[] args) {
        Scanner input = new Scanner(System.in);
        int m,n;
        matrix M = new matrix();

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
        System.out.print  ("Menu yang ingin dipilih: ");
        int menu = input.nextInt();

        while (menu>6 || menu<1)
        {
            System.out.print("Ulangi menu yang ingin dipilih: ");
            menu = input.nextInt();
        }

        if (menu == 1) {
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
            int submenu = input.nextInt();

            while (submenu>5 || submenu<1)
            {
                System.out.print("Ulangi menu yang ingin dipilih: ");
                submenu = input.nextInt();
            }

            if (submenu == 1) {
                M.row = input.nextInt();
                M.col = input.nextInt();
                M.readMatrixKeyboard();
                M.displayMatrix();
                double det = M.detM_reduction();
            }
            else if (submenu == 2) {

            }
            else if (submenu == 3) {

            }
            else if (submenu == 4) {

            }
            else {
                main(args);
            }

        }

        else if (menu == 2) {
            System.out.println();
            System.out.println("---------------------------------");
            System.out.println("            SUBMENU 2            ");
            System.out.println("---------------------------------");
            System.out.println("1. Metode reduksi baris");
            System.out.println("2. Metode Eliminasi Gauss-Jordan");
            System.out.println("3. Kembali ke menu utama");
            System.out.println("---------------------------------");
            System.out.print  ("Submenu yang ingin dipilih: ");
            int submenu = input.nextInt();

            while (submenu>3 || submenu<1)
            {
                System.out.print("Ulangi menu yang ingin dipilih: ");
                submenu = input.nextInt();
            }

            if (submenu == 1) {
                M.row = input.nextInt();
                M.col = M.row;
                M.readMatrixKeyboard();
                double det = M.detM_reduction();
                System.out.println(det);
            }
            else if (submenu == 2) {

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


        /*

        matrix M = new matrix();

        System.out.print("Input m: ");
        int m = input.nextInt();
        System.out.print("Input n: ");
        int n = input.nextInt();

        M.displayMatrix(m, n);

        M.readMatrixKeyboard(m, n);

        M.displayMatrix(m, n);

        */

    }

}
