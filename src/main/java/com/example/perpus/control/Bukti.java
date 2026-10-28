package com.example.perpus.control;

import com.example.perpus.entity.TransaksiPinjam;
import java.time.LocalDate;

public class Bukti {

    private String idTransaksi;
    private String namaAnggota;
    private String judulBuku;
    private LocalDate tanggalPinjam;
    private String status;

    public Bukti(String idTransaksi, String namaAnggota, String judulBuku, LocalDate tanggalPinjam, String status) {
        this.idTransaksi = idTransaksi;
        this.namaAnggota = namaAnggota;
        this.judulBuku = judulBuku;
        this.tanggalPinjam = tanggalPinjam;
        this.status = status;
    }

    public static Bukti dari(TransaksiPinjam t) {
        return new Bukti(
                t.getIdTransaksi(),
                t.getAnggota().getNama(),
                t.getBuku().getJudul(),
                t.getTanggalPinjam(),
                t.getStatus()
        );
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public String getNamaAnggota() {
        return namaAnggota;
    }

    public String getJudulBuku() {
        return judulBuku;
    }

    public LocalDate getTanggalPinjam() {
        return tanggalPinjam;
    }

    public String getStatus() {
        return status;
    }
}