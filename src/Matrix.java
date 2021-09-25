import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.lang.Math;

public class Matrix {

    /** Atribut **/
    int row;
    int col;
    double[][] Mat = new double[100][100];

    /** Method **/

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

    // Prosedur output
    public void displayMatrix(PrintWriter output) {
        for (int i=0; i<this.row; i++) {
            for (int j=0; j<this.col; j++) {
                output.print(this.Mat[i][j]);
                if (j != this.col-1) output.print(" ");
            }
            output.println();
        }
        output.flush();
    }

    public void displayMatrixSolution(PrintWriter output) {
        boolean foundNonZero;
        for (int i=0; i<this.row; i++) {
            output.printf("x%d = ", i+1);
            foundNonZero = false;
            for (int j=0; j<this.col; j++) {
                if (this.Mat[i][j] != 0d) {
                    if (foundNonZero) {
                        if (this.Mat[i][j] < 0d) output.print(" - ");
                        else output.print(" + ");
                        if (this.Mat[i][j] != 1d) output.printf("%.2f", Math.abs(this.Mat[i][j]));
                        output.print(col2p(j));
                    }
                    else {
                        if (this.Mat[i][j] != 1d) output.printf("%.2f", this.Mat[i][j]);
                        output.print(col2p(j));
                        foundNonZero = true;
                    }
                }
            }
            if (!foundNonZero) output.print("0");
            output.println();
        }
        output.flush();
    }

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

    //Menentukan apakah seluruh elemen baris terakhir bernilai 0 atau tidak
    public boolean isLastRowZero(){
        boolean foundvalue = false;
        int col = 0;
        while(col < (this.col -1) && !foundvalue){
            if(this.Mat[this.row -1][col]!=0)   {foundvalue = true;}
            else                                {col++;}
        }
        return !foundvalue;
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
            while (col <= i-1 && !nonZero){
                if(this.Mat[row][col] != 0)     {nonZero = true;}
                else                            {col++;}
            }
            if(!nonZero && this.Mat[row][col]==1) {found1Utama = true;}     //tidak ada baris yang valid
        }
        if (row==0 && !found1Utama)     {row = 999;}

        return row;
    }

    public Matrix getValue(){
        final int IDX_UNDEF = 999;
        int paramCol = 0;
        int rowMain;
        Matrix solusi = new Matrix();

        for (int i=(this.row -1); i>=0; i--){                 //dari X ke-n s.d. X1
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

    //optimasi matriks
    public void optimizeMat(){
        if (this.row != this.col-1){
            this.row = this.col -1;
        }
    }

    public void gauss(){
        int pass;                   //membaca setiap baris matriks m untuk setiap pembacaan 1 utama
        int row;                    //membaca tiap baris matriks m
        int colEff = 0;             //kolom efektif , dimana 1 utama belum terdefinisi

        for (pass=0 ; pass < this.row ; pass++){
            row = pass;
            boolean isChange = true;

            while (row < this.row) {
                if (this.Mat[row][colEff] == 0 && row == pass) {
                    //memutar baris apabila m[row][col] = 0
                    //isChange menjadi penentu terjadi pertukaran baris atau tidak
                    isChange = this.changeplace(row, colEff);
                }
                if (row == pass && this.Mat[row][colEff] != 1 && isChange) {
                    //m[row][col] bukan 1 utama dan bukan 0 serta bukan tepat di bawah sebuah 1 utama
                    this.bagi1utama(row, colEff);
                }
                if (row > pass && isChange) {
                    //prekondisi : selalu berada dibawah 1 utama
                    //membentuk nilai 0 dibawah 1 utama disesuaikan pada baris ke-row tersebut
                    this.makeZero(row, colEff,pass);

                } else if (!isChange) {row = this.row;}

                row++;
            }
            colEff++;
        }
    }

    public void gaussJordan(){
        this.gauss();
        for (int p = 0; p < this.row - 1; p++) {
            for (int r = p + 1; r < this.row; r++) {
                double ratio = this.Mat[p][r];
                for (int s = 0; s < this.col; s++) {
                    this.Mat[p][s] -= ratio * this.Mat[r][s];
                }
            }
        }
    }

    //penyelesaian SPL - Gauss
    public Matrix solveGauss(){
        //optimasi matriks untuk membentuk matriks Augmented dengan ukuran N x N+1
        this.optimizeMat();
        //membentuk matriks mengikuti metode Gauss
        this.gauss();
        boolean noSolution = (this.isLastRowZero() && this.Mat[this.row-1][this.col-1] != 0);
        Matrix solusi;

        if(noSolution){     //tidak ada solusi
            solusi = null;
        } else{     //banyak solusi ataupun solusi unik
            solusi = this.getValue();
        }
        return solusi;
    }

    public Matrix solveGaussJordan() {
        //optimasi matriks untuk membentuk matriks Augmented dengan ukuran N x N+1
        this.optimizeMat();
        //membentuk matriks mengikuti metode Gauss
        this.gaussJordan();
        boolean noSolution = (this.isLastRowZero() && this.Mat[this.row-1][this.col-1] != 0);
        Matrix solusi;

        if(noSolution){     //tidak ada solusi
            solusi = null;
        } else{     //banyak solusi ataupun solusi unik
            solusi = this.getValue();
        }
        return solusi;
    }

    public Matrix solveInverse(){
        Matrix SPL= new Matrix();
        SPL.row = this.row;
        SPL.col = SPL.row;

        Matrix rs = new Matrix();
        rs.row = this.row;
        rs.col =1;

        Matrix result = new Matrix();
        result.row = SPL.row;
        result.col = rs.col;

        for(int i=0; i<SPL.row;i++){
            rs.Mat[i][0] = this.Mat[i][this.col-1];
            for(int j=0; j<SPL.col; j++){
                SPL.Mat[i][j] = this.Mat[i][j];
            }
        }
        SPL= SPL.InverseIdentity();
        for(int i=0; i<SPL.row;i++){
            for(int j=0; j<rs.col; j++){
                for(int k=0; k<SPL.col; k++){
                    result.Mat[i][j] += SPL.Mat[i][k]* rs.Mat[k][j];
                }
            }
        }
        return result;
    }

    public Matrix solveCramer() {
        int i, j;
        double det0;

        Matrix solusi = new Matrix();
        solusi.row = this.row;
        solusi.col = 1;

        Matrix mCopy = this.getCopy();
        mCopy.col = mCopy.col-1;
        det0 = mCopy.detReduction();

        for (j=0; j<mCopy.col; j++) {
            mCopy = this.getCopy();
            mCopy.col = mCopy.col-1;
            for (i=0; i<mCopy.row; i++) {
                mCopy.Mat[i][j] = this.Mat[i][this.col-1];
            }
            solusi.Mat[j][0] = mCopy.detReduction()/det0;
        }

        return solusi;
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
    /************************* Inverse *************************/
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
        while (i<this.row && flag){
            while(j<this.col && flag){
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
    /******************** Interpolasi Polinom ********************/
    public void interpolasiPolinom(double x, PrintWriter output) {
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
        output.print("P(x)= ");
        for (int a = 0; a < row; a++) {
            output.printf("%.4f",L.Mat[a][3]);
            if(a==1){
                output.print("x");
            }
            else if (a>1){
                output.printf("x^%d",a);
            }
            if (a<row-1){
                if(L.Mat[a+1][3]>=0){
                    output.print("+");
                }
            }
            result += L.Mat[a][3] * Math.pow(x,a);
        }
        output.println();
        output.printf("P(%f) = %.4f",x,result);
    }


    //regresi linear berganda

    //menghitung koefisien tiap elemen
    public double elementValueReg(int k , int col){
        int row;
        double value = 0;
        for (row = 0 ; row < this.row ; row++){
            if(k != 0){
                value += this.Mat[row][k] * this.Mat[row][col];
            }else{
                value += this.Mat[row][col];
            }
        }
        return value;
    }

    //display SPL hasil regresi
    public void displayRegSPL(){
        int i , j;

        for(i=0 ; i < this.row ; i++){
            for(j=0 ; j<this.col ; j++){
                if (j == this.row -1){
                    System.out.println(" = " + this.Mat[i][j]);
                }else if(j == this.row -2){
                    System.out.print(this.Mat[i][j]  + "β" + (j+1));
                }else{
                    System.out.print(this.Mat[i][j]  + "β" + (j+1) + " + ");
                }
            }
        }
        System.out.println();
    }

    //solve regresi linear berganda
    public void regLinearBerganda(){
        int pass=0;
        int row , col;
        Matrix m = new Matrix();
        m.Mat[0][0] = this.row;

        for (row = 1; row < this.row -1; row++) {
            for (col = 1; col < this.col -1; col++) {
                m.Mat[row][col] = this.elementValueReg(pass, col);
            }
            pass++;
        }
        m.displayRegSPL();
        Matrix solusi = m.solveGauss();
    }

    // Parameter conversion (support up to 26 parameters)
    private static String col2p (int i) {
        if (i==0) return "";
        else return Character.toString((char) i+96); //Start: a
    }
}
