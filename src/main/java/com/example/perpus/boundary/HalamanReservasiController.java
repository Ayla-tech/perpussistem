package com.example.perpus.boundary;

import com.example.perpus.control.PinjamGagalException;
import com.example.perpus.control.ReservasiService;
import com.example.perpus.entity.Reservasi;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reservasi")
public class HalamanReservasiController {

    private final ReservasiService reservasiService;

    public HalamanReservasiController(ReservasiService reservasiService) {
        this.reservasiService = reservasiService;
    }

    @GetMapping
    public String tampilAtauAjukan(@RequestParam(required = false) String idBuku,
                                   HttpSession session,
                                   Model model) {
        if (idBuku != null && !idBuku.isBlank()) {
            return prosesReservasi(idBuku, session, model);
        }
        model.addAttribute("daftarReservasi", reservasiService.daftarReservasi());
        return "reservasi";
    }

    // [1] ajukanReservasi(idBuku) dari aktor Anggota
    @PostMapping
    public String ajukanReservasi(@RequestParam String idBuku,
                                  HttpSession session,
                                  Model model) {
        return prosesReservasi(idBuku, session, model);
    }

    private String prosesReservasi(String idBuku, HttpSession session, Model model) {
        String idAnggota = idAnggotaLogin(session);
        try {
            // [2] buatReservasi(idAnggota, idBuku)
            Reservasi reservasi = reservasiService.buatReservasi(idAnggota, idBuku);
            model.addAttribute("reservasi", reservasi);
        } catch (PinjamGagalException | IllegalStateException e) {
            model.addAttribute("pesan", e.getMessage());
        }
        model.addAttribute("daftarReservasi", reservasiService.daftarReservasi());
        return "reservasi"; // [7] tampilkan konfirmasi reservasi
    }

    private String idAnggotaLogin(HttpSession session) {
        String id = (String) session.getAttribute("idAnggota");
        return (id != null) ? id : "A001";
    }
}