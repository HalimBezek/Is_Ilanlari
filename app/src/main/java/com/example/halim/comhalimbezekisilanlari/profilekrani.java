package com.example.halim.comhalimbezekisilanlari;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class profilekrani extends AppCompatActivity {

   private static TextView tviptal,tvduzenle;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilekrani);

        tvduzenle = (TextView) findViewById(R.id.tvduzenle);
        tviptal = (TextView) findViewById(R.id.tviptal);

        tviptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ıntent = new Intent(getApplicationContext(),isilanlari.class);

                startActivity(ıntent);
            }
        });

        tviptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
