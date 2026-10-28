package com.example.perpus.control;

import com.example.perpus.entity.Buku;
import com.example.perpus.entity.BukuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class StokService {

    private final BukuRepository bukuRepository;

    public StokService(BukuRepository bukuRepository) {
        this.bukuRepository = bukuRepository;
    }

    public List<Buku> daftarBuku() {
        return bukuRepository.findAll();
    }

    @Transactional
    public void tambahStokBuku(String idBuku, int jumlah) {
        Buku buku = bukuRepository.findById(idBuku)
                .orElseThrow(() -> new IllegalArgumentException("Buku tidak ditemukan"));

        // [3] tambahStok(jumlah) -> [4] stok bertambah
        buku.tambahStok(jumlah);

        // [5] simpan perubahan
        bukuRepository.save(buku);
    }
}