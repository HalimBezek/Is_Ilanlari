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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.example.halim.comhalimbezekisilanlari.frgTumIlanlar.filePath;

public class IsIlanlari extends AppCompatActivity {

    public String ISLOGIN = "false";
    public MenuItem itemlogout, itemlogin;
    private FragmentManager fragmentManager;
    private Button btnFav, btnTum, btn_Ara;
    //private EditText et_Ne, et_Nerede;
    private AutoCompleteTextView actv_Ne, actv_Nerede;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isilanlari);

        fragmentManager = getSupportFragmentManager();
        btnFav = (Button) findViewById(R.id.btnFrgFav);
        btnTum = (Button) findViewById(R.id.btnFrgAll);

        btn_Ara = findViewById(R.id.btnAra);

        ArrayAdapter<String> adapterNe = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, JOBS);
        actv_Ne = (AutoCompleteTextView)findViewById(R.id.actvNe);
        actv_Ne.setAdapter(adapterNe);

        ArrayAdapter<String> adapterNerede = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line,COUNTRIES);
        actv_Nerede = (AutoCompleteTextView)findViewById(R.id.actvNerede);
        actv_Nerede.setAdapter(adapterNerede);
    }
    private static final String[] COUNTRIES = new String[] {
            "Adana", "Adıyaman", "Afyon", "Ağrı", "Amasya", "Ankara", "Antalya", "Artvin",
            "Aydın", "Balıkesir", "Bilecik", "Bingöl", "Bitlis", "Bolu", "Burdur", "Bursa", "Çanakkale",
            "Çankırı", "Çorum", "Denizli", "Diyarbakır", "Edirne", "Elazığ", "Erzincan", "Erzurum", "Eskişehir",
            "Gaziantep", "Giresun", "Gümüşhane", "Hakkari", "Hatay", "Isparta", "Mersin", "İstanbul", "İzmir",
            "Kars", "Kastamonu", "Kayseri", "Kırklareli", "Kırşehir", "Kocaeli", "Konya", "Kütahya", "Malatya",
            "Manisa", "Kahramanmaraş", "Mardin", "Muğla", "Muş", "Nevşehir", "Niğde", "Ordu", "Rize", "Sakarya",
            "Samsun", "Siirt", "Sinop", "Sivas", "Tekirdağ", "Tokat", "Trabzon", "Tunceli", "Şanlıurfa", "Uşak",
            "Van", "Yozgat", "Zonguldak", "Aksaray", "Bayburt", "Karaman", "Kırıkkale", "Batman", "Şırnak",
            "Bartın", "Ardahan", "Iğdır", "Yalova", "Karabük", "Kilis", "Osmaniye", "Düzce"

};
    private static final String[] JOBS = new String[] {// şimdilik manuel dolduruldu geliştirilince alınan iş ilan pozisyonları eklenecek
            "java","android","Hemşirelik Hizmetleri Müdürü","Tele-Satış Uzmanı",
            "Hemşire Yardımcılığı Öğretmenliği",
            "IT Teknik Destek Uzmanı",
            "Web Sosyal Medya Uzmanı","Project Team Leader",
            "Muhasebe Elemanı","Marketing Communications Specialist","Muhasebe Uzmanı","Gönüllü İlişkileri Yetkilisi","VERİ GİRİŞ UZMANI",
            "Procurement Engineer","Senior Software Engineer / Cloud Team","Kurumsal Satış Danışmanı",
            "Software Development and Test Engineers (ANKARA)","Genel Başvuru -Engelli-","Direkt Satış Uzmanı-Manisa Şube",
            "BT UYGULAMA YÖNETİCİSİ - GAZİANTEP","Otomotiv Elektrik-Elektronik Mühendisi (Ankara)","Muhasebe Uzmanı",
            "Genel Başvuru","Güvenlik Görevlisi","Teknik Eleman","Satış Destek Uzmanı","Sosyal Medya Uzmanı",
            "İNŞAAT TEKNİKERİ","Ticari Kayıplar Uzmanı","Misafir İlişkileri Görevlisi","Product Management and Development Marketing Spc.",
            "Kaynak Robot Operatörü","Mekanik Teknisyeni (Dicle Otomotiv-Diyabakır)","CMM ÖLÇÜM OPERATÖRÜ","Aktif Satış Temsilcisi",
            "Bilgi Teknolojileri İş Analisti","Kauçuk Pres Operatörü","Üretim Planlama Mühendisi",
            "Final Kalite Kontrol Elemanı","İngilizce Öğretmeni","Temizlik Hizmetleri Proje Müdürü","CNC Operatörü",
            "İş Analisti","AR-GE Uzmanı","Depo Elemanı","Aktif Satış Danışmanı","Proses Mühendisi","Saha Satış Uzmanı - Sakarya",
            "Junior Accountant","Ön Büro Sorumlusu","BEYLİKDÜZÜ - SATIŞ&amp;KASA ELEMANI"
    };
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

             String a = actv_Ne.getText().toString();

             tumIlanlar.setNe_ara(actv_Ne.getText().toString());
             tumIlanlar.setNerede_ara(actv_Nerede.getText().toString());

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

            Toast.makeText(IsIlanlari.this, "Kayıt bilgisi bulunamadı.", Toast.LENGTH_SHORT).show();
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
            if(!itemlogin.isVisible()) {//yani login yapılmış ve logaut itemi aktifse
                Intent ıntent = new Intent(getApplicationContext(), ProfilEkrani.class);

                startActivity(ıntent);
           }else
                Toast.makeText(this, "Profil ekranını görmek için giriş yapmanız gerekmektedir", Toast.LENGTH_LONG).show();

            return true;
        }
        if (id == R.id.action_ogin) {
            Intent intent = new Intent(getApplicationContext(),GirisEkrani.class);

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
                    Toast.makeText(IsIlanlari.this, "Çıkış yapılırken bir hata oluştu", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(IsIlanlari.this, "Çıkış işlemi başarıyla gerçekleşti", Toast.LENGTH_LONG).show();


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
        btnClick(btnTum);//arama butonuna basıldığında tüm ilanlarda arama yapacağı için o fonsiyon üzerinden gidildi.
    }
}
