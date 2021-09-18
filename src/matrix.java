import java.util.Scanner;

public class matrix {

    // Atribut
    int[][] Mat = new int[100][100];

    // Method

    // Konstruktor
    matrix() {
        int i,j;
        for (i=0; i<100; i++) {
            for (j=0; j<100; j++) {
                this.Mat[i][j] = 0;
            }
        }
    }

    // Prosedur input dari Keyboard
    public void readMatrixKeyboard(int m, int n) {
        Scanner input = new Scanner(System.in);
        int i,j;
        for (i=0; i<m; i++) {
            for (j=0; j<n; j++) {
                System.out.printf("Matrix[%d][%d]",i, j); this.Mat[i][j] = input.nextInt();
            }
        }
        input.close();
    }

    // Prosedur output ke layar
    public void displayMatrix(int m, int n) {
        int i,j;
        for (i=0; i<m; i++) {
            for (j=0; j<n; j++) {
                System.out.print(this.Mat[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
