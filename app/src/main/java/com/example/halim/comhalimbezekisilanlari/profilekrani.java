package com.example.halim.comhalimbezekisilanlari;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class profilekrani extends AppCompatActivity {

    public static boolean PERS_LOGIN;

   private static TextView tv_duzenle,tv_adSoyad;
   private static EditText et_ad,et_soyad, et_mail,et_telno,et_sifre;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilekrani);

        et_ad = findViewById(R.id.etad);
        et_soyad = findViewById(R.id.etsoyad);
        et_mail = findViewById(R.id.etemail);
        et_telno = findViewById(R.id.ettelno);
        et_sifre = findViewById(R.id.etsifresi);
        tv_duzenle = (TextView) findViewById(R.id.tvduzenle);
        tv_adSoyad = findViewById(R.id.tvadsoyad);

        try {
            Veritabani db = new Veritabani(getApplicationContext());
            List<KisiBilgileri> kisiBilgileriList = new ArrayList<KisiBilgileri>();
            kisiBilgileriList = db.SonKaydiGetir();

            StringBuilder stringBuilder = new StringBuilder();//liste bilgilerini getirmek için
            for (KisiBilgileri kisi:kisiBilgileriList)// içindeki verileri bitirene kadar dongu donecek
            {
                stringBuilder.append(kisi.getAd()+kisi.getSoyad()+kisi.getMail()+kisi.getTelNo()+kisi.getSifre());

                et_ad.setText(kisi.getAd());
                et_soyad.setText(kisi.getSoyad());
                et_mail.setText(kisi.getMail());
                et_telno.setText(kisi.getTelNo());
                et_sifre.setText(kisi.getSifre());

                tv_adSoyad.setText(kisi.getAd() + " " + kisi.getSoyad());
            }
        }catch (Exception e){

            Toast.makeText(profilekrani.this, "Herhangi bir kayıt bulunamadı.", Toast.LENGTH_SHORT).show();
        }


        tv_duzenle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ıntent = new Intent(getApplicationContext(),kayitekrani.class);

                startActivity(ıntent);
            }
        });

    }


}
