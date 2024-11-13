package com.perpustakaan.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

// Kelas CatatanPeminjaman digunakan untuk merepresentasikan informasi tentang peminjaman buku
public class CatatanPeminjaman {
    private static int idGenerator = 0; // Variabel statis untuk menghasilkan ID peminjaman unik
    private static int paymentIdCounter = 1; // Variabel statis untuk menghitung ID pembayaran

    private int idPeminjaman; // ID unik untuk peminjaman
    private String namaPeminjam; // Nama peminjam buku
    private String judulBuku; // Judul buku yang dipinjam
    private String isbnBuku; // ISBN buku yang dipinjam
    private int jumlahHari; // Jumlah hari peminjaman
    private BigDecimal biayaTotal; // Biaya total peminjaman
    private boolean sudahDibayar; // Status pembayaran
    private boolean sudahDikembalikan; // Status pengembalian
    private Integer paymentId; // ID pembayaran, null jika belum ada pembayaran

    // Konstruktor untuk membuat catatan peminjaman baru
    public CatatanPeminjaman(String namaPeminjam, String judulBuku, String isbnBuku, int jumlahHari) {
        this.idPeminjaman = ++idGenerator; // Menghasilkan ID peminjaman unik
        this.namaPeminjam = namaPeminjam;
        this.judulBuku = judulBuku;
        this.isbnBuku = isbnBuku;
        this.jumlahHari = jumlahHari;
        this.biayaTotal = BigDecimal.valueOf(jumlahHari * 7000).setScale(2, RoundingMode.HALF_UP); // Menghitung biaya total berdasarkan jumlah hari
        this.sudahDibayar = false; // Awalnya belum dibayar
        this.sudahDikembalikan = false; // Awalnya belum dikembalikan
        this.paymentId = null; // ID pembayaran belum ada
    }

    // Konstruktor untuk membuat catatan peminjaman dengan semua detail
    public CatatanPeminjaman(int idPeminjaman, String namaPeminjam, String judulBuku, String isbnBuku, int jumlahHari, BigDecimal biayaTotal, boolean sudahDibayar, boolean sudahDikembalikan) {
        this.idPeminjaman = idPeminjaman;
        this.namaPeminjam = namaPeminjam;
        this.judulBuku = judulBuku;
        this.isbnBuku = isbnBuku;
        this.jumlahHari = jumlahHari;
        this.biayaTotal = biayaTotal;
        this.sudahDibayar = sudahDibayar;
        this.sudahDikembalikan = sudahDikembalikan;
        this.paymentId = null; // ID pembayaran belum ada
    }

    // Getters and Setters untuk setiap field

    public int getIdPeminjaman() {
        return idPeminjaman;
    }

    public String getNamaPeminjam() {
        return namaPeminjam;
    }

    public String getJudulBuku() {
        return judulBuku;
    }

    public String getIsbnBuku() {
        return isbnBuku;
    }

    public int getJumlahHari() {
        return jumlahHari;
    }

    public BigDecimal getBiayaTotal() {
        return biayaTotal;
    }

    public boolean isSudahDibayar() {
        return sudahDibayar;
    }

    public void setSudahDibayar(boolean sudahDibayar) {
        this.sudahDibayar = sudahDibayar;
    }

    public boolean isSudahDikembalikan() {
        return sudahDikembalikan;
    }

    public void setSudahDikembalikan(boolean sudahDikembalikan) {
        this.sudahDikembalikan = sudahDikembalikan;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    // Mengembalikan representasi string dari objek CatatanPeminjaman
    @Override
    public String toString() {
        return String.format("%-10d | %-20s | %-30s | %-15s | %-5d | %-10s | %-6s | %-12s | %-10s",
                idPeminjaman, namaPeminjam, judulBuku, isbnBuku, jumlahHari, biayaTotal.toString(),
                (sudahDibayar ? "Dibayar" : "Belum Dibayar"), (sudahDikembalikan ? "Dikembalikan" : "Belum Dikembalikan"),
                paymentId != null ? paymentId.toString() : "N/A");
    }

    // Metode statis untuk mengatur dan mendapatkan nilai generator ID
    public static void setIdGenerator(int id) {
        idGenerator = id;
    }

    public static int getIdGenerator() {
        return idGenerator;
    }

    // Metode statis untuk mengatur dan mendapatkan nilai counter ID pembayaran
    public static void setPaymentIdCounter(int counter) {
        paymentIdCounter = counter;
    }

    public static int getPaymentIdCounter() {
        return paymentIdCounter;
    }
}

