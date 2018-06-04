package com.example.halim.comhalimbezekisilanlari;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class sifremiunuttum extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifremiunuttum);

        TextView tviptal = findViewById(R.id.tviptal);

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
                Intent ıntent = new Intent(getApplicationContext(),girisekrani.class);

                startActivity(ıntent);

                break;
        }

    }

}
