package com.example.halim.comhalimbezekisilanlari;

import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Draft;
import com.google.api.services.gmail.model.Message;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.MediaCas;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class sifremiunuttum extends AppCompatActivity {

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
        mail_konu = "İş İlanları Şifre Değişikliği";
        mail_icerik = "Değerli kullanıcı isteğiniz üzerine şifrenizi değiştirilerek gönderdik \n" +
                "Yeni şifreniz aşağıdadır: \n" +
                "123456789";
        mail_alan = et_mailadres.getText().toString();
        Mail m = new Mail("halimbezek@gmail.com", "571dgm..,,1992"); //gönderecek kişi kullanıcı adı ve şifresini girmeli

        String body = mail_icerik;
        String subject = mail_konu;
        String[] toArr = {mail_alan};//{ emailadress.getText().toString() };
        m.setTo(toArr);
        m.setSubject(subject);
        m.setBody(body);

        try {

            if (m.send()) {
                Toast.makeText(sifremiunuttum.this,
                        "Email başarıla gönderildi.",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(sifremiunuttum.this, "Email gönderilemedi tekrar deneyin.",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.e("MailApp", "Email gönderilemedi daha sonra tekrara deneyin", e);
            Toast.makeText(sifremiunuttum.this, "Email gönderilemedi tekrar deneyin. " + e.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
        }
        finally {
            finish();
        }
    }


}
