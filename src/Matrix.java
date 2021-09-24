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

    public int getRowMain(int i) {
        boolean nonZero;
        boolean found1Utama = false;
        int row = i+1;
        int col;
        while (row > 0 && !found1Utama){
            row--; col = 0;
            nonZero = false;
            while (col < i-1 && !nonZero){
                if(this.Mat[row][col] != 0)     {nonZero = true;}
                else                            {col++;}
            }
            if(!nonZero && this.Mat[row][col]==1) {
                found1Utama = true;
            }
        }
        if (row==0 && !found1Utama)     {row = 999;}

        return row;
    }

    public Matrix getValue(){
        final int IDX_UNDEF = 999;
        int paramCol = 0;
        int rowMain;
        Matrix solusi = new Matrix();

        for (int i =this.row-1; i>=0; i--){                 //dari X ke-n s.d. X1
            if (this.getRowMain(i) == IDX_UNDEF) {
                paramCol++;
                solusi.Mat[i][paramCol] = 1;
            }
            else {
                rowMain = this.getRowMain(i);
                for (int j=0; j<=paramCol; j++){            //C, p, q, dst{

                    if (j==0)   {solusi.Mat[i][j] = this.Mat[rowMain][this.col-1];}

                    for (int k=i+1; k<this.row; k++){      //kurangi C1, C2, ...
                        solusi.Mat[i][j] -= this.Mat[rowMain][k] * solusi.Mat[k][j];
                    }
                }
            }
        }
        solusi.row = this.row;
        solusi.col = paramCol+1;

        return solusi;
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
                    //memutar baris apabila m[row][col] = 0
                    //isChange menjadi penentu terjadi pertukaran baris atau tidak
                    isChange = m.changeplace(row, colEff);
                }
                if (row == pass && m.Mat[row][colEff] != 1 && isChange) {
                    //m[row][col] bukan 1 utama dan bukan 0 serta bukan tepat di bawah sebuah 1 utama
                    m.bagi1utama(row, colEff);
                }
                if (row > pass && isChange) {
                    //prekondisi : selalu berada dibawah 1 utama
                    //membentuk nilai 0 dibawah 1 utama disesuaikan pada baris ke-row tersebut
                    m.makeZero(row, colEff,pass);

                } else if (!isChange) {row = this.row;}

                row++;
            }
            colEff++;
        }
        return m.Mat;
    }

    public void gaussJordan(){
        this.Mat = this.gauss();
        for (int p = 0; p < this.row - 1; p++) {
            for (int r = p + 1; r < this.row; r++) {
                double ratio = this.Mat[p][r];
                for (int s = 0; s < this.col; s++) {
                    this.Mat[p][s] -= ratio * this.Mat[r][s];
                }
            }
        }
    }

    /************************ SPL with Gauss ************************/
    public void solveGauss(){

        boolean noSolution = (this.isRowZero(this.row-1 , this.col-1) && this.Mat[this.row-1][this.col-1] != 0);

        Matrix solusi = new Matrix();
        //membentuk matriks mengikuti metode Gauss
        this.gauss();
        //menghitung banyak 1 utama

        if(noSolution){     //tidak ada solusi
            System.out.println("SPL ini tidak ada solusi.");
        } else{     //banyak solusi ataupun solusi unik
            solusi = this.getValue();
            solusi.displayMatrix();
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

    public Matrix MatrixInvalid(){
        Matrix m = new Matrix();
        m.row = this.row;
        m.col = this.col;
        for(int i=0; i<row; i++){
            for(int j=0; j<col;j++){
                m.Mat[i][j] = -999;
            }
        }
        return m;
    }

    public boolean isMatrixInvalid(){
        boolean flag = true;
        int i=0,j=0;
        while (i<this.row && flag == true){
            while(j<this.col && flag == true){
                if (this.Mat[i][j] != -999){
                    flag = false;
                }
                j++;
            }
            i++;
        }
        return flag;
    }

    public Matrix InverseAdjoin(){
        Matrix m = new Matrix();
        m.row = this.row;
        m.col = this.col;
        int cons;

        double determinant = this.detCofactor(this.Mat,this.row);
        if (determinant == 0){
            return (this.MatrixInvalid());
        }

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
        Matrix MT = new Matrix();
        MT.row = m.row;
        MT.col = m.col;
        for(int a=0; a<m.row;a++){
            for(int b=0; b<m.col;b++){
                MT.Mat[a][b] = m.Mat[b][a];
            }
        }
        return MT;
    }

    public Matrix InverseIdentity (){
        Matrix aug = new Matrix();
        aug.row = this.row;
        aug.col = this.col*2;

        Matrix validity= new Matrix();
        validity.row = this.row;
        validity.col = this.col;

        //create Identity
        Matrix I = new Matrix();
        I.row = this.row;
        I.col = this.col;
        for (int i=0; i<I.row; i++) {
            for (int j=0; j<I.col; j++) {
                if(i!=j){
                    I.Mat[i][j] = 0;
                }
                else {
                    I.Mat[i][j] = 1;
                }
            }
        }

        double temp;
        for(int i=0; i<this.row; i++){
            for(int j=0; j<this.col*2; j++){
                if(j<this.col){
                    aug.Mat[i][j] = this.Mat[i][j];
                }
                else{
                    aug.Mat[i][j] = I.Mat[i][j-this.col];
                }
            }
        }
        aug.gaussJordan();

        for (int i=0; i<validity.row; i++){
            for(int j=0; j<validity.col; j++){
                validity.Mat[i][j] = aug.Mat[i][j];
            }
        }

        int zero=0;
        boolean isZero=false;
        for(int i=0; i<validity.row; i++){
            for(int j=0; j<validity.col; j++){
                if(validity.Mat[i][j]==0){
                    zero+=1;
                }
            }
            if (zero == validity.col){
                isZero = true;
                break;
            }
            else{
                zero=0;
            }
        }
        if (isZero){
            return I.MatrixInvalid();
        }
        else{
            Matrix result= new Matrix();
            result.row = I.row;
            result.col = I.col;
            for (int i=0; i<result.row;i++){
                for(int j=0; j<result.col;j++){
                    result.Mat[i][j] = aug.Mat[i][I.col+j];
                }
            }
            return result;
        }

    }

    public double Pangkat(double x, int y){
        if (y==1){
            return x;
        }
        else if(y==2){
            return(x*x);
        }
        else if(y==0){
            return 1;
        }
        return (x*(Pangkat(x,y-1)));
    }

    public double InterpolasiPolinom(double x) {
        Matrix L = new Matrix();
        L.row = this.row;
        L.col = 4;

        for (int i = 0; i < L.row; i++) {
            L.Mat[i][0] = 1;
        }

        for (int j = 0; j < L.row; j++) {
            L.Mat[j][1] = this.Mat[j][0];
        }

        for (int k = 0; k < L.row; k++) {
            L.Mat[k][2] = this.Mat[k][0] * this.Mat[k][0];
        }
        for (int l = 0; l < L.row; l++) {
            L.Mat[l][3] = this.Mat[l][1];
        }
        L.gaussJordan();
        double result = 0;
        for (int a = 0; a < row; a++) {
            result += L.Mat[a][3] * Pangkat(x,a);
        }
        return result;
    }
}
