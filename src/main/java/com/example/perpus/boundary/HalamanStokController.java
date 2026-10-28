package com.example.perpus.boundary;

import com.example.perpus.control.StokService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/stok")
public class HalamanStokController {

    private final StokService stokService;

    public HalamanStokController(StokService stokService) {
        this.stokService = stokService;
    }

    @GetMapping
    public String tampilHalaman(Model model) {
        model.addAttribute("daftarBuku", stokService.daftarBuku());
        return "stok"; // [6] tampilkan daftar buku & stok terbaru
    }

    // [1] ubahStok(idBuku, jumlah) dari aktor Pustakawan
    @PostMapping
    public String ubahStok(@RequestParam String idBuku,
                           @RequestParam int jumlah,
                           Model model) {
        try {
            // [2] tambahStokBuku(idBuku, jumlah)
            stokService.tambahStokBuku(idBuku, jumlah);
            return "redirect:/stok";
        } catch (IllegalArgumentException e) {
            model.addAttribute("pesan", e.getMessage());
            return tampilHalaman(model);
        }
    }
}