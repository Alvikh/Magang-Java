package com.perpustakaan;

import com.perpustakaan.service.LayananPerpustakaan;
import com.perpustakaan.utils.InputUtils;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LayananPerpustakaan layananPerpustakaan = new LayananPerpustakaan(); // Membuat instance layanan perpustakaan
        Scanner scanner = new Scanner(System.in); // Membuat scanner untuk input pengguna
        boolean keluar = false; // Menentukan kondisi loop untuk keluar dari program

        while (!keluar) { // Loop utama untuk menampilkan menu dan menangani pilihan pengguna
            System.out.println("\n=======================================");
            System.out.println("==  SELAMAT DATANG DI PERPUSTAKAAN!  ==");
            System.out.println("=======================================");
            System.out.println("=== Sistem Manajemen Perpustakaan ====+");
            System.out.println("1.  Menu Buku                         |");
            System.out.println("2.  Peminjaman                        |");
            System.out.println("3.  Keterangan Peminjaman             |");
            System.out.println("4.  Pembayaran                        |");
            System.out.println("5.  Catatan Pembayaran                |");
            System.out.println("6.  Laporan Buku                      |");
            System.out.println("7.  Pengembalian                      |");
            System.out.println("8.  Keluar                            |");
            System.out.println("======================================+");
            System.out.print("Pilih opsi (1-8): ");

            int pilihan = InputUtils.bacaInt(scanner); // Membaca input pilihan pengguna

            switch (pilihan) {
                case 1:
                    lihatBukuMenu(scanner, layananPerpustakaan); // Panggil menu buku
                    break;
                case 2:
                    layananPerpustakaan.pinjamBuku(scanner); // Panggil fungsi peminjaman buku
                    break;
                case 3:
                    layananPerpustakaan.lihatCatatanPeminjaman(); // Panggil fungsi untuk melihat catatan peminjaman
                    break;
                case 4:
                    layananPerpustakaan.lakukanPembayaran(scanner); // Panggil fungsi untuk melakukan pembayaran
                    break;
                case 5:
                    layananPerpustakaan.lihatLaporanPembayaran(); // Panggil fungsi untuk melihat laporan pembayaran
                    break;
                case 6:
                    lihatLaporanBuku(scanner, layananPerpustakaan); // Panggil menu laporan buku
                    break;
                case 7:
                    layananPerpustakaan.kembalikanBuku(scanner); // Panggil fungsi untuk mengembalikan buku
                    break;
                case 8:
                    keluar = true; // Keluar dari loop utama
                    System.out.println("\nTerima kasih telah menggunakan sistem perpustakaan.");
                    break;
                default:
                    System.out.println("Opsi tidak valid. Silakan coba lagi."); // Pesan untuk input tidak valid
            }
        }

        scanner.close(); // Menutup scanner
    }

    // Fungsi untuk menampilkan menu buku dan menangani pilihan pengguna
    private static void lihatBukuMenu(Scanner scanner, LayananPerpustakaan layananPerpustakaan) {
        boolean kembaliKeMenuUtama = false;

        while (!kembaliKeMenuUtama) { // Loop untuk menu buku
            System.out.println("\n=========== Menu Buku ===========+");
            System.out.println("1.  Daftar Buku                  |");
            System.out.println("2.  Cari Buku Berdasarkan Judul  |");
            System.out.println("3.  Tambah Buku                  |");
            System.out.println("4.  Perbarui Data Buku           |");
            System.out.println("5.  Hapus Buku                   |");
            System.out.println("6.  Kembali ke Menu Utama        |");
            System.out.println("=================================+");
            System.out.print("Pilih opsi (1-6): ");

            int pilihanSubMenu = InputUtils.bacaInt(scanner); // Membaca input pilihan submenu

            switch (pilihanSubMenu) {
                case 1:
                    layananPerpustakaan.lihatBuku(); // Panggil fungsi untuk melihat daftar buku
                    break;
                case 2:
                    layananPerpustakaan.cariBukuBerdasarkanJudul(scanner); // Panggil fungsi untuk mencari buku berdasarkan judul
                    break;
                case 3:
                    layananPerpustakaan.tambahBuku(scanner); // Panggil fungsi untuk menambah buku
                    break;
                case 4:
                    layananPerpustakaan.perbaruiBuku(scanner); // Panggil fungsi untuk memperbarui data buku
                    break;
                case 5:
                    layananPerpustakaan.hapusBuku(scanner); // Panggil fungsi untuk menghapus buku
                    break;
                case 6:
                    kembaliKeMenuUtama = true; // Kembali ke menu utama
                    break;
                default:
                    System.out.println("Opsi tidak valid. Silakan coba lagi."); // Pesan untuk input tidak valid
            }
        }
    }

    // Fungsi untuk menampilkan menu laporan buku dan menangani pilihan pengguna
    private static void lihatLaporanBuku(Scanner scanner, LayananPerpustakaan layananPerpustakaan) {
        boolean kembaliKeMenuUtama = false;

        while (!kembaliKeMenuUtama) { // Loop untuk menu laporan buku
            System.out.println("\n=========== Laporan =============+");
            System.out.println("1.  Laporan Buku Masuk           |");
            System.out.println("2.  Laporan Buku Keluar          |");
            System.out.println("3.  Kembali ke Menu Utama        |");
            System.out.println("=================================+");
            System.out.print("Pilih opsi (1-3): ");

            int pilihanSubMenu = InputUtils.bacaInt(scanner); // Membaca input pilihan submenu

            switch (pilihanSubMenu) {
                case 1:
                    layananPerpustakaan.lihatLaporanBukuMasuk(); // Panggil fungsi untuk melihat laporan buku masuk
                    break;
                case 2:
                    layananPerpustakaan.lihatLaporanBukuKeluar(); // Panggil fungsi untuk melihat laporan buku keluar
                    break;
                case 3:
                    kembaliKeMenuUtama = true; // Kembali ke menu utama
                    break;
                default:
                    System.out.println("Opsi tidak valid. Silakan coba lagi."); // Pesan untuk input tidak valid
            }
        }
    }
}
