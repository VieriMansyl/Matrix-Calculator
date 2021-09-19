import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class matrix {

    // Atribut
    int row;
    int col;
    double[][] Mat = new double[100][100];

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
    public void readMatrixKeyboard() {
        Scanner input = new Scanner(System.in);
        int i,j;
        for (i=0; i<this.row; i++) {
            for (j=0; j<this.col; j++) {
                this.Mat[i][j] = input.nextDouble();
            }
        }
        input.close();
    }

    // Prosedur input dari File
    public void readMatrixFile(String filename) {
        try {
            int i=0, j=0;
            String pathname = "test/" + filename;
            File matFile = new File(pathname);
            Scanner fileRead = new Scanner(matFile);

            while (fileRead.hasNextLine()) {
                String data = fileRead.nextLine();
                Scanner lineRead = new Scanner(data);
                j = 0;
                while (lineRead.hasNextDouble()) {
                    this.Mat[i][j] = lineRead.nextDouble();
                    j++;
                }
                i++;
            }
            this.row = i;
            this.col = j;
            fileRead.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    // Prosedur output ke layar
    public void displayMatrix() {
        int i,j;
        for (i=0; i<this.row; i++) {
            for (j=0; j<this.col; j++) {
                System.out.print(this.Mat[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    // Prosedur output ke file
    public void saveMatrix() {

    }


    /**********************************VIERI**********************************/
    //Metode eliminasi Gauss (A)            - progress
    //Metode eliminasi Gauss Jordan (A)     - progress
    //determinan :
    //reduksi baris (A)                     - done


    //eliminasi gauss
    
    /*****Menentukan apakah seluruh elemen kolom ke-row bernilai 0 atau tidak***/
    public static int isColZero(double[][] m , int row , int rowM , int col){
        boolean isfound = false;

        while (row < rowM && !isfound){
            if(m[row][col]!=0){
                isfound = true;
            }else{
                row++;
            }
        }
        return row;
    }
    /*****Menentukan apakah seluruh elemen baris ke-row bernilai 0 atau tidak***/
    public static boolean isRowZero(double[][] m , int row , int colM){
        boolean foundvalue = false;
        int col=0;
        while(col < colM){
            if(m[row][col]!=0)  {foundvalue = true;}
            else                {col++;}
        }
        return foundvalue;
    }

    /*****Menukar baris dengan elemen terdefinisi pada kolom ke-col*****/
    public static boolean changeplace(double[][] m ,int rowM , int colM , int row , int col){
        int change_row;
        double temp;
        boolean ischange = false;
        
        change_row = isColZero(m , row , rowM , col);
        if (change_row != row){
            ischange = true;
            for (col=0 ; col < colM ; col++){
                temp = m[change_row][col];
                m[change_row][col] = m[row][col];
                m[row][col] = temp;
            }
        }
        return ischange;
    }

    /*****konversi elemen menjadi 1 utama disesuaikan pada baris ke-row tersebut*****/
    public static void bagi1utama(double[][] m , int row , int col , int colM) {
        double pembagi;
        pembagi = m[row][col];
        for (col=0 ; col < colM ; col++){
            m[row][col] /= pembagi;
        }
    }
    
    /*****konversi kolom ke 0 disesuaikan pada baris ke-row tersebut*****/
    public static void makeZero(double[][] m , int row , int col , int colM , int pass){
        int j = col;
        while (j < colM){
            m[row][j] -= (m[row][j] / m[pass][j]) * m[pass][j];
            j++;
        }
    }
    
    /************************membentuk matriks segitiga bawah************************/
    
    public static double[][] trianglebawah (double[][] m , int rowM , int colM , int option) {
        //option 1 = SPL dengan metode gauss
        //option 2 = menentukan determinan dengan OBE
        int pass;                   //membaca setiap baris matriks m untuk setiap pembacaan 1 utama
        int row;                    //membaca tiap baris matriks m
        int colEff = 0;             //kolom efektif , dimana 1 utama belum terdefinisi


        for (pass=0 ; pass < rowM ; pass++){
            row = pass;
            boolean isChange = true;

            //untuk eliminasi gauss
            if (option == 1){
                for (row=pass ; row < rowM ; row++){
                    if(m[row][colEff]==0 && row==pass){
                        //mengecek apabila m[row][col] = 0
                        isChange = changeplace(m , rowM , colM , row , colEff);
                    }
                    if(row==pass && m[row][colEff]!=1 && m[row][colEff]!=0){
                        //m[row][col] bukan 1 utama dan bukan 0 serta bukan tepat di bawah sebuah 1 utama
                        bagi1utama(m , row , colEff , colM);
                    }
                    if(row > pass && isChange){
                        //prekondisi : selalu berada dibawah 1 utama
                        //membentuk nilai 0 dibawah 1 utama disesuaikan pada baris ke-row tersebut
                        makeZero(m , row , colEff , colM , pass);
                    }
                }
            }
            //untuk determinan dengan metode reduksi baris elementer
            else if(option == 2){
                while (row < rowM){
                    if(m[row][colEff]==0 && row==pass){
                        //mengecek apabila m[row][col] = 0
                        isChange = changeplace(m , rowM , colM , row , colEff);
                    }
                    if(row > pass && isChange){
                        //membentuk nilai 0 dibawah 1 utama disesuaikan pada baris ke-row tersebut
                        makeZero(m , row , colEff , colM , pass);
                    }else{
                        //seluruh elemen di 1 kolom colEff bernilai 0
                        row = rowM;
                    }
                    row++;
                }
            }
            colEff++;
        }

        return m;
    }
    
    /*************************DETERMINAN*************************/
    public double detM_reduction(){
        double[][] new_M;
        double detM = 1;
        int j=0;

        new_M = trianglebawah(this.Mat, this.row, this.col, 2);
        for(int i=0; i < this.row ; i++) {
            detM *= new_M[i][j];
            j++;
        }
        return detM;

    }

}