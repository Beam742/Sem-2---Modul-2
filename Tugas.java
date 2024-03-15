import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Main {
    static Book[] bookList = {
            new Book(1, "Laskar Pelangi", "Andrea Hirata", "Fiction"),
            new Book(2, "Ayat-Ayat Cinta", "Habiburrahman", "Romance"),
            new Book(3, "Tentang Kamu", "Tere Liye", "Romance"),
            new Book(4, "Perahu Kertas", "Dee Lestari", "Fiction"),
            new Book(5, "Negeri 5 Menara", "Ahmad Fuadi", "Fiction")

    };

    static ArrayList<User> userStudents = new ArrayList<>();
    static User currentUser = null;

    public static void main(String[] args) {
        menu();
    }

    static void menu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("=== Library System ===");
            System.out.println("1. Login as Student");
            System.out.println("2. Login as Admin");
            System.out.println("3. Exit");
            System.out.print("Pilih menu (1/2/3): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    Student student = new Student();
                    if (student.login()) {
                        currentUser = student;
                        student.menuStudent();
                    }
                    break;
                case 2:
                    Admin admin = new Admin();
                    if (admin.login()) {
                        admin.menuAdmin();
                    }
                    break;
                case 3:
                    System.out.println("Program selesai. Sampai jumpa lagi!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }
}

class Book {
    int id;
    String title;
    String author;
    String category;

    public Book(int id, String title, String author, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
    }
}

class User {
    String name;
    String nim;

    public User(String name, String nim) {
        this.name = name;
        this.nim = nim;
    }
}

class Student extends User {
    static HashMap<String, ArrayList<Book>> borrowedBooksMap = new HashMap<>();

    public Student() {
        super("", "");
    }

    boolean login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Masukkan NIM Anda: ");
        nim = scanner.nextLine();
        User user = getUserByNim(nim);

        if (user != null) {
            System.out.println("Login berhasil sebagai " + user.name + ".");
            return true;
        } else {
            System.out.println("NIM tidak valid. Silakan coba lagi.");
            return false;
        }
    }

    User getUserByNim(String nim) {
        for (User user : Main.userStudents) {
            if (user.nim.equals(nim)) {
                return user;
            }
        }
        return null;
    }

    void menuStudent() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("=== Student Menu ===");
            System.out.println("1. Buku terpinjam");
            System.out.println("2. Pinjam buku");
            System.out.println("3. Logout");
            System.out.print("Pilih menu (1/2/3): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    displayBorrowedBooks();
                    break;
                case 2:
                    borrowBook();
                    break;
                case 3:
                    logout();
                    System.out.println("Berhasil logout dari user student.");
                    return;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }

    void borrowBook() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("==================================================================");
            System.out.println("=========================  Daftar  Buku  =========================");
            displayBookList();

            System.out.print("Input id buku yang ingin dipinjam (input 99 untuk kembali): ");
            int bookId = scanner.nextInt();
            scanner.nextLine();

            if (bookId == 99) {
                return;
            }

            Book bookToBorrow = findBookById(bookId);
            if (bookToBorrow != null && !isBookAlreadyBorrowed(bookToBorrow)) {
                borrowedBooksMap.computeIfAbsent(nim, k -> new ArrayList<>()).add(bookToBorrow);
                System.out.println("Buku " + bookToBorrow.title + " dipinjam.");
                return;
            } else {
                System.out.println("Buku tidak tersedia atau sudah dipinjam sebelumnya. Silakan coba lagi.");
            }
        }
    }

    void displayBookList() {
        System.out.println("==================================================================");
        System.out.printf("|| %-3s || %-20s || %-15s || %-10s ||%n", "Id", "Judul Buku", "Penulis", "Kategori");
        System.out.println("==================================================================");
        for (Book book : Main.bookList) {
            System.out.printf("|| %-3d || %-20s || %-15s || %-10s ||%n", book.id, book.title, book.author,
                    book.category);
        }
        System.out.println("==================================================================");

    }

    Book findBookById(int id) {
        for (Book book : Main.bookList) {
            if (book.id == id) {
                return book;
            }
        }
        return null;
    }

    boolean isBookAlreadyBorrowed(Book book) {
        ArrayList<Book> borrowedBooks = borrowedBooksMap.getOrDefault(nim, new ArrayList<>());
        return borrowedBooks.contains(book);
    }

    void displayBorrowedBooks() {
        ArrayList<Book> borrowedBooks = borrowedBooksMap.getOrDefault(nim, new ArrayList<>());
        System.out.println("Daftar Buku yang Dipinjam:");
        for (Book book : borrowedBooks) {
            System.out.println("Id: " + book.id);
            System.out.println("Judul: " + book.title);
            System.out.println("Penulis: " + book.author);
            System.out.println("Kategori: " + book.category);
            System.out.println();
        }
    }

    void logout() {
        Main.currentUser = null;
    }
}

class Admin {
    String username = "admin";
    String password = "admin";

    boolean login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Username: ");
        String inputUsername = scanner.nextLine();
        System.out.print("Password: ");
        String inputPassword = scanner.nextLine();
        if (inputUsername.equals(username) && inputPassword.equals(password)) {
            System.out.println("Berhasil login sebagai admin.");
            return true;
        } else {
            System.out.println("Login gagal. Username atau password salah.");
            return false;
        }
    }

    void menuAdmin() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("=== Admin Dashboard ===");
            System.out.println("1. Add student");
            System.out.println("2. Display registered student");
            System.out.println("3. Logout");
            System.out.print("Pilih menu (1/2/3): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    displayRegisteredStudent();
                    break;
                case 3:
                    System.out.println("Berhasil logout sebagai admin.");
                    return;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }

    void addStudent() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Masukkan Nama Mahasiswa: ");
        String name = scanner.nextLine();

        String nim = inputNim();

        User newStudent = new User(name, nim);
        Main.userStudents.add(newStudent);

        System.out.println("Student added successfully.");
    }

    String inputNim() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Masukkan NIM Mahasiswa (panjang 15 angka): ");
            String nim = scanner.nextLine();
            if (nim.length() == 15) {
                return nim;
            } else {
                System.out.println("Panjang NIM harus 15 angka. Silakan coba lagi.");
            }
        }
    }

    void displayRegisteredStudent() {
        System.out.println("Daftar Mahasiswa Terdaftar:");
        for (User student : Main.userStudents) {
            System.out.println("Nama: " + student.name);
            System.out.println("NIM: " + student.nim);
            System.out.println();
        }
    }
}
