package com.example.halim.comhalimbezekisilanlari;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class KayitEkrani extends AppCompatActivity {

    private EditText et_Ad,et_Soyad,et_Email,et_Telno,et_Sifre,et_Sifretekrar;
    private Button btn_Iptal,btn_Kayit;

    Veritabani db;
    List<KisiBilgileri> kisiBilgileriList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayitekrani);

        et_Ad = findViewById(R.id.etadi);
        et_Soyad = findViewById(R.id.etsoyadi);
        et_Email = findViewById(R.id.etemail);
        et_Telno = findViewById(R.id.ettelno);
        et_Sifre = findViewById(R.id.etsifre);
        et_Sifretekrar = findViewById(R.id.etsifret);

        btn_Iptal = findViewById(R.id.btniptal);
        btn_Kayit = findViewById(R.id.btnkayit);

        Veritabani db = new Veritabani(getApplicationContext());
        List<KisiBilgileri> kisiBilgileriList = new ArrayList<KisiBilgileri>();
        kisiBilgileriList = db.SonKaydiGetir();

        if (!kisiBilgileriList.isEmpty())
            KayitDuzenle(kisiBilgileriList);

    }

    private void KayitDuzenle(List<KisiBilgileri> kisiBilgileriList) {

        try {

            StringBuilder stringBuilder = new StringBuilder();//liste bilgilerini getirmek için
            for (KisiBilgileri kisi:kisiBilgileriList)// içindeki verileri bitirene kadar dongu donecek
            {
                stringBuilder.append(kisi.getAd()+kisi.getSoyad()+kisi.getMail()+kisi.getTelNo()+kisi.getSifre());

                et_Ad.setText(kisi.getAd());
                et_Soyad.setText(kisi.getSoyad());
                et_Email.setText(kisi.getMail());
                et_Telno.setText(kisi.getTelNo());
                et_Sifre.setText(kisi.getSifre());


            }
        }catch (Exception e){

            Toast.makeText(KayitEkrani.this, "Herhangi bir kayıt bulunamadı.", Toast.LENGTH_SHORT).show();
        }

    }

    public void butonaTiklandi(View view) {

        switch (view.getId()){
            case R.id.btnkayit:

                String ad = et_Ad.getText().toString();
                String soyad = et_Soyad.getText().toString();
                String email = et_Email.getText().toString();
                String telNo = et_Telno.getText().toString();
                String sifre = et_Sifre.getText().toString();
                String sifreT = et_Sifretekrar.getText().toString();
                String isLogin = "true";

                if (ad.equals("")||soyad.equals("")||email.equals("")||telNo.equals("")||sifre.equals("")||sifreT.equals(""))
                    Toast.makeText(KayitEkrani.this, "Boş alan bırakmayınız !", Toast.LENGTH_SHORT).show();
                else
                if(!sifre.equals(sifreT))
                    Toast.makeText(KayitEkrani.this, "Şifre tekrarı eşleşmedi !", Toast.LENGTH_SHORT).show();
                else {
                    KisiBilgileri kisiBilgileri = new KisiBilgileri(ad, soyad, email, telNo, sifre, sifreT,isLogin);
                    long id = 0;
                    try {

                        Veritabani db = new Veritabani(getApplicationContext());
                        id = db.KayitEkle(kisiBilgileri);//normalde değişkeenler cerilirdi biz burada nesne vererek yapacağız

                        if (id == -1)
                            Toast.makeText(KayitEkrani.this, "Kayıt esnasında bi hata oluştu !", Toast.LENGTH_SHORT).show();
                        else {
                            Toast.makeText(KayitEkrani.this, "Kayıt başarılı..", Toast.LENGTH_SHORT).show();

                            Intent ıntent = new Intent(getApplicationContext(),ProfilEkrani.class);
                            startActivity(ıntent);

                        }

                    } catch (Exception e) {
                        Toast.makeText(KayitEkrani.this, "Hay aksi ! \n" +
                                e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }


                break;

            case R.id.btniptal:

                finish();
                break;

        }

    }


}
