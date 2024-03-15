import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

class Mahasiswa {
    static String universitas = "Universitas Muhammadiyah Malang\n";

    String nama;
    BigInteger nim;
    String jurusan;

    Mahasiswa(String nama, BigInteger nim, String jurusan) {
        this.nama = nama;
        this.nim = nim;
        this.jurusan = jurusan;
    }

    String tampilDataMahasiswa() {
        return "Nama: " + nama + "\nNIM: " + nim + "\nJurusan: " + jurusan;
    }

    static String tampilUniversitas() {
        return universitas;
    }
}

public class Codelab {
    static ArrayList<Mahasiswa> daftarMahasiswa = new ArrayList<>();

    public static void main(String[] args) {
        menu();
    }

    static void menu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("====== Menu ======");
            System.out.println("1. Tambah Data Mahasiswa");
            System.out.println("2. Tampilkan Data Mahasiswa");
            System.out.println("3. Keluar");
            System.out.print("Pilih menu (1/2/3): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    tambahDataMahasiswa();
                    break;
                case 2:
                    tampilkanDataMahasiswa();
                    break;
                case 3:
                    System.out.println("Program selesai. Sampai jumpa!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }

    static void tambahDataMahasiswa() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Masukkan Nama Mahasiswa: ");
        String nama = scanner.nextLine();

        BigInteger nim = validasiNIM();

        System.out.print("Masukkan Jurusan Mahasiswa: ");
        String jurusan = scanner.nextLine();

        Mahasiswa mahasiswa = new Mahasiswa(nama, nim, jurusan);
        daftarMahasiswa.add(mahasiswa);

        System.out.println("Data mahasiswa berhasil ditambahkan.");
    }

    static void tampilkanDataMahasiswa() {
        System.out.println("\n======= Data Mahasiswa =======");
        System.out.println(Mahasiswa.tampilUniversitas());

        for (Mahasiswa mahasiswa : daftarMahasiswa) {
            System.out.println(mahasiswa.tampilDataMahasiswa());
            System.out.println();
        }
    }

    static BigInteger validasiNIM() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Masukkan NIM Mahasiswa (panjang 15 angka): ");
            String nimInput = scanner.nextLine();
            try {
                BigInteger nim = new BigInteger(nimInput);
                if (nimInput.length() == 15) {
                    return nim;
                } else {
                    System.out.println("Panjang NIM harus 15 angka. Silakan coba lagi.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka. Silakan coba lagi.");
            }
        }
    }
}
