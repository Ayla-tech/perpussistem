package com.example.perpus;

import com.example.perpus.entity.Anggota;
import com.example.perpus.entity.Buku;
import com.example.perpus.entity.Reservasi;
import com.example.perpus.entity.TransaksiPinjam;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** Unit test method entity yang dipanggil control pada sequence. */
class PinjamEntityTest {

    @Test
    void cekStok_dan_kurangiStok() {
        Buku buku = new Buku("B001", "Pemrograman Java", 1);
        assertTrue(buku.cekStok());
        buku.kurangiStok();
        assertEquals(0, buku.getStok());
        assertFalse(buku.cekStok());
        assertThrows(IllegalStateException.class, buku::kurangiStok);
    }

    @Test
    void cekBatasPinjam_menolak_jika_sudah_mencapai_batas() {
        Anggota anggota = new Anggota("A002", "Sari Dewi", 1);
        Buku buku = new Buku("B003", "Basis Data", 2);
        assertTrue(anggota.cekBatasPinjam());

        TransaksiPinjam t = TransaksiPinjam.buat(anggota, buku);
        assertEquals(TransaksiPinjam.DIPINJAM, t.getStatus());
        assertFalse(anggota.cekBatasPinjam());
    }

    @Test
    void tambahStok_menambah_jumlah_stok_dan_menolak_nilai_negatif() {
        Buku buku = new Buku("B002", "Rekayasa Perangkat Lunak", 0);
        assertFalse(buku.cekStok());

        buku.tambahStok(3);
        assertEquals(3, buku.getStok());
        assertTrue(buku.cekStok());

        assertThrows(IllegalArgumentException.class, () -> buku.tambahStok(0));
        assertThrows(IllegalArgumentException.class, () -> buku.tambahStok(-2));
    }

    @Test
    void buatReservasi_berhasil_jika_stok_habis_dan_menolak_jika_stok_ada() {
        Anggota anggota = new Anggota("A001", "Budi Santoso", 2);
        Buku bukuHabis = new Buku("B002", "Rekayasa Perangkat Lunak", 0);
        Buku bukuAda = new Buku("B001", "Pemrograman Java", 2);

        Reservasi r = Reservasi.buat(anggota, bukuHabis);
        assertEquals(Reservasi.AKTIF, r.getStatus());
        assertEquals(anggota, r.getAnggota());
        assertEquals(bukuHabis, r.getBuku());

        assertThrows(IllegalStateException.class, () -> Reservasi.buat(anggota, bukuAda));
    }
}
