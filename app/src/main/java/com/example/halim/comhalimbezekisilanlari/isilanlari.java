package com.example.halim.comhalimbezekisilanlari;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class isilanlari extends AppCompatActivity {

    FragmentManager fragmentManager;
    Button btnFav, btnTum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isilanlari);

        fragmentManager = getSupportFragmentManager();

        btnFav = (Button) findViewById(R.id.btnFrgFav);

        btnTum = (Button) findViewById(R.id.btnFrgAll);

    }

    @SuppressLint("ResourceAsColor")
    public void btnClick(View v)
    {
     switch (v.getId()){

         case R.id.btnFrgFav:

             FragmentTransaction transaction = fragmentManager.beginTransaction();
             frgFavIlanlar  favIlanlar = new frgFavIlanlar();

             //ekleyeceğimiz layout belirtiliyor
             transaction.replace(R.id.lytcontainer,favIlanlar,"favIlanlar");
             transaction.addToBackStack(null);//yapıllan işlem kaydediliyor aslında
             transaction.commit();
             int color = Color.parseColor("#6dacea");
             int colorpres = Color.parseColor("#FFB6CFF5");
             btnTum.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC));
             btnFav.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(colorpres, PorterDuff.Mode.SRC));

             break;

         case R.id.btnFrgAll:
             FragmentTransaction transaction1 = fragmentManager.beginTransaction();
             frgTumIlanlar tumIlanlar = new frgTumIlanlar();

             transaction1.replace(R.id.lytcontainer,tumIlanlar,"frgTumIlanlar");
             transaction1.addToBackStack(null);
             transaction1.commit();

             int color1 = Color.parseColor("#6dacea");
             int colorpres1 = Color.parseColor("#FFB6CFF5");
             btnTum.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(colorpres1, PorterDuff.Mode.SRC));
             btnFav.getBackground().mutate().setColorFilter(new PorterDuffColorFilter(color1, PorterDuff.Mode.SRC));

             break;
     }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_profil) {

            Intent ıntent =  new Intent(getApplicationContext(),profilekrani.class);

            startActivity(ıntent);

            return true;
        }
        if (id == R.id.action_login) {
            Intent intent = new Intent(getApplicationContext(),girisekrani.class);

            startActivity(intent);

            return true;
        }
        if (id == R.id.action_logout) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
