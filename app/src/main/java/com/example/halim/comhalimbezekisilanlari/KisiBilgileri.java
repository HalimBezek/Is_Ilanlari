package com.example.halim.comhalimbezekisilanlari;

public class KisiBilgileri {
    private String Ad;
    private String Soyad;
    private String Mail;
    private String TelNo;
    private String Sifre;
    private String SifreTekrar;

    public KisiBilgileri() { //boş bir yapıcı metot(Constractor)

    }

    //bilgileri buraya almanın burada birkaç yolu var : İstenirse constractor ile verilir ve ya get-set metotlarıyla da verilebilir.
    public KisiBilgileri(String ad, String soyad, String mail, String telNo, String sifre, String sifreTekrar) {
        Ad = ad;
        Soyad = soyad;
        Mail = mail;
        TelNo = telNo;
        Sifre = sifre;
        SifreTekrar = sifreTekrar;
    }

    public String getAd() {
        return Ad;
    }

    public void setAd(String ad) {
        Ad = ad;
    }

    public String getSoyad() {
        return Soyad;
    }

    public void setSoyad(String soyad) {
        Soyad = soyad;
    }

    public String getMail() {
        return Mail;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getTelNo() {
        return TelNo;
    }

    public void setTelNo(String telNo) {
        TelNo = telNo;
    }

    public String getSifre() {
        return Sifre;
    }

    public void setSifre(String sifre) {
        Sifre = sifre;
    }

    public String getSifreTekrar() {
        return SifreTekrar;
    }

    public void setSifreTekrar(String sifreTekrar) {
        SifreTekrar = sifreTekrar;
    }
}
