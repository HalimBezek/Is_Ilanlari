package com.example.halim.comhalimbezekisilanlari;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class sifremiunuttum extends AppCompatActivity {

    private String mail_gonderen;
    private String mail_alan;
    private String mail_konu;
    private String mail_icerik;

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


                break;
        }

    }

}
