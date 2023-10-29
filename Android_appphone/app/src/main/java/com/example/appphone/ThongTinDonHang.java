package com.example.appphone;

public class ThongTinDonHang {
    private String KeyDH;
    private String UID;
    private String KeyDT;
    private String TenNguoiNhan;
    private String DiaChi;
    private int Sdt;
    private String TenSP;
    private int GiaSP;
    private int SoLuong;
    private String AnhSP;
    private String TrangThai;
    private int ngay;
    private int thang;
    private int nam;
    private int GiaDT;
    public ThongTinDonHang() {
    }

    public ThongTinDonHang(String keyDH, String UID, String keyDT, String tenNguoiNhan, String diaChi, int sdt, String tenSP, int giaSP, int soLuong, String anhSP, String trangThai, int ngay, int thang, int nam, int giaDT) {
        KeyDH = keyDH;
        this.UID = UID;
        KeyDT = keyDT;
        TenNguoiNhan = tenNguoiNhan;
        DiaChi = diaChi;
        Sdt = sdt;
        TenSP = tenSP;
        GiaSP = giaSP;
        SoLuong = soLuong;
        AnhSP = anhSP;
        TrangThai = trangThai;
        this.ngay = ngay;
        this.thang = thang;
        this.nam = nam;
        GiaDT = giaDT;
    }

    public String getKeyDH() {
        return KeyDH;
    }

    public void setKeyDH(String keyDH) {
        KeyDH = keyDH;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getKeyDT() {
        return KeyDT;
    }

    public void setKeyDT(String keyDT) {
        KeyDT = keyDT;
    }

    public String getTenNguoiNhan() {
        return TenNguoiNhan;
    }

    public void setTenNguoiNhan(String tenNguoiNhan) {
        TenNguoiNhan = tenNguoiNhan;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public int getSdt() {
        return Sdt;
    }

    public void setSdt(int sdt) {
        Sdt = sdt;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public int getGiaSP() {
        return GiaSP;
    }

    public void setGiaSP(int giaSP) {
        GiaSP = giaSP;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public String getAnhSP() {
        return AnhSP;
    }

    public void setAnhSP(String anhSP) {
        AnhSP = anhSP;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String trangThai) {
        TrangThai = trangThai;
    }

    public int getNgay() {
        return ngay;
    }

    public void setNgay(int ngay) {
        this.ngay = ngay;
    }

    public int getThang() {
        return thang;
    }

    public void setThang(int thang) {
        this.thang = thang;
    }

    public int getNam() {
        return nam;
    }

    public void setNam(int nam) {
        this.nam = nam;
    }

    public int getGiaDT() {
        return GiaDT;
    }

    public void setGiaDT(int giaDT) {
        GiaDT = giaDT;
    }
}
