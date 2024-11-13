package com.perpustakaan.model;

// Class Buku digunakan untuk merepresentasikan informasi tentang sebuah buku
public class Buku {
    private String judul;   // Menyimpan judul buku
    private String penulis; // Menyimpan nama penulis buku
    private String isbn;    // Menyimpan nomor ISBN buku
    private int stok;       // Menyimpan jumlah stok buku

    // Konstruktor untuk menginisialisasi objek Buku dengan judul, penulis, isbn, dan stok
    public Buku(String judul, String penulis, String isbn, int stok) {
        this.judul = judul;
        this.penulis = penulis;
        this.isbn = isbn;
        this.stok = stok;
    }

    // Getter untuk setiap field

    // Mengembalikan judul buku
    public String getJudul() {
        return judul;
    }

    // Setter untuk mengubah judul buku
    public void setJudul(String judul) {
        this.judul = judul;
    }

    // Mengembalikan nama penulis buku
    public String getPenulis() {
        return penulis;
    }

    // Setter untuk mengubah nama penulis buku
    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    // Mengembalikan nomor ISBN buku
    public String getIsbn() {
        return isbn;
    }

    // Mengembalikan jumlah stok buku
    public int getStok() {
        return stok;
    }

    // Setter untuk mengubah jumlah stok buku
    public void setStok(int stok) {
        this.stok = stok;
    }

    // Metode untuk mengembalikan representasi string dari objek Buku
    @Override
    public String toString() {
        // Menggunakan String.format untuk mengatur tampilan string
        return String.format("%-30s | %-30s | %-15s | %-5d", judul, penulis, isbn, stok);
    }
}
