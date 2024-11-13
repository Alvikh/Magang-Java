package com.perpustakaan.utils;

import java.math.BigDecimal;
import java.util.Scanner;

// Kelas utilitas untuk membaca input dari pengguna
public class InputUtils {
    // Metode untuk membaca input string dari pengguna
    public static String bacaString(Scanner scanner) {
        // Menggunakan nextLine() untuk membaca seluruh baris input
        return scanner.nextLine();
    }

    // Metode untuk membaca input integer dari pengguna
    public static int bacaInt(Scanner scanner) {
        // Loop sampai pengguna memasukkan nilai yang valid
        while (!scanner.hasNextInt()) {
            // Menampilkan pesan error jika input tidak valid
            System.out.println("Input tidak valid. Harap masukkan angka.");
            scanner.next(); // Mengonsumsi input yang tidak valid
        }
        int result = scanner.nextInt();
        scanner.nextLine(); // Mengonsumsi newline yang tersisa
        return result;
    }

    // Metode untuk membaca input BigDecimal dari pengguna
    public static BigDecimal bacaBigDecimal(Scanner scanner) {
        // Loop sampai pengguna memasukkan nilai yang valid
        while (!scanner.hasNextBigDecimal()) {
            // Menampilkan pesan error jika input tidak valid
            System.out.println("Input tidak valid. Harap masukkan angka desimal.");
            scanner.next(); // Mengonsumsi input yang tidak valid
        }
        BigDecimal result = scanner.nextBigDecimal();
        scanner.nextLine(); // Mengonsumsi newline yang tersisa
        return result;
    }
}
