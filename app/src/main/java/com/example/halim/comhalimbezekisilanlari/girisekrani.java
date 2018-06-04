package com.example.halim.comhalimbezekisilanlari;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class girisekrani extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girisekrani);

        Button btniptal = (Button) findViewById(R.id.btniptal);

        btniptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });


    }

    public void tvsifremiunuttum(View view) {

        switch (view.getId()) {
            case R.id.tvsifremiunuttum:

                Intent ıntent = new Intent(getApplicationContext(),sifremiunuttum.class);

                startActivity(ıntent);
                break;

        }

    }

    public void tvyenikayit(View view){

        if(view.getId() == R.id.tvyenikayit){

            Intent ıntent = new Intent(getApplicationContext(),kayitekrani.class);

            startActivity(ıntent);

        }
    }

}
