package com.example.halim.comhalimbezekisilanlari;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class girisekrani extends AppCompatActivity {

    private static Button btn_iptal,btn_giris;
    private static TextView tv_sifremiunuttum,tv_yenikayit;
    private static EditText et_mail,et_sifre;

    private Veritabani db ;
    private List<KisiBilgileri> kisiBilgileriList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girisekrani);

        db = new Veritabani(getApplicationContext());
        kisiBilgileriList = new ArrayList<KisiBilgileri>();

        btn_iptal = (Button) findViewById(R.id.btniptal);
        btn_giris = (Button) findViewById(R.id.btngiris);
        tv_yenikayit = (TextView) findViewById(R.id.tvyenikayit);
        tv_sifremiunuttum = (TextView) findViewById(R.id.tvsifremiunuttum);
        et_mail = (EditText) findViewById(R.id.etemail);
        et_sifre= (EditText) findViewById(R.id.etsifre);




        btn_iptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        tv_sifremiunuttum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ıntent = new Intent(getApplicationContext(),sifremiunuttum.class);

                startActivity(ıntent);
            }
        });

        tv_yenikayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ıntent = new Intent(getApplicationContext(),kayitekrani.class);

                startActivity(ıntent);
            }
        });


        btn_giris.setOnClickListener(new View.OnClickListener() {
            String pmail,psifre;

            @Override
            public void onClick(View view) {
                try{

                    kisiBilgileriList = db.mailAndSifre();

                    StringBuilder stringBuilder = new StringBuilder();

                    for (KisiBilgileri kisi: kisiBilgileriList)
                    {
                        stringBuilder.append(kisi.getMail() + kisi.getSifre());//tek listede oluşturmak için kullanılabilir.

                        pmail = kisi.getMail().toString();
                        psifre = kisi.getSifre().toString();

                    }

                }catch (Exception e){

                    Toast.makeText(girisekrani.this, "Kayıt bulunamadı " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }

                String mail = et_mail.getText().toString();
                String sifre = et_sifre.getText().toString();
                if(mail.equals(pmail) && sifre.equals(psifre))
                {
                    Toast.makeText(girisekrani.this, "Giriş Başarılı,\n" +
                            " artık favori ilanlar belirleyebilir ve ya \n farklı ilan sitelrini takip edebilirsiniz. !", Toast.LENGTH_SHORT).show();

                    Veritabani db = new Veritabani(getApplicationContext());
                    long i = db.LoginORNOT("true");

                    if(i==-1)
                        Toast.makeText(girisekrani.this, "Giriş yapılırken bir hata oluştu", Toast.LENGTH_SHORT).show();

                    Intent ıntent = new Intent(getApplicationContext(),isilanlari.class);

                    startActivity(ıntent);

                }
                else
                {
                    Toast.makeText(girisekrani.this, "Meail adresi ve ya şifre hatalı! \n" +
                            "Lütfen kontrol ediniz.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
