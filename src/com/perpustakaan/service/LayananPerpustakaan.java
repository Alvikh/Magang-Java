package com.perpustakaan.service;

import com.perpustakaan.model.Buku;
import com.perpustakaan.model.CatatanPeminjaman;
import com.perpustakaan.utils.InputUtils;

import java.io.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class LayananPerpustakaan {
    // Daftar buku yang tersedia di perpustakaan
    private List<Buku> daftarBuku = new ArrayList<>();

    // Daftar catatan peminjaman buku
    private List<CatatanPeminjaman> daftarPeminjaman = new ArrayList<>();

    // Lokasi file untuk menyimpan data buku
    private final String filePathBuku = "data_buku.txt";

    // Lokasi file untuk menyimpan data peminjaman
    private final String filePathPeminjaman = "data_peminjaman.txt";

    // Lokasi file untuk menyimpan laporan
    private final String filePathLaporan = "laporan.txt"; 

    // Konstruktor kelas yang akan memuat data buku dan data peminjaman dari file
    public LayananPerpustakaan() {
        muatDataBuku(); // Memanggil metode untuk memuat data buku dari file
        muatDataPeminjaman(); // Memanggil metode untuk memuat data peminjaman dari file
    }
    
    public void tambahBuku(Scanner scanner) {
        // Meminta pengguna memasukkan judul buku
        System.out.print("Masukkan judul buku: ");
        String judul = InputUtils.bacaString(scanner);

        // Meminta pengguna memasukkan penulis buku
        System.out.print("Masukkan penulis buku: ");
        String penulis = InputUtils.bacaString(scanner);

        // Meminta pengguna memasukkan ISBN buku
        System.out.print("Masukkan ISBN buku: ");
        String isbn = InputUtils.bacaString(scanner);

        // Meminta pengguna memasukkan stok buku
        System.out.print("Masukkan stok buku: ");
        int stok = InputUtils.bacaInt(scanner);

        // Membuat objek Buku baru dengan data yang dimasukkan oleh pengguna
        Buku buku = new Buku(judul, penulis, isbn, stok);

        // Menambahkan buku baru ke daftar buku
        daftarBuku.add(buku);

        // Menyimpan data buku ke file
        simpanDataBuku();

        // Memberikan konfirmasi bahwa buku berhasil ditambahkan
        System.out.println("Buku berhasil ditambahkan!");
    }

    // Metode untuk menyimpan data buku ke file
    private void simpanDataBuku() {  //Metode simpanDataBuku tidak menerima parameter apapun dan tidak mengembalikan nilai (void).
    	//menggunakan mekanisme try-with-resources untuk membuat objek BufferedWriter Objek ini digunakan untuk menulis data ke file. Nama file ditentukan oleh variabel filePathBuku.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePathBuku))) { 
            // Menulis setiap buku di daftar buku ke file
            for (Buku buku : daftarBuku) { // sebuah loop yang mengiterasi setiap objek Buku yang ada di dalam daftar bernama daftarBuku.
                writer.write(String.format("%s|%s|%s|%d%n", buku.getJudul(), buku.getPenulis(), buku.getIsbn(), buku.getStok())); //metode write dari BufferedWriter dipanggil untuk menulis data buku ke file.
            }
            //Jika terjadi kesalahan (misalnya, jika file tidak dapat ditulis), blok catch akan menangkap IOException dan mencetak pesan kesalahan ke konsol dengan menggunakan System.out.println.
        } catch (IOException e) {
            // Menangani kesalahan yang mungkin terjadi saat menyimpan data buku
            System.out.println("Gagal menyimpan data buku : " + e.getMessage());
        }
    }

    // Metode untuk memuat data buku dari file
    private void muatDataBuku() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePathBuku))) {
            String line;
            // Membaca setiap baris di file
            while ((line = reader.readLine()) != null) { //sebuah loop yang membaca setiap baris dalam file sampai akhir file tercapai (readLine mengembalikan null).
                String[] parts = line.split("\\|");
                // Memastikan bahwa setiap baris memiliki 4 bagian
                if (parts.length == 4) { //memastikan bahwa setiap baris yang diproses memiliki tepat 4 bagian, sesuai dengan format data buku yang diharapkan (judul, penulis, ISBN, dan stok).
                    String judul = parts[0].trim();
                    String penulis = parts[1].trim();
                    String isbn = parts[2].trim();
                    int stok = Integer.parseInt(parts[3].trim());

                    // Membuat objek Buku baru dengan data dari file
                    Buku buku = new Buku(judul, penulis, isbn, stok); //membuat objek Buku baru menggunakan data yang telah dibaca dan dibersihkan.

                    // Menambahkan buku ke daftar buku
                    daftarBuku.add(buku); //menambahkan objek Buku yang baru dibuat ke dalam daftar daftarBuku.
                }
            }
        } catch (IOException e) {
            // Menangani kesalahan yang mungkin terjadi saat memuat data buku
            System.out.println("Gagal memuat data buku: " + e.getMessage());
        }
    }

    // Metode untuk menampilkan daftar buku
    public void lihatBuku() {
        // Memeriksa apakah daftar buku kosong
        if (daftarBuku.isEmpty()) {
            System.out.println("Tidak ada buku yang tersedia.");
        } else {
            // Mendapatkan panjang kolom untuk memastikan format yang rapi
            final int JUDUL_BUKU_PANJANG = 45;
            final int PENULIS_PANJANG = 30;
            final int ISBN_PANJANG = 15;
            final int STOK_PANJANG = 5;

            // Format header tabel
            System.out.println("\n============================================== Daftar Buku ==============================================");
            System.out.println("=========================================================================================================");
            String headerFormat = String.format("%%-%ds | %%-%ds | %%-%ds | %%-%ds|%n", 
                                                JUDUL_BUKU_PANJANG, PENULIS_PANJANG, ISBN_PANJANG, STOK_PANJANG);
            String rowFormat = headerFormat;

            // Menampilkan header tabel
            System.out.printf(rowFormat, "Judul Buku", "Penulis", "ISBN", "Stok");
            System.out.println("=".repeat(JUDUL_BUKU_PANJANG + PENULIS_PANJANG + ISBN_PANJANG + STOK_PANJANG + 10));

            // Menampilkan data buku
            for (Buku buku : daftarBuku) {
                System.out.printf(rowFormat, buku.getJudul(), buku.getPenulis(), buku.getIsbn(), buku.getStok());
            }
        }
        System.out.println("=========================================================================================================");
    }

    public void cariBukuBerdasarkanJudul(Scanner scanner) {
        // Meminta pengguna memasukkan judul buku yang ingin dicari
        System.out.print("Masukkan judul buku yang ingin dicari: ");
        String judul = InputUtils.bacaString(scanner);

        boolean ditemukan = false; // Variabel untuk melacak apakah buku ditemukan
        
        // Mendapatkan panjang kolom untuk memastikan format yang rapi
        final int JUDUL_BUKU_PANJANG = 45;
        final int PENULIS_PANJANG = 30;
        final int ISBN_PANJANG = 15;
        final int STOK_PANJANG = 5;

        // Format header tabel
        System.out.println("\n=============================================== Cari Buku ===============================================");
        System.out.println("=========================================================================================================");
        String headerFormat = String.format("%%-%ds | %%-%ds | %%-%ds | %%-%ds|%n", 
                                            JUDUL_BUKU_PANJANG, PENULIS_PANJANG, ISBN_PANJANG, STOK_PANJANG);
        String rowFormat = headerFormat; // Format untuk baris data

        // Loop melalui daftar buku
        for (Buku buku : daftarBuku) {
            // Membandingkan judul buku dengan input pengguna (case-insensitive)
            if (buku.getJudul().equalsIgnoreCase(judul)) {
                if (!ditemukan) {
                    // Menampilkan header tabel hanya sekali
                    System.out.printf(rowFormat, "Judul Buku", "Penulis", "ISBN", "Stok");
                    System.out.println("=".repeat(JUDUL_BUKU_PANJANG + PENULIS_PANJANG + ISBN_PANJANG + STOK_PANJANG + 10));
                    ditemukan = true;
                }
                // Menampilkan informasi buku yang ditemukan
                System.out.printf(rowFormat, buku.getJudul(), buku.getPenulis(), buku.getIsbn(), buku.getStok());
                System.out.println("=========================================================================================================");
            }
        }

        // Jika tidak ada buku yang ditemukan, menampilkan pesan ini
        if (!ditemukan) {
            System.out.println("Buku tidak ditemukan.");
        }
    }

    public void perbaruiBuku(Scanner scanner) {
        // Menampilkan header untuk menu perbarui buku
        System.out.println("\n===== Perbarui Buku =====");
        // Meminta pengguna untuk memasukkan ISBN buku yang ingin diperbarui
        System.out.print("Masukkan ISBN buku yang ingin diperbarui: ");
        String isbn = InputUtils.bacaString(scanner);

        Buku buku = null;
        // Mencari buku berdasarkan ISBN dalam daftarBuku
        for (Buku b : daftarBuku) {
            if (b.getIsbn().equals(isbn)) {
                buku = b;
                break;
            }
        }

        // Jika buku ditemukan, memperbarui detail buku
        if (buku != null) {
            System.out.print("Masukkan judul baru (kosongkan jika tidak ingin mengubah): ");
            String judul = InputUtils.bacaString(scanner);
            if (!judul.isEmpty()) {
                buku.setJudul(judul);
            }

            System.out.print("Masukkan penulis baru (kosongkan jika tidak ingin mengubah): ");
            String penulis = InputUtils.bacaString(scanner);
            if (!penulis.isEmpty()) {
                buku.setPenulis(penulis);
            }

            System.out.print("Masukkan stok baru (kosongkan jika tidak ingin mengubah): ");
            String stokInput = InputUtils.bacaString(scanner);
            if (!stokInput.isEmpty()) {
                try {
                    int stok = Integer.parseInt(stokInput);
                    buku.setStok(stok);
                } catch (NumberFormatException e) {
                    System.out.println("Stok tidak valid. Tidak ada perubahan yang dilakukan.");
                }
            }

            // Menyimpan data buku yang telah diperbarui
            simpanDataBuku();
            System.out.println("\nBuku berhasil diperbarui!");
        } else {
            // Menampilkan pesan jika buku dengan ISBN yang dimasukkan tidak ditemukan
            System.out.println("Buku dengan ISBN tersebut tidak ditemukan.");
        }
    }

    public void hapusBuku(Scanner scanner) {
        // Meminta pengguna untuk memasukkan ISBN buku yang ingin dihapus
        System.out.print("Masukkan ISBN buku yang ingin dihapus: ");
        String isbn = InputUtils.bacaString(scanner);

        Buku bukuToRemove = null;
        // Mencari buku berdasarkan ISBN dalam daftarBuku
        for (Buku buku : daftarBuku) {
            if (buku.getIsbn().equals(isbn)) {
                bukuToRemove = buku;
                break;
            }
        }

        // Jika buku ditemukan, menghapus buku dari daftarBuku
        if (bukuToRemove != null) {
            daftarBuku.remove(bukuToRemove);
            // Menyimpan data buku setelah penghapusan
            simpanDataBuku();
            System.out.println("Buku berhasil dihapus!");
        } else {
            // Menampilkan pesan jika buku dengan ISBN yang dimasukkan tidak ditemukan
            System.out.println("Buku tidak ditemukan.");
        }
    }

    public void pinjamBuku(Scanner scanner) {
        // Meminta pengguna untuk memasukkan ISBN buku yang ingin dipinjam
        System.out.print("Masukkan ISBN buku yang ingin dipinjam: ");
        String isbn = InputUtils.bacaString(scanner).trim(); // Membaca dan menghapus spasi di awal/akhir ISBN

        // Validasi ISBN tidak boleh kosong
        if (isbn.isEmpty()) {
            System.out.println("ISBN tidak boleh kosong.");
            return; // Menghentikan eksekusi metode jika ISBN kosong
        }

        Buku bukuPinjam = null; // Variabel untuk menyimpan buku yang akan dipinjam
        // Mencari buku berdasarkan ISBN yang dimasukkan
        for (Buku buku : daftarBuku) {
            if (buku.getIsbn().equals(isbn)) {
                // Memeriksa stok buku
                if (buku.getStok() > 0) {
                    bukuPinjam = buku; // Menyimpan buku jika stok masih ada
                    break;
                } else {
                    System.out.println("Stok buku habis.");
                    return; // Menghentikan eksekusi metode jika stok habis
                }
            }
        }

        // Jika buku ditemukan, lanjutkan proses peminjaman
        if (bukuPinjam != null) {
            // Meminta nama peminjam dan jumlah hari peminjaman
            System.out.print("Masukkan nama peminjam: ");
            String namaPeminjam = InputUtils.bacaString(scanner);
            System.out.print("Masukkan jumlah hari peminjaman: ");
            int jumlahHari = InputUtils.bacaInt(scanner);

            // Membuat catatan peminjaman baru
            CatatanPeminjaman catatan = new CatatanPeminjaman(namaPeminjam, bukuPinjam.getJudul(), bukuPinjam.getIsbn(), jumlahHari);
            daftarPeminjaman.add(catatan); // Menambahkan catatan peminjaman ke daftar
            bukuPinjam.setStok(bukuPinjam.getStok() - 1); // Mengurangi stok buku
            simpanDataPeminjaman(); // Menyimpan data peminjaman ke file
            simpanDataBuku(); // Menyimpan data buku yang terupdate ke file
            simpanLaporanBukuKeluar(); // Menyimpan laporan buku keluar ke file
            System.out.println("Buku berhasil dipinjam!");
        } else {
            System.out.println("Buku tidak ditemukan.");
        }
    }

 // Metode untuk menyimpan data peminjaman ke dalam file
    private void simpanDataPeminjaman() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePathPeminjaman))) {
            int lastId = 0; // Untuk menyimpan ID peminjaman terakhir
            int lastPaymentId = 0; // Untuk menyimpan ID pembayaran terakhir

            // Iterasi melalui daftar peminjaman
            for (CatatanPeminjaman catatan : daftarPeminjaman) {
                // Menentukan status pembayaran dan pengembalian
                String statusBayar = catatan.isSudahDibayar() ? "Sudah Bayar" : "Belum Bayar";
                String statusKembalikan = catatan.isSudahDikembalikan() ? "Sudah Dikembalikan" : "Belum Dikembalikan";

                // Menulis data peminjaman ke file dengan format tertentu
                writer.write(String.format("%d|%s|%s|%s|%d|%s|%s|%s%s%n",
                                           catatan.getIdPeminjaman(), catatan.getNamaPeminjam(),
                                           catatan.getJudulBuku(), catatan.getIsbnBuku(), catatan.getJumlahHari(),
                                           catatan.getBiayaTotal().toString(), statusBayar, statusKembalikan,
                                           catatan.getPaymentId() != null ? "|" + catatan.getPaymentId() : ""));
                // Memperbarui ID terakhir dan ID pembayaran terakhir
                lastId = Math.max(lastId, catatan.getIdPeminjaman());
                lastPaymentId = Math.max(lastPaymentId, catatan.getPaymentId() != null ? catatan.getPaymentId() : 0);
            }
            // Mengatur ID generator dengan ID terakhir dan counter ID pembayaran dengan ID terakhir
            CatatanPeminjaman.setIdGenerator(lastId);
            CatatanPeminjaman.setPaymentIdCounter(lastPaymentId);
        } catch (IOException e) {
            // Menangani kesalahan saat menulis ke file
            System.out.println("Gagal menyimpan data peminjaman : " + e.getMessage());
        }
    }

    // Metode untuk memuat data peminjaman dari file
    private void muatDataPeminjaman() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePathPeminjaman))) {
            String line;
            // Membaca setiap baris dari file
            while ((line = reader.readLine()) != null) {
                // Memisahkan baris berdasarkan delimiter '|'
                String[] parts = line.split("\\|");
                if (parts.length >= 8) { // Memastikan format data cukup untuk diolah
                    int idPeminjaman = Integer.parseInt(parts[0].trim());
                    String namaPeminjam = parts[1].trim();
                    String judulBuku = parts[2].trim();
                    String isbn = parts[3].trim();
                    int hari = Integer.parseInt(parts[4].trim());
                    BigDecimal biayaTotal = new BigDecimal(parts[5].trim());
                    boolean sudahDibayar = parts[6].trim().equals("Sudah Bayar");
                    boolean sudahDikembalikan = parts[7].trim().equals("Sudah Dikembalikan");
                    Integer paymentId = null;

                    // Mengonversi paymentId hanya jika ada
                    if (parts.length == 9) {
                        paymentId = Integer.parseInt(parts[8].trim());
                    }

                    // Membuat objek CatatanPeminjaman dan menambahkannya ke daftar
                    CatatanPeminjaman catatan = new CatatanPeminjaman(idPeminjaman, namaPeminjam, judulBuku, isbn, hari, biayaTotal, sudahDibayar, sudahDikembalikan);
                    catatan.setPaymentId(paymentId);
                    daftarPeminjaman.add(catatan);
                }
            }

            // Mengatur ID generator dan counter ID pembayaran dengan ID terakhir yang ditemukan
            if (!daftarPeminjaman.isEmpty()) {
                int lastId = daftarPeminjaman.stream()
                                             .mapToInt(CatatanPeminjaman::getIdPeminjaman)
                                             .max()
                                             .orElse(0);
                int lastPaymentId = daftarPeminjaman.stream()
                                                    .mapToInt(c -> c.getPaymentId() != null ? c.getPaymentId() : 0)
                                                    .max()
                                                    .orElse(0);
                CatatanPeminjaman.setIdGenerator(lastId);
                CatatanPeminjaman.setPaymentIdCounter(lastPaymentId);
            }
        } catch (IOException e) {
            // Menangani kesalahan saat membaca dari file
            System.out.println("Error memuat data peminjaman : " + e.getMessage());
        } catch (NumberFormatException e) {
            // Menangani kesalahan format angka
            System.out.println("Error dalam format angka : " + e.getMessage());
        }
    }

    // Metode untuk menampilkan catatan peminjaman
    public void lihatCatatanPeminjaman() {
        if (daftarPeminjaman.isEmpty()) {
            // Menampilkan pesan jika tidak ada catatan peminjaman
            System.out.println("Tidak ada catatan peminjaman yang tersedia.");
        } else {
            // Mendefinisikan panjang kolom untuk format tampilan
            final int ID_PANJANG = 13;
            final int NAMA_PANJANG = 30;
            final int JUDUL_BUKU_PANJANG = 45;
            final int ISBN_PANJANG = 15;
            final int HARI_PANJANG = 5;
            final int BIAYA_TOTAL_PANJANG = 12;
            final int STATUS_BAYAR_PANJANG = 15;
            final int STATUS_KEMBALIKAN_PANJANG = 18;

            // Menampilkan header tabel catatan peminjaman
            System.out.println("\n================================================================================= Catatan Peminjaman ==========================================================================");
            System.out.println("===============================================================================================================================================================================");
            String headerFormat = String.format("%%-%ds | %%-%ds | %%-%ds | %%-%ds | %%-%ds | %%-%ds | %%-%ds | %%-%ds|%n",
                                                ID_PANJANG, NAMA_PANJANG, JUDUL_BUKU_PANJANG, ISBN_PANJANG, HARI_PANJANG, 
                                                BIAYA_TOTAL_PANJANG, STATUS_BAYAR_PANJANG, STATUS_KEMBALIKAN_PANJANG);

            String rowFormat = headerFormat;

            // Menampilkan baris header tabel
            System.out.printf(headerFormat, "ID Peminjaman", "Nama Peminjam", "Judul Buku", "ISBN", "Hari", 
                              "Biaya Total", "Status Bayar", "Status Kembalikan");
            // Menampilkan garis pemisah tabel
            System.out.println("=".repeat(ID_PANJANG + NAMA_PANJANG + JUDUL_BUKU_PANJANG + ISBN_PANJANG + HARI_PANJANG + 
                              BIAYA_TOTAL_PANJANG + STATUS_BAYAR_PANJANG + STATUS_KEMBALIKAN_PANJANG + 22));

            // Menampilkan setiap catatan peminjaman dalam format tabel
            for (CatatanPeminjaman catatan : daftarPeminjaman) {
                System.out.printf(rowFormat, 
                                  catatan.getIdPeminjaman(), 
                                  catatan.getNamaPeminjam(), 
                                  catatan.getJudulBuku(), 
                                  catatan.getIsbnBuku(), 
                                  catatan.getJumlahHari(), 
                                  catatan.getBiayaTotal().toString(), 
                                  catatan.isSudahDibayar() ? "Sudah Bayar" : "Belum Bayar", 
                                  catatan.isSudahDikembalikan() ? "Sudah Dikembalikan" : "Belum Dikembalikan");
            }
        }
        // Menampilkan garis akhir tabel
        System.out.println("===============================================================================================================================================================================");
    }

 // Metode untuk melakukan pembayaran
    public void lakukanPembayaran(Scanner scanner) {
        System.out.println("\n===== Lakukan Pembayaran =====");
        System.out.print("Masukkan ID peminjaman untuk melakukan pembayaran: ");
        
        // Membaca ID peminjaman dari input pengguna
        int idPeminjaman = InputUtils.bacaInt(scanner);

        CatatanPeminjaman catatan = null;
        // Mencari catatan peminjaman yang sesuai dengan ID yang dimasukkan
        for (CatatanPeminjaman c : daftarPeminjaman) {
            if (c.getIdPeminjaman() == idPeminjaman) {
                catatan = c;
                break;
            }
        }

        if (catatan != null) {
            // Jika catatan ditemukan dan sudah dibayar
            if (catatan.isSudahDibayar()) {
                System.out.println("Catatan peminjaman ini sudah dibayar.");
            } else {
                // Menampilkan biaya total yang harus dibayar
                System.out.printf("Biaya total yang harus dibayar: %s%n", catatan.getBiayaTotal());
                System.out.print("Masukkan jumlah pembayaran: ");
                
                // Membaca jumlah pembayaran dari input pengguna
                BigDecimal pembayaran = InputUtils.bacaBigDecimal(scanner);

                // Memeriksa apakah jumlah pembayaran mencukupi
                if (pembayaran.compareTo(catatan.getBiayaTotal()) >= 0) {
                    // Menandai catatan sebagai sudah dibayar dan mencetak struk pembayaran
                    catatan.setSudahDibayar(true);
                    cetakStruk(catatan, pembayaran);
                    // Menyimpan data peminjaman dan laporan pembayaran
                    simpanDataPeminjaman();
                    simpanLaporanPembayaran();
                } else {
                    // Jika jumlah pembayaran tidak mencukupi
                    System.out.println("Pembayaran tidak mencukupi. Silakan coba lagi.");
                }
            }
        } else {
            // Jika catatan peminjaman tidak ditemukan
            System.out.println("Catatan peminjaman tidak ditemukan.");
        }
    }

    // Metode untuk menyimpan laporan pembayaran ke file
    private void simpanLaporanPembayaran() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePathLaporan))) {
            // Menulis catatan peminjaman yang sudah dibayar ke file
            for (CatatanPeminjaman catatan : daftarPeminjaman) {
                if (catatan.isSudahDibayar()) {
                    writer.write(String.format("%d|%d|%s|%d|%s%n",
                                               catatan.getIdPeminjaman(), 
                                               catatan.getIdPeminjaman(), 
                                               catatan.getIsbnBuku(),
                                               catatan.getJumlahHari(),
                                               catatan.getBiayaTotal().toString()));
                }
            }
            System.out.println("Laporan pembayaran berhasil disimpan.");
        } catch (IOException e) {
            // Menangani kemungkinan kesalahan saat menyimpan laporan
            System.out.println("Gagal menyimpan leporan mebayaran : " + e.getMessage());
        }
    }

    // Metode untuk mencetak struk pembayaran ke konsol
    private void cetakStruk(CatatanPeminjaman catatan, BigDecimal pembayaran) {
        // Menyiapkan format mata uang Indonesia
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        Currency rupiah = Currency.getInstance("IDR");
        currencyFormat.setCurrency(rupiah);

        // Menyiapkan separator untuk struk
        String separator = "=".repeat(60);

        // Mencetak struk pembayaran
        System.out.println("\n===================== STRUK PEMBAYARAN =====================");
        System.out.println(separator);
        System.out.printf("%-20s: %d%n", "ID Pembayaran", catatan.getIdPeminjaman());
        System.out.printf("%-20s: %s%n", "Nama Peminjam", catatan.getNamaPeminjam());
        System.out.printf("%-20s: %s%n", "Judul Buku", catatan.getJudulBuku());
        System.out.printf("%-20s: %s%n", "ISBN", catatan.getIsbnBuku());
        System.out.printf("%-20s: %s%n", "Biaya Total", currencyFormat.format(catatan.getBiayaTotal()));
        System.out.printf("%-20s: %s%n", "Jumlah Pembayaran", currencyFormat.format(pembayaran));
        System.out.printf("%-20s: %s%n", "Kembalian", currencyFormat.format(pembayaran.subtract(catatan.getBiayaTotal())));
        System.out.println(separator);
    }

    // Metode untuk melihat laporan pembayaran dari file
    public void lihatLaporanPembayaran() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePathLaporan))) {
            String line;
            // Membaca baris pertama untuk memeriksa apakah ada laporan pembayaran
            if ((line = reader.readLine()) == null) {
                System.out.println("Tidak ada pembayaran yang tercatat.");
            } else {
                // Mendefinisikan panjang kolom untuk header dan baris
                final int ID_PEMBAYARAN_PANJANG = 15;
                final int ID_PEMINJAMAN_PANJANG = 15;
                final int ISBN_PANJANG = 15;
                final int HARI_PANJANG = 5;
                final int BIAYA_TOTAL_PANJANG = 15;

                // Format header tabel
                System.out.println("\n============================= Catatan Pembayaran =============================");
                System.out.println("==============================================================================");
                String headerFormat = String.format("%%-%ds | %%-%ds | %%-%ds | %%-%ds | %%-%ds|%n", 
                                                    ID_PEMBAYARAN_PANJANG, ID_PEMINJAMAN_PANJANG, ISBN_PANJANG, HARI_PANJANG, BIAYA_TOTAL_PANJANG);
                String rowFormat = headerFormat;

                // Menampilkan header tabel
                System.out.printf(headerFormat, "ID Pembayaran", "ID Peminjaman", "ISBN", "Hari", "Biaya Total");
                System.out.println("=".repeat(ID_PEMBAYARAN_PANJANG + ID_PEMINJAMAN_PANJANG + ISBN_PANJANG + HARI_PANJANG + BIAYA_TOTAL_PANJANG + 13));

                // Menampilkan setiap baris laporan pembayaran
                do {
                    String[] parts = line.split("\\|");
                    if (parts.length == 5) {
                        System.out.printf(rowFormat, parts[0], parts[1], parts[2], parts[3], parts[4]);
                    }
                } while ((line = reader.readLine()) != null);
                System.out.println("=".repeat(ID_PEMBAYARAN_PANJANG + ID_PEMINJAMAN_PANJANG + ISBN_PANJANG + HARI_PANJANG + BIAYA_TOTAL_PANJANG + 13));
            }
        } catch (IOException e) {
            // Menangani kemungkinan kesalahan saat membaca laporan
            System.out.println("Gagal memuat laporan pembayaran : " + e.getMessage());
        }
    }
    
    public void kembalikanBuku(Scanner scanner) {
        // Meminta pengguna untuk memasukkan ID peminjaman buku yang akan dikembalikan
        System.out.print("Masukkan ID peminjaman untuk mengembalikan buku: ");
        int idPeminjaman = InputUtils.bacaInt(scanner); // Membaca ID peminjaman dari input pengguna

        CatatanPeminjaman catatan = null;
        // Mencari catatan peminjaman yang sesuai dengan ID yang dimasukkan
        for (CatatanPeminjaman c : daftarPeminjaman) {
            if (c.getIdPeminjaman() == idPeminjaman) {
                catatan = c;
                break; // Jika ditemukan, simpan referensi catatan dan keluar dari loop
            }
        }

        // Memeriksa apakah catatan peminjaman ditemukan
        if (catatan != null) {
            // Memeriksa apakah buku sudah dikembalikan
            if (catatan.isSudahDikembalikan()) {
                System.out.println("Buku sudah dikembalikan."); // Menampilkan pesan jika buku sudah dikembalikan
            } else {
                // Mengubah status catatan peminjaman menjadi sudah dikembalikan
                catatan.setSudahDikembalikan(true);

                Buku bukuKembalikan = null;
                // Mencari buku yang sesuai dengan ISBN dari catatan peminjaman
                for (Buku buku : daftarBuku) {
                    if (buku.getIsbn().equals(catatan.getIsbnBuku())) {
                        bukuKembalikan = buku;
                        break; // Jika ditemukan, simpan referensi buku dan keluar dari loop
                    }
                }

                // Memeriksa apakah buku ditemukan
                if (bukuKembalikan != null) {
                    // Mengupdate stok buku dengan menambah satu
                    bukuKembalikan.setStok(bukuKembalikan.getStok() + 1);
                    
                    // Menyimpan data buku, peminjaman, dan laporan buku masuk ke file
                    simpanDataBuku();
                    simpanDataPeminjaman();
                    simpanLaporanBukuMasuk();
                    
                    // Menampilkan pesan sukses
                    System.out.println("Buku berhasil dikembalikan!");
                } else {
                    // Jika buku tidak ditemukan, menampilkan pesan error
                    System.out.println("Buku tidak ditemukan.");
                }
            }
        } else {
            // Jika catatan peminjaman tidak ditemukan, menampilkan pesan error
            System.out.println("Catatan peminjaman tidak ditemukan.");
        }
    }

 // Metode untuk menyimpan laporan buku masuk ke dalam file
    public void simpanLaporanBukuMasuk() {
        // Menggunakan try-with-resources untuk otomatis menutup BufferedWriter
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("laporan_buku_masuk.txt"))) {
            // Iterasi melalui daftar peminjaman
            for (CatatanPeminjaman catatan : daftarPeminjaman) {
                // Menulis catatan ke file jika buku sudah dikembalikan
                if (catatan.isSudahDikembalikan()) {
                    writer.write(String.format("%d|%s|%s%n",
                                               catatan.getIdPeminjaman(),
                                               catatan.getJudulBuku(),
                                               catatan.getIsbnBuku()));
                }
            }
            System.out.println("Laporan buku masuk berhasil disimpan.");
        } catch (IOException e) {
            // Menangani pengecualian jika terjadi kesalahan saat menulis ke file
            System.out.println("Gagal menyimpan laporan buku masuk : " + e.getMessage());
        }
    }

    // Metode untuk menampilkan laporan buku masuk dari file
    public void lihatLaporanBukuMasuk() {
        // Menggunakan try-with-resources untuk otomatis menutup BufferedReader
        try (BufferedReader reader = new BufferedReader(new FileReader("laporan_buku_masuk.txt"))) {
            String line;
            // Membaca baris pertama untuk memeriksa apakah file kosong
            if ((line = reader.readLine()) == null) {
                System.out.println("Tidak ada laporan buku masuk.");
            } else {
                // Mendefinisikan panjang kolom untuk header dan baris
                final int ID_PANJANG = 13;
                final int JUDUL_BUKU_PANJANG = 45;
                final int ISBN_PANJANG = 15;

                // Format header tabel
                System.out.println("\n============================= Laporan Buku Masuk ===============================");
                System.out.println("================================================================================");
                String headerFormat = String.format("%%-%ds | %%-%ds | %%-%ds|%n", 
                                                    ID_PANJANG, JUDUL_BUKU_PANJANG, ISBN_PANJANG);
                String rowFormat = headerFormat;

                // Menampilkan header tabel
                System.out.printf(headerFormat, "ID Peminjaman", "Judul Buku", "ISBN");
                System.out.println("=".repeat(ID_PANJANG + JUDUL_BUKU_PANJANG + ISBN_PANJANG + 7));

                // Menampilkan setiap baris laporan buku masuk
                do {
                    String[] parts = line.split("\\|");
                    if (parts.length == 3) {
                        System.out.printf(rowFormat, parts[0], parts[1], parts[2]);
                    }
                } while ((line = reader.readLine()) != null);
                System.out.println("=".repeat(ID_PANJANG + JUDUL_BUKU_PANJANG + ISBN_PANJANG + 7));
            }
        } catch (IOException e) {
            // Menangani pengecualian jika terjadi kesalahan saat membaca dari file
            System.out.println("Gagal memuat laporan buku masuk : " + e.getMessage());
        }
    }

    // Metode untuk menyimpan laporan buku keluar ke dalam file
    public void simpanLaporanBukuKeluar() {
        // Menggunakan try-with-resources untuk otomatis menutup BufferedWriter
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("laporan_buku_keluar.txt"))) {
            // Iterasi melalui daftar peminjaman
            for (CatatanPeminjaman catatan : daftarPeminjaman) {
                // Menulis catatan ke file jika buku belum dikembalikan
                if (!catatan.isSudahDikembalikan()) {
                    writer.write(String.format("%d|%s|%s%n",
                                               catatan.getIdPeminjaman(),
                                               catatan.getJudulBuku(),
                                               catatan.getIsbnBuku()));
                }
            }
            System.out.println("Laporan buku keluar berhasil disimpan.");
        } catch (IOException e) {
            // Menangani pengecualian jika terjadi kesalahan saat menulis ke file
            System.out.println("Gagal menyimpan laporan buku keluar : " + e.getMessage());
        }
    }

    // Metode untuk menampilkan laporan buku keluar dari file
    public void lihatLaporanBukuKeluar() {
        // Menggunakan try-with-resources untuk otomatis menutup BufferedReader
        try (BufferedReader reader = new BufferedReader(new FileReader("laporan_buku_keluar.txt"))) {
            String line;
            // Membaca baris pertama untuk memeriksa apakah file kosong
            if ((line = reader.readLine()) == null) {
                System.out.println("Tidak ada laporan buku keluar.");
            } else {
                // Mendefinisikan panjang kolom untuk header dan baris
                final int ID_PANJANG = 13;
                final int JUDUL_BUKU_PANJANG = 45;
                final int ISBN_PANJANG = 15;

                // Format header tabel
                System.out.println("\n============================= Laporan Buku Keluar ==============================");
                System.out.println("================================================================================");
                String headerFormat = String.format("%%-%ds | %%-%ds | %%-%ds|%n", 
                                                    ID_PANJANG, JUDUL_BUKU_PANJANG, ISBN_PANJANG);
                String rowFormat = headerFormat;

                // Menampilkan header tabel
                System.out.printf(headerFormat, "ID Peminjaman", "Judul Buku", "ISBN");
                System.out.println("=".repeat(ID_PANJANG + JUDUL_BUKU_PANJANG + ISBN_PANJANG + 7));

                // Menampilkan setiap baris laporan buku keluar
                do {
                    String[] parts = line.split("\\|");
                    if (parts.length == 3) {
                        System.out.printf(rowFormat, parts[0], parts[1], parts[2]);
                    }
                } while ((line = reader.readLine()) != null);
                System.out.println("=".repeat(ID_PANJANG + JUDUL_BUKU_PANJANG + ISBN_PANJANG + 7));
            }
        } catch (IOException e) {
            // Menangani pengecualian jika terjadi kesalahan saat membaca dari file
            System.out.println("Gagal memuat laporan buku keluar : " + e.getMessage());
        }
    }
}
