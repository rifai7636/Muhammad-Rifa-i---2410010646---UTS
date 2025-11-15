package UTSSMT5.model;

/**
 * File: Barang.java
 * Tugas: MODEL - Menyimpan data untuk satu barang (POJO).
 * Konsep OOP: Encapsulation.
 */
public class Barang {

    private String kode;
    private String nama;
    private int stok;
    private double harga;
    private String kategori;

    /**
     * Constructor
     */
    public Barang(String kode, String nama, int stok, double harga, String kategori) {
        this.kode = kode;
        this.nama = nama;
        this.stok = stok;
        this.harga = harga;
        this.kategori = kategori;
    }

    // --- Getters & Setters ---
    public String getKode() { return kode; }
    public void setKode(String kode) { this.kode = kode; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public int getStok() { return stok; }
    public void setStok(int stok) { this.stok = stok; }
    public double getHarga() { return harga; }
    public void setHarga(double harga) { this.harga = harga; }
    public String getKategori() { return kategori; }
    public void setKategori(String kategori) { this.kategori = kategori; }

    @Override
    public String toString() {
        return this.nama;
    }
}