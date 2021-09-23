import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Matrix {

    // Atribut
    int row;
    int col;
    double[][] Mat = new double[100][100];

    // Method

    // Konstruktor
    Matrix() {
        int i,j;
        for (i=0; i<100; i++) {
            for (j=0; j<100; j++) {
                this.Mat[i][j] = 0;
            }
        }
    }


    // Copy Matrix
    public Matrix getCopy() {
        Matrix m = new Matrix();
        m.row = this.row;
        m.col = this.col;

        for (int i=0; i<m.row; i++) {
            for (int j=0; j<m.col; j++) {
                m.Mat[i][j] = this.Mat[i][j];
            }
        }

        return m;
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
        //input.close();
    }

    // Prosedur input dari File
    public void readMatrixFile(String pathname) {
        try {
            int i=0, j=0;
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


    //Menentukan apakah seluruh elemen kolom ke-row bernilai 0 atau tidak
    public int isColZero(int row , int col){
        boolean isfound = false;

        while (row < this.row && !isfound){
            if(this.Mat[row][col]!=0){
                isfound = true;
            }else{
                row++;
            }
        }
        return row;
    }
    //Menentukan apakah seluruh elemen baris ke-row bernilai 0 atau tidak
    public boolean isRowZero(int row , int colM){
        boolean foundvalue = false;
        int col = 0;
        while(col < colM){
            if(this.Mat[row][col]!=0)  {foundvalue = true;}
            else                {col++;}
        }
        return foundvalue;
    }

    //Mencari kolom yang memiliki 1 utama pada baris ke-row
    public int get1Utama(int row){
        int col = 0;
        boolean found = false;
        while (col < (this.col-1) && !found){
            if(this.Mat[row][col]==1)   {found = true;}
            else                        {col++;}
        }
        return col;
    }

    //Menukar baris dengan elemen terdefinisi pada kolom ke-col
    public boolean changeplace(int row , int col){
        int change_row;
        double temp;
        boolean ischange = false;
        
        change_row = this.isColZero(row , col);
        if ((change_row != row) && (change_row != this.row)){
            ischange = true;
            for (col=0 ; col < this.col ; col++){
                temp                        = this.Mat[change_row][col];
                this.Mat[change_row][col]   = this.Mat[row][col];
                this.Mat[row][col]          = temp;
            }
        }
        return ischange;
    }

    //konversi elemen menjadi 1 utama disesuaikan pada baris ke-row tersebut
    public void bagi1utama(int row , int col) {
        double pembagi;
        pembagi = this.Mat[row][col];
        for (col=0 ; col < this.col ; col++){
            this.Mat[row][col] /= pembagi;
        }
    }
    
    //konversi kolom ke 0 disesuaikan pada baris ke-row tersebut
    public void makeZero(int row , int col , int pass){
        int j = col;
        double divisor =  this.Mat[pass][j];
        double divident = this.Mat[row][j];
        while (j < this.col){
            this.Mat[row][j] -= (divident / divisor) * this.Mat[pass][j];
            j++;
        }
    }



    /************************ Gauss ************************/

    public double[][] gauss(){
        int pass;                   //membaca setiap baris matriks m untuk setiap pembacaan 1 utama
        int row;                    //membaca tiap baris matriks m
        int colEff = 0;             //kolom efektif , dimana 1 utama belum terdefinisi

        Matrix m = this.getCopy();

        for (pass=0 ; pass < this.row ; pass++){
            row = pass;
            boolean isChange = true;

            while (row < this.row) {
                if (m.Mat[row][colEff] == 0 && row == pass) {
                    //mengecek apabila m[row][col] = 0
                    isChange = m.changeplace(row, colEff);
                }
                if (row == pass && m.Mat[row][colEff] != 1 && this.Mat[row][colEff] != 0) {
                    //m[row][col] bukan 1 utama dan bukan 0 serta bukan tepat di bawah sebuah 1 utama
                    m.bagi1utama(row, colEff);
                }
                if (row > pass && isChange) {
                    //prekondisi : selalu berada dibawah 1 utama
                    //membentuk nilai 0 dibawah 1 utama disesuaikan pada baris ke-row tersebut
                    m.makeZero(row, colEff,pass);
                } else if (!isChange) {
                    row = this.row;
                }
                row++;
            }
            colEff++;
        }
        return m.Mat;
    }

    /************************ SPL with Gauss ************************/
    public void solveGauss(){
        int total_1utama = 0;
        int row;
        int col = 0;
        boolean found;

        //menghitung banyak 1 utama
        for (row = 0; row < this.row ; row++){
                if(this.isRowZero(row , this.col-1) && this.Mat[row][this.col-1] != 0){              //tidak ada solusi
                    System.out.println("SPL ini tidak ada solusi.");
                } else if(this.isRowZero(row , this.col-1) && this.Mat[row][this.col-1] == 0){       //banyak solusi
                    //banyak solusi
                }else{                                                                  //solusi unik
                    //solusi unik
                }
        }



    }

    /************************* DETERMINAN *************************/
    public double detReduction () {
        int pass;                   //membaca setiap baris matriks m untuk setiap pembacaan 1 utama
        int row;                    //membaca tiap baris matriks m
        int colEff = 0;             //kolom efektif , dimana 1 utama belum terdefinisi
        double detM = 1;

        Matrix m = this.getCopy();

        for (pass=0 ; pass < this.row ; pass++){
            row = pass;
            boolean isChange = true;

            while (row < this.row) {
                if (m.Mat[row][colEff] == 0 && row == pass) {
                    //mengecek apabila m[row][col] = 0
                    isChange = m.changeplace(row, colEff);
                }
                if (row > pass && isChange) {
                    //prekondisi : selalu berada dibawah 1 utama
                    //membentuk nilai 0 dibawah 1 utama disesuaikan pada baris ke-row tersebut
                    m.makeZero(row, colEff, pass);
                } else if (!isChange) {
                    row = this.row;
                }
                row++;
            }
            colEff++;
        }

        for(int i=0; i < this.row ; i++)    {detM *= m.Mat[i][i];}

        return detM;
    }


    public void solveCramer() {
        int i, j;
        double[] detList = new double[this.col];
        double solutionValue;

        Matrix MCopy = this.getCopy();
        MCopy.col = MCopy.col-1;
        detList[0] = MCopy.detReduction();

        for (j=0; j<MCopy.col; j++) {
            MCopy = this.getCopy();
            MCopy.col = MCopy.col-1;
            for (i=0; i<MCopy.row; i++) {
                MCopy.Mat[i][j] = this.Mat[i][this.col-1];
            }
            detList[j+1] = MCopy.detReduction();
            solutionValue = detList[j+1]/detList[0];
            System.out.printf("x%d = %.2f\n", j+1, solutionValue);
        }
    }

    public void getCofactor(double [][]mat, double [][]temp, int rows, int cols, int dims){
        int i=0,j=0,nrow,ncol;
        for(nrow = 0; nrow<dims;nrow++){
            for(ncol = 0; ncol<dims;ncol++){
                if(nrow!=rows && ncol !=cols){
                    temp[i][j] = mat[nrow][ncol];
                    j++;
                    if(j==dims-1){
                        j=0;
                        i++;
                    }
                }
            }
        }
    }

    public double detCofactor(double [][]mat,int dim){
        double det = 0;
        if(dim==1)
            return mat[0][0];
        if(dim==2){
            return(mat[0][0]*mat[1][1] - mat[0][1]*mat[1][0]);
        }
        double [][]temp = new double [100][100];
        int cons = 1,k;

        for (k=0;k<dim;k++){
            getCofactor(mat,temp,0,k,dim);
            det += cons*mat[0][k]* detCofactor(temp,dim-1);
            cons *= -1;
        }
        return det;
    }

    public Matrix CreateIdentity(){
        Matrix m = new Matrix();
        m.row = this.row;
        m.col = this.col;

        for (int i=0; i<m.row; i++) {
            for (int j=0; j<m.col; j++) {
                if(i!=j){
                    m.Mat[i][j] = 0;
                }
                else {
                    m.Mat[i][j] = 1;
                }
            }
        }

        return m;
    }

    public Matrix InversAdjoin(){
        //Prekondisi determinan !=0
        Matrix m = new Matrix();
        m.row = this.row;
        m.col = this.col;
        int cons;

        double determinant = this.detCofactor(this.Mat,this.row);

        Matrix temp = new Matrix();
        temp.row = m.row-1;
        temp.col = m.col-1;
        for(int i=0; i<m.row;i++){
            for(int j=0; j<m.col;j++){
                temp.getCofactor(this.Mat,temp.Mat,i,j,this.row);
                if ((i+j)%2 == 0){
                    cons = 1;
                }
                else{
                    cons = -1;
                }
                double det = temp.detCofactor(temp.Mat,temp.row);
                m.Mat[i][j] = cons*det;
            }
        }

        for(int p=0; p<m.row;p++) {
            for (int q = 0; q < m.col; q++) {
                m.Mat[p][q] /=  determinant;
            }
        }
        return m;
    }

}
