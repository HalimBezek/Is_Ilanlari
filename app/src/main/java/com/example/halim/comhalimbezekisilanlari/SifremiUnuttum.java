package com.example.halim.comhalimbezekisilanlari;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;

import javax.mail.Session;
import javax.mail.*;

public class SifremiUnuttum extends AppCompatActivity {

    private String mail_gonderen;
    private String mail_alan;
    private String mail_konu;
    private String mail_icerik;

    private Session session = null;
    private ProgressDialog progressDialog =null;
    private Context context = null;

    private EditText et_mailadres;
    private TextView tviptal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // android.os.NetworkOnMainThreadException hatası için iki kod satırı eklendi.
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_sifremiunuttum);

        et_mailadres = findViewById(R.id.etemail);
        tviptal = findViewById(R.id.tviptal);

        tviptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void btnsifreGonder(View view) throws MessagingException, IOException {

        switch (view.getId()){
            case R.id.btnsifregonder:
                sendEmail();
                break;
        }}


    private void sendEmail() {

        String yenisifre = sifreOlusur();
        mail_konu = "İş İlanları Uygulaması Şifre Değişikliği";
        mail_icerik = "Değerli kullanıcı şifreniz değiştirildi \n" +
                "Yeni şifreniz aşağıdadır: \n\n\n" +
                yenisifre + "\n\n" +
                "Görüş, öneri ve şikayetleriniz için halimbezek@gmail.com adresine mail atabilirsiniz.";
        mail_alan = et_mailadres.getText().toString();
        Mail m = new Mail("isilanlariuygulamasi@gmail.com", "123hlmbzk..,,"); //gönderecek kişi kullanıcı adı ve şifresini girmeli

        String body = mail_icerik;
        String subject = mail_konu;
        String[] toArr = {mail_alan};//{ emailadress.getText().toString() };
        m.setTo(toArr);
        m.setSubject(subject);
        m.setBody(body);

        try {

            if (m.send()) {

                try {

                    Veritabani veritabani = new Veritabani(getApplicationContext());
                    veritabani.SifreGuncelle(yenisifre);

                    Toast.makeText(SifremiUnuttum.this,"Email başarıla gönderildi.",Toast.LENGTH_LONG).show();

                }catch (Exception e)
                {
                    Toast.makeText(context, "Şifre değiştirirken hata oluştu", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(SifremiUnuttum.this, "Email gönderilemedi tekrar deneyin.",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.e("MailApp", "Email gönderilemedi daha sonra tekrara deneyin", e);
            Toast.makeText(SifremiUnuttum.this, "Email gönderilemedi tekrar deneyin. " + e.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
        }
        finally {
            finish();
        }
    }

    private String sifreOlusur() {

        final String symbolstring = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789._,";

        final Random random = new Random();
        final char[] symbols = symbolstring.toCharArray();
        final char[] buf = new char[6];

        for (int idx = 0; idx < 6; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];

        return new String(buf);
    }


}
