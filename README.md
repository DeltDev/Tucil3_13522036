# Deskripsi Singkat
Program ini adalah program yang berfungsi untuk mencari jalur terpendek dalam permainan Word Ladder. Ada 3 pilihan metode pencarian jalur terpendek di program ini, yaitu UCS, A*, dan Greedy Best First Search.
# Requirements
1. ```Java ver 19.0.2```
2. ```JavaFX SDK ver 22.0.1``` unduh di [sini](https://openjfx.io/)

# Cara Menjalankan Program
1. Anda **WAJIB** mengunduh JavaFX SDK versi 22.0.1 terlebih dahulu di situs yang sudah diberikan di [Requirements](#Requirements) sebelum menjalankan program ini, karena SDK ini diperlukan untuk menjalankan program ini.
2. Setelah mengunduh JavaFX SDK, unzip file JavaFX SDK tersebut di direktori bebas.
3. Ketik ```java --module-path "Direktori JavaFX SDK yang sudah diunzip sampai ke folder lib yang terletak di dalam folder javafx-sdk-22.0.1" --add-modules=javafx.controls,javafx.fxml -jar bin/app/WordLadderSolver.jar``` ke terminal di direktori root repository ini.
<br>Untuk lebih jelasnya, misalkan jika Anda melakukan unzip file JavaFX SDK di dalam folder ```D:\Java Development```, maka buka terminal/command prompt/powershell **DI DALAM FOLDER ROOT REPOSITORY INI**, lalu perintah yang Anda tuliskan ke dalam terminal adalah ```java --module-path "D:\Java Development\openjfx-22.0.1_windows-x64_bin-sdk\javafx-sdk-22.0.1\lib" --add-modules=javafx.controls,javafx.fxml -jar bin/app/WordLadderSolver.jar```. Direktori yang di salin harus sampai ke folder lib.
4. Anda boleh mengubah isi ```dictionary.txt``` yang berada di dalam src/dictionary dengan syarat **semua huruf dalam suatu kata wajib dalam huruf kecil dan kata baru akan dianggap sebagai kata yang berbeda oleh program jika Anda menulis kata tersebut di baris baru**.
5. **DILARANG MENGHAPUS FOLDER SRC/CACHE BESERTA FILE .GITKEEP (.gitkeep hanya berfungsi agar folder cache tidak hilang di repository ini) YANG BERADA DI DALAMNYA KARENA PROGRAM INI MEMERLUKAN FOLDER TERSEBUT UNTUK PEMROSESAN PATH.**
6. **DILARANG MENGHAPUS FILE .DAT APAPUN YANG DIBUAT OLEH PROGRAM INI DI DALAM FOLDER SRC/CACHE SAAT PROGRAM SEDANG BERJALAN KARENA PROGRAM INI MEMBUTUHKAN SEMUA FILE .DAT (kecuali .gitkeep) YANG BERADA DI DALAM FOLDER TERSEBUT UNTUK PENCARIAN PATH**. <br> Keterangan: semua file .dat akan terhapus secara otomatis jika Anda menutup program ini
7. **DILARANG MENGHAPUS FILE APAPUN DI FOLDER BIN KARENA DIPERLUKAN UNTUK RUN PROGRAM INI**.
8. **JANGAN TUTUP COMMAND PROMPT/ POWERSHELL/ TERMINAL SAAT MENJALANKAN PROGRAM INI**
# Cara Menggunakan Program
1. [Jalankan Program ini terlebih dahulu](#Cara-Menjalankan-Program)
2. Di layar komputer Anda akan ditampilkan layar landing dengan tombol mulai di bawahnya. Klik tombol tersebut untuk mulai menggunakan program.
3. Input kata awal dan kata akhir. Input kata bersifat **CASE INSENSITIVE**.
4. Anda **wajib** memilih salah satu dari 3 algoritma pencarian (UCS,A*,Greedy Best First Search) dari dropdown yang berada di tengah.
5. Jika input 3 dan 4 tidak valid, program akan mengeluarkan error di GUI.
6. Klik tombol "Cari!" untuk memulai pencarian path word ladder.
7. Hasil akan ditampilkan ke layar. Anda bisa kembali ke layar sebelumnya dengan mengklik tombol "Kembali".
# Tentang Pembuat Program
| Nama          | NIM    | Kelas Strategi Algoritma|
| --------------|--------| ----|
|Akbar Al Fattah|13522036| K-02|

# Check List Program
| No | Poin | Ya | Tidak |
| --- | --- | --- | --- |
| 1 | Program berhasil dijalankan | V | |
| 2 | Program dapat menemukan rangkaian kata dari start word ke end word sesuai aturan permainan dengan algoritma UCS | V | |
| 3 | Solusi yang diberikan pada algoritma UCS optimal | V | |
| 4 | Program dapat menemukan rangkaian kata dari start word ke end word sesuai aturan permainan dengan algoritma Greedy Best First Search | V | |
| 5 |  Program dapat menemukan rangkaian kata dari start word ke end word sesuai aturan permainan dengan algoritma A* | V | |
| 6 |  Solusi yang diberikan pada algoritma A* optimal | V | |
| 7 |  Bonus: Program memiliki tampilan GUI | V | |
