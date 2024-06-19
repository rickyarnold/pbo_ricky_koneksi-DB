package perpustakaan;

import java.sql.*;
import java.util.Scanner;

public class Perpustakaan {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1/perpustakaan";
    static final String USER = "root";
    static final String PASS = "";

    static Connection conn = null;
    static Statement stmt = null;
    static ResultSet rs = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("=== Menu Perpustakaan ===");
            System.out.println("1. Insert Buku");
            System.out.println("2. Show Buku");
            System.out.println("3. Edit Buku");
            System.out.println("4. Delete Buku");
            System.out.println("5. Exit");
            System.out.print("Pilih menu: ");
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    insert(scanner);
                    break;
                case 2:
                    show();
                    break;
                case 3:
                    edit(scanner);
                    break;
                case 4:
                    delete(scanner);
                    break;
                case 5:
                    System.out.println("Keluar dari program.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        } while (choice != 5);

        scanner.close();
    }

    public static void insert(Scanner scanner) {
        System.out.print("Masukkan ID Buku: ");
        String id = scanner.nextLine();
        System.out.print("Masukkan Judul Buku: ");
        String judul_buku = scanner.nextLine();
        System.out.print("Masukkan Tahun Terbit: ");
        int tahun_terbit = scanner.nextInt();
        System.out.print("Masukkan Stok: ");
        int stok = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Masukkan ID Penulis: ");
        String id_penulis = scanner.nextLine();

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "INSERT INTO buku (id, judul_buku, tahun_terbit, stok, id_penulis) VALUES (?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, id);
            ps.setString(2, judul_buku);
            ps.setInt(3, tahun_terbit);
            ps.setInt(4, stok);
            ps.setString(5, id_penulis);
            ps.executeUpdate();

            ps.close();
            System.out.println("Data buku berhasil ditambahkan.");
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static void show() {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            String sql = "SELECT * FROM buku";
            rs = stmt.executeQuery(sql);

            int i = 1;
            while (rs.next()) {
                System.out.println("Data ke-" + i);
                System.out.println("ID Buku: " + rs.getString("id"));
                System.out.println("Judul Buku: " + rs.getString("judul_buku"));
                System.out.println("Tahun Terbit: " + rs.getInt("tahun_terbit"));
                System.out.println("Stok: " + rs.getInt("stok"));
                System.out.println("ID Penulis: " + rs.getString("id_penulis"));
                i++;
            }

            rs.close();
            stmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static void edit(Scanner scanner) {
        System.out.print("Masukkan ID Buku yang akan diubah: ");
        String id = scanner.nextLine();
        System.out.print("Masukkan Judul Buku baru: ");
        String judul_buku = scanner.nextLine();
        System.out.print("Masukkan Tahun Terbit baru: ");
        int tahun_terbit = scanner.nextInt();
        System.out.print("Masukkan Stok baru: ");
        int stok = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Masukkan ID Penulis baru: ");
        String id_penulis = scanner.nextLine();

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "UPDATE buku SET judul_buku=?, tahun_terbit=?, stok=?, id_penulis=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, judul_buku);
            ps.setInt(2, tahun_terbit);
            ps.setInt(3, stok);
            ps.setString(4, id_penulis);
            ps.setString(5, id);
            ps.executeUpdate();

            ps.close();
            System.out.println("Data buku berhasil diubah.");
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static void delete(Scanner scanner) {
        System.out.print("Masukkan ID Buku yang akan dihapus: ");
        String id = scanner.nextLine();

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "DELETE FROM buku WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, id);
            ps.executeUpdate();

            ps.close();
            System.out.println("Data buku berhasil dihapus.");
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}

