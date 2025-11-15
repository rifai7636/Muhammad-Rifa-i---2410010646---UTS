package UTSSMT5.service;

import UTSSMT5.model.Barang;
import UTSSMT5.util.FileUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * File: BarangService.java
 * Tugas: CONTROLLER/SERVICE - Mengelola semua logika bisnis (CRUD, Impor/Ekspor).
 */
public class BarangService {

    // ======================================================================
    // BAGIAN INI HILANG DARI KODE ANDA (1)
    // Variabel utama untuk menyimpan data
    private List<Barang> daftarBarang;
    // ======================================================================

    /**
     * Constructor
     * BAGIAN INI HILANG DARI KODE ANDA (2)
     */
    public BarangService() {
        this.daftarBarang = new ArrayList<>();
        // Tambah data dummy
        tambahDataDummy();
    }
    
    /**
     * BAGIAN INI HILANG DARI KODE ANDA (3)
     */
    private void tambahDataDummy() {
        this.daftarBarang.add(new Barang("B001", "Laptop", 10, 5500000, "Elektronik"));
        this.daftarBarang.add(new Barang("B002", "Smartphone", 8, 4000000, "Elektronik"));
        this.daftarBarang.add(new Barang("A001", "Buku Tulis", 100, 5000, "ATK"));
    }

    // ======================================================================
    // SEMUA METHOD DI BAWAH INI HILANG DARI KODE ANDA (4)
    // --- Logika CRUD (Create, Read, Update, Delete) ---
    // ======================================================================
    
    public void tambahBarang(Barang barang) {
        this.daftarBarang.add(barang);
    }

    public List<Barang> getAllBarang() {
        return this.daftarBarang;
    }

    public List<Barang> getBarangByKategori(String kategori) {
        List<Barang> filteredList = new ArrayList<>();
        for (Barang barang : daftarBarang) {
            if (barang.getKategori().equalsIgnoreCase(kategori)) {
                filteredList.add(barang);
            }
        }
        return filteredList;
    }
    
    public List<String> getDaftarKategori() {
        TreeSet<String> kategoriSet = new TreeSet<>();
        for (Barang barang : daftarBarang) {
            kategoriSet.add(barang.getKategori());
        }
        return new ArrayList<>(kategoriSet);
    }
    
    public Barang getBarangByKode(String kode) {
        for (Barang barang : daftarBarang) {
            if (barang.getKode().equals(kode)) {
                return barang;
            }
        }
        return null;
    }

    public boolean updateBarang(String kode, Barang barangBaru) {
        for (int i = 0; i < daftarBarang.size(); i++) {
            if (daftarBarang.get(i).getKode().equals(kode)) {
                daftarBarang.set(i, barangBaru);
                return true; 
            }
        }
        return false; 
    }

    public boolean hapusBarang(String kode) {
        // Hapus barang jika kodenya cocok
        return this.daftarBarang.removeIf(barang -> barang.getKode().equals(kode));
    }

    // --- Logika Impor/Ekspor (Mendelegasikan ke FileUtil) ---
    // (Ini adalah bagian yang sudah Anda miliki)
    //Menambahkan fitur ekspor data barang ke file JSON
    public boolean exportDataJSON(File file) {
        // Sekarang 'this.daftarBarang' sudah ada dan tidak akan error
        return FileUtil.exportKeJSON(this.daftarBarang, file);
    }
    
    public boolean exportDataPDF(File file) {
        // Sekarang 'this.daftarBarang' sudah ada dan tidak akan error
        return FileUtil.exportKePDF(this.daftarBarang, file);
    }
    
    public boolean exportDataXLS(File file) {
        // Sekarang 'this.daftarBarang' sudah ada dan tidak akan error
        return FileUtil.exportKeXLS(this.daftarBarang, file);
    }
    //Menambahkan fitur impor data JSON ke JTable
    public boolean importData(File file) {
        List<Barang> dataBaru = FileUtil.imporDariJSON(file);
        if (!dataBaru.isEmpty() || file.length() == 0) { 
            //Validasi data sebelum ekspor/impor
            // Sekarang 'this.daftarBarang' sudah ada dan tidak akan error
            this.daftarBarang.clear();
            this.daftarBarang.addAll(dataBaru);
            return true;
        }
        return false;
    }
}