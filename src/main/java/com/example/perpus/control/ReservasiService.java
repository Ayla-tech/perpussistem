package com.example.perpus.control;

import com.example.perpus.entity.Anggota;
import com.example.perpus.entity.AnggotaRepository;
import com.example.perpus.entity.Buku;
import com.example.perpus.entity.BukuRepository;
import com.example.perpus.entity.Reservasi;
import com.example.perpus.entity.ReservasiRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ReservasiService {

    private final AnggotaRepository anggotaRepository;
    private final BukuRepository bukuRepository;
    private final ReservasiRepository reservasiRepository;

    public ReservasiService(AnggotaRepository anggotaRepository,
                            BukuRepository bukuRepository,
                            ReservasiRepository reservasiRepository) {
        this.anggotaRepository = anggotaRepository;
        this.bukuRepository = bukuRepository;
        this.reservasiRepository = reservasiRepository;
    }

    public List<Reservasi> daftarReservasi() {
        return reservasiRepository.findAll();
    }

    @Transactional
    public Reservasi buatReservasi(String idAnggota, String idBuku) {
        Anggota anggota = anggotaRepository.findById(idAnggota)
                .orElseThrow(() -> PinjamGagalException.tidakDitemukan("Anggota"));
        Buku buku = bukuRepository.findById(idBuku)
                .orElseThrow(() -> PinjamGagalException.tidakDitemukan("Buku"));

        // [3] cekStok() -> [4] pastikan stok memang habis
        if (buku.cekStok()) {
            throw new IllegalStateException("Stok buku masih tersedia, silakan langsung pinjam");
        }

        // [5] buat(anggota, buku)
        Reservasi reservasi = Reservasi.buat(anggota, buku);

        // [6] simpan reservasi
        return reservasiRepository.save(reservasi);
    }
}