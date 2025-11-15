package UTSSMT5.util;

import UTSSMT5.model.Barang;
import java.io.File;
import java.io.FileOutputStream; // <-- TAMBAHKAN IMPORT
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.NumberFormat; // <-- TAMBAHKAN IMPORT
import java.util.ArrayList;
import java.util.List;
import java.util.Locale; // <-- TAMBAHKAN IMPORT
import org.json.JSONArray;
import org.json.JSONObject;

// --- TAMBAHKAN IMPORT UNTUK PDF (iText) ---
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

// --- TAMBAHKAN IMPORT UNTUK EXCEL (Apache POI) ---
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


/**
 * File: FileUtil.java
 * Tugas: UTILITY - Menangani semua logika baca/tulis file.
 */
public class FileUtil {
    
    // Helper untuk format mata uang (Rp)
    private static NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    /**
     * Mengekspor daftar barang ke file JSON.
     * (Ini adalah kode Anda yang sudah ada)
     */
    public static boolean exportKeJSON(List<Barang> daftarBarang, File file) {
        // ... (KODE ANDA YANG SUDAH ADA, JANGAN DIUBAH)
        JSONArray jsonArray = new JSONArray();
        for (Barang barang : daftarBarang) {
            JSONObject jsonBarang = new JSONObject();
            jsonBarang.put("kode", barang.getKode());
            jsonBarang.put("nama", barang.getNama());
            jsonBarang.put("stok", barang.getStok());
            jsonBarang.put("harga", barang.getHarga());
            jsonBarang.put("kategori", barang.getKategori());
            jsonArray.put(jsonBarang);
        }

        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(jsonArray.toString(4)); // Indentasi 4
            return true;
        } catch (IOException e) {
            System.err.println("Error saat ekspor JSON: " + e.getMessage());
            return false;
        }
    }

    /**
     * Mengimpor daftar barang dari file JSON.
     * (Ini adalah kode Anda yang sudah ada)
     */
    public static List<Barang> imporDariJSON(File file) {
        // ... (KODE ANDA YANG SUDAH ADA, JANGAN DIUBAH)
        List<Barang> daftarBarang = new ArrayList<>();
        try {
            String content = new String(Files.readAllBytes(file.toPath()));
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonBarang = jsonArray.getJSONObject(i);
                Barang barang = new Barang(
                    jsonBarang.getString("kode"),
                    jsonBarang.getString("nama"),
                    jsonBarang.getInt("stok"),
                    jsonBarang.getDouble("harga"),
                    jsonBarang.getString("kategori")
                );
                daftarBarang.add(barang);
            }
        } catch (Exception e) {
            System.err.println("Error saat impor JSON: " + e.getMessage());
            return new ArrayList<>(); 
        }
        return daftarBarang;
    }
    
    // --- KODE BARU UNTUK EKSPOR PDF ---
    
    /**
     * Mengekspor daftar barang ke file PDF.
     */
    public static boolean exportKePDF(List<Barang> daftarBarang, File file) {
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            
            // Tambahkan Judul
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("Laporan Data Inventaris Barang", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);
            
            // Buat Tabel (4 kolom sesuai GUI)
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            float[] columnWidths = {0.15f, 0.45f, 0.1f, 0.3f};
            table.setWidths(columnWidths);

            // Buat Header Tabel
            Font headFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            String[] headers = {"Kode", "Nama Barang", "Stok", "Harga"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, headFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(8);
                table.addCell(cell);
            }
            
            // Isi Data Tabel
            for (Barang barang : daftarBarang) {
                table.addCell(barang.getKode());
                table.addCell(barang.getNama());
                table.addCell(String.valueOf(barang.getStok()));
                table.addCell(currencyFormat.format(barang.getHarga())); // Format harga
            }
            
            document.add(table);
            document.close();
            return true;
            
        } catch (DocumentException | IOException e) {
            System.err.println("Error saat ekspor PDF: " + e.getMessage());
            return false;
        }
    }
    
    // --- KODE BARU UNTUK EKSPOR EXCEL (.xls) ---

    /**
     * Mengekspor daftar barang ke file Excel (.xls).
     */
    public static boolean exportKeXLS(List<Barang> daftarBarang, File file) {
        try (Workbook workbook = new HSSFWorkbook()) { // HSSF untuk .xls
            Sheet sheet = workbook.createSheet("Data Inventaris");
            
            // Buat Header Row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Kode", "Nama Barang", "Kategori", "Stok", "Harga"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }
            
            // Buat Cell Style untuk Harga (agar tetap angka)
            DataFormat format = workbook.createDataFormat();
            CellStyle styleHarga = workbook.createCellStyle();
            // Format "Rp #,##0" akan menampilkan Rp 5.000.000
            styleHarga.setDataFormat(format.getFormat("\"Rp\" #,##0"));
            
            // Isi Data Rows
            int rowNum = 1;
            for (Barang barang : daftarBarang) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(barang.getKode());
                row.createCell(1).setCellValue(barang.getNama());
                row.createCell(2).setCellValue(barang.getKategori());
                row.createCell(3).setCellValue(barang.getStok());
                
                // Set Harga sebagai Angka (bukan teks)
                Cell hargaCell = row.createCell(4);
                hargaCell.setCellValue(barang.getHarga());
                hargaCell.setCellStyle(styleHarga); // Terapkan style
            }
            
            // Auto-size kolom
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Tulis ke file
            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
            }
            return true;
            
        } catch (IOException e) {
            System.err.println("Error saat ekspor XLS: " + e.getMessage());
            return false;
        }
    }
}