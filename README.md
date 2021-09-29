#   Algeo01-20006
##  KELOMPOK BINGUNG

##  Anggota
1. 13520006 - VIONIE NOVENCIA THANGGESTYO
2. 13520071 - WESLY GIOVANO
3. 13520092 - VIERI MANSYL

## Latar Belakang
Seiring berjalannya waktu, seorang mahasiswa tentunya akan menerima banyak tugas,
salah satunya Tubes.
Namun, karena tugas yang melimpah bukanlah rintangan, serta lautan ujian sudahlah biasa,
maka tidak ada namanya keluhan dan malas mengerjakan tugas.
Maka dari itu, Tubes Algeo (IF2123) pertama yang diberikan kepada para mahasiswa,
yang berisikan implementasi secara algoritmik terkait materi aljabar linier,
yaitu SPL, determinan, matriks balikan, dan disertai beberapa aplikasi SPL,
yaitu interpolasi polinom dan regresi linier berganda, takkan menggoyahkan kami.

## Struktur Direktori

- bin: menyimpan hasil kompilasi Main.class dan package packMatrix yang berisi Matrix.class
- doc: berisi laporan tugas besar
- lib: berisi library Matrix.jar
- src: berisi source code Main.java dan package packMatrix yang berisi Matrix.java
- test: berisi masukan data uji *.txt berdasarkan soal studi kasus
- testOutput: berisi luaran data uji *.txt

##  Cara Menggunakan
Di dalam folder bin terdapat sebuah file bytecode, yaitu Main.class
hasil dikompilasi dari Main.java bersama library Matrix.jar.
Library ini mengandung sebuah package yang bernama packMatrix, yang berisi
class Matrix.

Silakan jalankan Main.class pada folder bin untuk menggunakan program utamanya
melalui terminal/console, kemudian masuk ke folder bin, dan jalankan `java Main`.

Tampilan pertama program utama adalah menu dengan enam pilihan:
1. Menghitung solusi sistem persamaan linier
2. Menghitung determinan matriks persegi
3. Menghitung matriks balikan dari matriks persegi
4. Menghitung persamaan interpolasi polinomial dan taksiran nilainya
5. Menghitung persamaan regresi linier berganda dan taksiran nilainya
6. Keluar dari program

Masukan matriks untuk setiap fungsionalitas program utama dapat melalui dua metode,
yaitu melalui terminal/console dan melalui file *.txt yang disimpan dalam folder test.
Luaran dari setiap fungsionalitas akan ditampilkan melalui terminal/console, 
dengan pilihan dapat menyimpan luaran ke dalam file *.txt dalam folder testOutput.
Catatan: filename yang diinputkan termasuk extensionnya, misalnya filename1.txt.
