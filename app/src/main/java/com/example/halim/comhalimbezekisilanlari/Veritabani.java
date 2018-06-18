package com.example.halim.comhalimbezekisilanlari;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Veritabani  extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "kisiler_veritabani";
    private static final String TABLE_NAME = "kisiler_tablosu";
    private static final int DATABASE_VERSIYON = 1;


    //KOLON SABİTLERİ
    private static final String ID = "_id";
    private static final String AD = "ad";
    private static final String SOYAD = "soyad";
    private static final String MAIL = "mail";
    private static final String TELNO = "telno";
    private static final String SIFRE = "sifre";
    private static final String ISLOGIN ="islogin";



    public Veritabani(Context context) {//biz değişkenleri burada tanımladığımız için constractorda sadece contexti bıraktık diğerlerini sildik


        super(context, DATABASE_NAME, null, DATABASE_VERSIYON);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {//BIRDAHA TABLO OLUSTURULDUĞUNDA COPY PASTE ILE PRATIK OLUR

        //veritabanını oluşturalım
        String tablo_olustur = "CREATE TABLE " + TABLE_NAME +
                " ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                AD+" TEXT ," +
                SOYAD + " TEXT ," +
                MAIL + " TEXT ," +
                TELNO + " TEXT ," +
                SIFRE + " TEXT ," +
                ISLOGIN + " TEXT )";

        sqLiteDatabase.execSQL(tablo_olustur);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) { //ESKİ TABLOYU SİLİP YENİSİNİ OLUŞTURMAK İÇİN

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public long KayitEkle(KisiBilgileri kisiBilgileri) {

        //onUpgrade(getWritableDatabase(),1,2); // database yeni bir eleman eklendiğinde bu kod bloğu çalıştırılabilir.

        SQLiteDatabase db = this.getWritableDatabase();//hem okunabilir hem yazılabilir durumda açtık
        ContentValues cv = new ContentValues();//kayıt ekleme ve kayıt güncellemede bu sınıftan yararlanılır.

        cv.put(AD,kisiBilgileri.getAd());
        cv.put(SOYAD,kisiBilgileri.getSoyad());
        cv.put(MAIL,kisiBilgileri.getMail());
        cv.put(TELNO,kisiBilgileri.getTelNo());
        cv.put(SIFRE,kisiBilgileri.getSifre());
        cv.put(ISLOGIN,kisiBilgileri.getIsLogin());
        cv.put(ISLOGIN,kisiBilgileri.getIsLogin());

        long id =db.insert(TABLE_NAME,null,cv);//kaydı db ze ekledik. insert eğer başarısıs olursa geriye -1 değeri gönderir
        db.close();
        return id;

    }

    public List<KisiBilgileri> SonKaydiGetir() {
        SQLiteDatabase db = this.getReadableDatabase();//sadece okuyacagız
        String[] stunlar = new String[]{AD,SOYAD,MAIL,TELNO,SIFRE,ISLOGIN};

        Cursor c = db.query(TABLE_NAME,stunlar,null,null,null,null,null);//alınacak stunları biz belirledik
        //Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);//bu metotda kullanılabilir.

        int adsirano = c.getColumnIndex(AD);
        int soyadsirano = c.getColumnIndex(SOYAD);
        int mailsirano = c.getColumnIndex(MAIL);
        int telnosirano = c.getColumnIndex(TELNO);
        int sifresirano = c.getColumnIndex(SIFRE);
        int isloginsirano = c.getColumnIndex(ISLOGIN);


        List<KisiBilgileri> kisiBilgileriList = new ArrayList<KisiBilgileri>();
        for (c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            KisiBilgileri kisiBilgileri = new KisiBilgileri();

            kisiBilgileri.setAd(c.getString(adsirano));
            kisiBilgileri.setSoyad(c.getString(soyadsirano));
            kisiBilgileri.setMail(c.getString(mailsirano));
            kisiBilgileri.setTelNo(c.getString(telnosirano));
            kisiBilgileri.setSifre(c.getString(sifresirano));
            kisiBilgileri.setIsLogin(c.getString(isloginsirano));

            kisiBilgileriList.add(kisiBilgileri);

        }

        db.close();


        return kisiBilgileriList;
    }

    public List<KisiBilgileri> mailAndSifre() {

        SQLiteDatabase db = this.getReadableDatabase();
        String[] stunlar = new String[]{MAIL,SIFRE};

        Cursor c = db.query(TABLE_NAME,stunlar,null,null,null,null,null);

        int mailsirano = c.getColumnIndex(MAIL);
        int sifresirano = c.getColumnIndex(SIFRE);


        List<KisiBilgileri> kisiBilgileriList = new ArrayList<KisiBilgileri>();
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            KisiBilgileri kisiBilgileri = new KisiBilgileri();

            kisiBilgileri.setMail(c.getString(mailsirano));
            kisiBilgileri.setSifre(c.getString(sifresirano));

            kisiBilgileriList.add(kisiBilgileri);
        }


        db.close();
        return kisiBilgileriList;

    }

    public long LoginORNOT(String durum){
        SQLiteDatabase db =this.getWritableDatabase();

        ContentValues cv = new ContentValues();//kayıt ekleme ve kayıt güncellemede bu sınıftan yararlanılır.

        cv.put(ISLOGIN,durum);

        long id =db.insert(TABLE_NAME,null,cv);//kaydı db ze ekledik. insert eğer başarısıs olursa geriye -1 değeri gönderir
        db.close();
        return id;
    }

    public void Guncelle(String ad, String soyad, String mail, String telno, String sifre){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(AD,ad);
        cv.put(SOYAD,soyad);
        cv.put(MAIL,mail);
        cv.put(TELNO,telno);
        cv.put(SIFRE,sifre);

        db.update(TABLE_NAME,cv,null,null);
        db.close();;

    }
}
