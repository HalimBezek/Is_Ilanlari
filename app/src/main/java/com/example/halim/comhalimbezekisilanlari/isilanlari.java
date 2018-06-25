package com.example.halim.comhalimbezekisilanlari;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class isilanlari extends AppCompatActivity {

    public String ISLOGIN = "false";
    public MenuItem itemlogout, itemlogin;
    private FragmentManager fragmentManager;
    private Button btnFav, btnTum, btn_Ara;
    private EditText et_Ne, et_Nerede;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isilanlari);

        fragmentManager = getSupportFragmentManager();
        btnFav = (Button) findViewById(R.id.btnFrgFav);
        btnTum = (Button) findViewById(R.id.btnFrgAll);

        btn_Ara = findViewById(R.id.btnAra);
        et_Ne = findViewById(R.id.etne);
        et_Nerede = findViewById(R.id.etnerede);

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
             String a = et_Ne.getText().toString();
             String b = et_Nerede.getText().toString();
             tumIlanlar.setNe_ara(et_Ne.getText().toString());
             tumIlanlar.setNerede_ara(et_Nerede.getText().toString());

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

        IsloginControl();

        itemlogin = menu.getItem(1);
        itemlogout = menu.getItem(2);

        if(ISLOGIN.equals("true"))
            itemlogin.setVisible(false);
          // menu.getItem(1).setVisible(false);
        else
            itemlogout.setVisible(false);
          // menu.getItem(2).setVisible(false);



        return true;
    }

    private void IsloginControl() {

        try {
            Veritabani db = new Veritabani(getApplicationContext());
            List<KisiBilgileri> kisiBilgileriList = new ArrayList<KisiBilgileri>();
           // db.LoginORNOT();
            kisiBilgileriList=db.SonKaydiGetir();

            StringBuilder stringBuilder = new StringBuilder();//liste bilgilerini getirmek için
            for (KisiBilgileri kisi:kisiBilgileriList)// içindeki verileri bitirene kadar dongu donecek
            {
              ISLOGIN = kisi.getIsLogin();
            }
        }catch (Exception e){

            Toast.makeText(isilanlari.this, "Kayıt bilgisi bulunamadı.", Toast.LENGTH_SHORT).show();
        }

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
        if (id == R.id.action_ogin) {
            Intent intent = new Intent(getApplicationContext(),girisekrani.class);

            startActivity(intent);

            return true;
        }
        if (id == R.id.action_ogout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Uyarı!");
            builder.setMessage("Çıkış yapmak istediğinizden emin misiniz ? ");
            builder.setIcon(android.R.drawable.ic_dialog_alert);

            //builder.setCancelable(false);//herhangi bir yere tıklandığında diyalog kaybolmasın

            builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                Veritabani db = new Veritabani(getApplicationContext());
                long i = db.LoginORNOT("false");

                if(i==-1)
                    Toast.makeText(isilanlari.this, "Çıkış yapılırken bir hata oluştu", Toast.LENGTH_SHORT).show();


                itemlogin.setVisible(true);
                itemlogout.setVisible(false);

                dialog.dismiss();
                }
            });
            builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void btnArama(View view) {
        btnClick(btnTum);
    }
}
