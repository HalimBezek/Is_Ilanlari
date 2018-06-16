package com.example.halim.comhalimbezekisilanlari;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.MediaCas;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifremiunuttum);

        final EditText et_mailadres = findViewById(R.id.etemail);
        final Button btn_sifregonder = findViewById(R.id.btnsifregonder);
        final TextView tviptal = findViewById(R.id.tviptal);

        mail_alan = et_mailadres.getText().toString();

        tviptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void btntikla(View view) {

        switch (view.getId()){

            case R.id.btnsifregonder:

                mail_konu = "İş İlanları Şifre Değişikliği";
                mail_icerik = "Değerli kullanıcı isteğiniz üzerine şifrenizi değiştirilerek gönderdik \n" +
                        "Yeni şifreniz aşağıdadır: \n" +
                        "123456789";


                Properties props = new Properties();
                props.put("mail.smtp.host","smtp.gmail.com");
                props.put("mail.smtp.socketFactory.port", "465");
                props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.port", "465");

                session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("halimbezek@gmail.com", "571dgm..,,_1992");
                    }
                });

              //  progressDialog = ProgressDialog.show(getApplication(), "Mail", "Mail  Gönderiliyor…",true);//context
                RetreiveFeedTask task= new RetreiveFeedTask();
                task.execute();
                break;
        }}

    private class RetreiveFeedTask extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {
            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("halimbezek@gmail.com"));//Kimden gönderiliyor?
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("halimbezek@gmail.com"));//(mail_alan Kime gönderiliyor?
                message.setSubject( mail_konu);
                message.setContent(mail_konu, "text/html; charset=utf-8");//message.setContent(talep.getText().toString(), “text/html; charset=utf-8″);

                Transport.send(message);// Mail gönderiliyor //Transport.send(mesaj); Mail gönderiliyor

            }
            catch (MessagingException e) {//Hata yakalama
                e.printStackTrace();
            }
            catch (Exception e) {
                e.printStackTrace();

            }
            return null;

        }

        protected void onPostExecute(String feed) {//İşler yolunda gitmişse

            progressDialog.dismiss();//Dialoğu kapat
            Toast.makeText(getApplicationContext(), "Mail başarıyla gönderildi..", Toast.LENGTH_LONG).show();

        }
    }
                /*
                Intent mailıntent = new Intent(Intent.ACTION_SEND);
                mailıntent.setType("message/rfc822");
                mailıntent.putExtra(Intent.EXTRA_EMAIL, new String[]{mail_alan});
                mailıntent.putExtra(Intent.ACTION_ANSWER, new String[]{mail_alan});
                mailıntent.putExtra(Intent.EXTRA_SUBJECT, mail_konu);
                mailıntent.putExtra(Intent.EXTRA_TEXT, mail_icerik);

                try {

                    startActivity(mailıntent);

                }catch (ActivityNotFoundException e){
                    Toast.makeText(this, "Hata oluştu " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
*/

}
