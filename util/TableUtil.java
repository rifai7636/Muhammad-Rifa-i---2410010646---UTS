package UTSSMT5.util;

import UTSSMT5.model.Barang;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 * File: TableUtil.java
 * Tugas: UTILITY - Menangani semua logika yang berhubungan dengan JTable.
 */
public class TableUtil {

    // Helper untuk format mata uang (Rp)
    private static NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
    
    /**
     * Mengupdate JTable dengan data dari list.
     */
    public static void updateBarangTable(DefaultTableModel tableModel, List<Barang> daftarBarang) {
        // Kosongkan tabel
        tableModel.setRowCount(0);
        
        if (daftarBarang == null) return;
        
        // Isi tabel dengan data baru
        for (Barang barang : daftarBarang) {
            Object[] rowData = {
                barang.getKode(),
                barang.getNama(),
                barang.getStok(),
                currencyFormat.format(barang.getHarga()) // Format sebagai mata uang
            };
            tableModel.addRow(rowData);
        }
    }
    
    /**
     * Mengatur lebar kolom JTable agar sesuai desain.
     */
    public static void setupTableColumns(javax.swing.JTable jTableBarang) {
        TableColumnModel columnModel = jTableBarang.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);  // Kode
        columnModel.getColumn(1).setPreferredWidth(150); // Nama
        columnModel.getColumn(2).setPreferredWidth(40);  // Stok
        columnModel.getColumn(3).setPreferredWidth(100); // Harga
    }
    
    /**
     * Mengambil nilai harga asli (double) dari tabel yang sudah diformat.
     */
    public static double getHargaAsliFromTable(String formattedHarga) {
         try {
             // Menghilangkan "Rp", spasi, dan titik ribuan
            String cleanString = formattedHarga.replaceAll("[^\\d,]", "").replace(",", ".");
            return Double.parseDouble(cleanString);
        } catch (Exception e) {
            return 0.0;
        }
    }
}