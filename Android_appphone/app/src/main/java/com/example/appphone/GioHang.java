package com.example.appphone;

public class GioHang {
    private String TenGioHang;
    private int GiaGioHang;
    private int SoLuong;
    private String AnhGioHang;
    private int GiaDT;
    private String KeyDT;
    private int DaBan;

    public GioHang() {
    }

    public GioHang(String tenGioHang, int giaGioHang, int soLuong, String anhGioHang, int giaDT, String keyDT, int daBan) {

        TenGioHang = tenGioHang;
        GiaGioHang = giaGioHang;
        SoLuong = soLuong;
        AnhGioHang = anhGioHang;
        GiaDT = giaDT;
        KeyDT = keyDT;
        DaBan = daBan;
    }

    public String getTenGioHang() {
        return TenGioHang;
    }

    public void setTenGioHang(String tenGioHang) {
        TenGioHang = tenGioHang;
    }

    public int getGiaGioHang() {
        return GiaGioHang;
    }

    public void setGiaGioHang(int giaGioHang) {
        GiaGioHang = giaGioHang;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public String getAnhGioHang() {
        return AnhGioHang;
    }

    public void setAnhGioHang(String anhGioHang) {
        AnhGioHang = anhGioHang;
    }

    public int getGiaDT() {
        return GiaDT;
    }

    public void setGiaDT(int giaDT) {
        GiaDT = giaDT;
    }

    public String getKeyDT() {
        return KeyDT;
    }

    public void setKeyDT(String keyDT) {
        KeyDT = keyDT;
    }

    public int getDaBan() {
        return DaBan;
    }

    public void setDaBan(int daBan) {
        DaBan = daBan;
    }
}
