package com.example.appphone;

public class ThongBao {
    private String TieuDe;
    private String NoiDung;

    public ThongBao() {
    }

    public ThongBao(String tieuDe, String noiDung) {
        TieuDe = tieuDe;
        NoiDung = noiDung;
    }

    public String getTieuDe() {
        return TieuDe;
    }

    public void setTieuDe(String tieuDe) {
        TieuDe = tieuDe;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }
}
