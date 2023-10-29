package com.example.appphone;

import java.util.HashMap;
import java.util.Map;

public class DienThoai {
    private String Id;
    private String Ten;
    private String ChiTiet;
    private int GiaTien;
    private String LinkAnh;
    private int DaBan;
    private float SoLike;


    public DienThoai() {
    }

    public DienThoai(String id, String ten, String chiTiet, int giaTien, String linkAnh, int daBan, float soLike) {
        Id = id;
        Ten = ten;
        ChiTiet = chiTiet;
        GiaTien = giaTien;
        LinkAnh = linkAnh;
        DaBan = daBan;
        SoLike = soLike;
    }

    public DienThoai(String ten, int gia, String ct, String suaanh, int daban, float like) {
        Ten = ten;
        ChiTiet = ct;
        GiaTien = gia;
        LinkAnh = suaanh;
        DaBan = daban;
        SoLike = like;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getChiTiet() {
        return ChiTiet;
    }

    public void setChiTiet(String chiTiet) {
        ChiTiet = chiTiet;
    }

    public int getGiaTien() {
        return GiaTien;
    }

    public void setGiaTien(int giaTien) {
        GiaTien = giaTien;
    }

    public String getLinkAnh() {
        return LinkAnh;
    }

    public void setLinkAnh(String linkAnh) {
        LinkAnh = linkAnh;
    }

    public int getDaBan() {
        return DaBan;
    }

    public void setDaBan(int daBan) {
        DaBan = daBan;
    }

    public float getSoLike() {
        return SoLike;
    }

    public void setSoLike(float soLike) {
        SoLike = soLike;
    }

    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("ten",Ten);
        result.put("giaTien",GiaTien);
        result.put("chiTiet",ChiTiet);
        result.put("linkAnh",LinkAnh);
        result.put("daBan",DaBan);
        result.put("soLike",SoLike);

        return result;
    }
}
