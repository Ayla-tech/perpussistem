package com.example.perpus.boundary;

import com.example.perpus.control.Bukti;
import com.example.perpus.control.PinjamGagalException;
import com.example.perpus.control.PinjamService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HalamanPinjamController {

    private final PinjamService pinjamService; // «control»

    public HalamanPinjamController(PinjamService pinjamService) {
        this.pinjamService = pinjamService;
    }

    /** Langkah 1-2 scenario: tampilkan daftar buku beserta stok. */
    @GetMapping({"/", "/pinjam"})
    public String tampilHalaman(HttpSession session, Model model) {
        model.addAttribute("idAnggota", idAnggotaLogin(session));
        model.addAttribute("daftarBuku", pinjamService.daftarBuku());
        return "pinjam"; // templates/pinjam.html
    }

    /** [1] pilihBuku(idBuku) dari aktor Anggota. */
    @PostMapping({"/", "/pinjam"})
    public String pilihBuku(@RequestParam String idBuku,
                            HttpSession session, Model model) {
        String idAnggota = idAnggotaLogin(session);
        try {
            // [2] pinjamBuku(idAnggota, idBuku)
            Bukti bukti = pinjamService.pinjamBuku(idAnggota, idBuku);
            return tampilBukti(bukti, model); // [12] tampilkan bukti
        } catch (PinjamGagalException e) { // [13] pesanHabis
            model.addAttribute("pesan", e.getMessage());
            model.addAttribute("bisaReservasi", e.isBisaReservasi());
            model.addAttribute("idBukuGagal", idBuku);
            return tampilHalaman(session, model); // [14] pesan & reservasi
        }
    }

    /** +tampilBukti() pada class diagram 8a. */
    private String tampilBukti(Bukti bukti, Model model) {
        model.addAttribute("bukti", bukti);
        return "bukti"; // templates/bukti.html
    }

    private String idAnggotaLogin(HttpSession session) {
        String id = (String) session.getAttribute("idAnggota");
        return (id != null) ? id : "A001";
    }
}