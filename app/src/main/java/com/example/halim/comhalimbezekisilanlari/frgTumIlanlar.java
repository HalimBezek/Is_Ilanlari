package com.example.halim.comhalimbezekisilanlari;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class frgTumIlanlar extends Fragment
{
    private ListView listView;
    private ProgressDialog progressDialog;
    private List<Model> modelList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frgtum,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView = (ListView) getView().findViewById(R.id.lvTumListe);
        new IlanServisiAsyncTask().execute("https://www.kariyer.net/is-ilanlari/");//parametre gonderilmese paremetre tanımlansa da olur
        //new IlanServisiAsyncTask().execute("https://www.wired.com/feed/");//parametre gonderilmese paremetre tanımlansa da olur

    }

    class IlanServisiAsyncTask extends AsyncTask<String,String,List<Model>>{

        @Override
        protected List<Model> doInBackground(String... params) {
            //override deilmsi zorumlu
            //1.parametre buraya geliyor
            //execute metoduna verilen arguman ile çağrılır.Arkaplan işlemleri burada yapılır.
            //deriye donen değer 3. parametre tipinde ve OnPostExecute metoduna arguman olarak veriliyor.
            //UI theread içinde değil yardımcı thread içinde çalışır
            //burada arayüz guncellenmez
            //publishProgress metodu ile onProgrsssUpdate metoduna bilgi gonderiliyor.

            modelList = new ArrayList<Model>();
            HttpURLConnection bağlanti = null;
            try {
                URL url = new URL(params[0]);//verilen url buraya geliyor.
                Document document = Jsoup.connect(String.valueOf(url)).get();
                Elements elements = document.select("div[class=ilan]");
                Elements pozitionc = elements.select("a[class=link position]");
                Elements companyc = elements.select("a[class=link company]");
                Elements cityc = elements.select("span[class=city");
                Elements datec = elements.select("p[class=tarih]");

                //Elements status = elements.select("a[class=link position]");//incelendi mi, başvuruldu mu bilgisi :)

               // Elements titlea = elements.select("a[data-title]");

               // String veri4=titlea.html();//istenilen html taglarını çeker.
               // String aciklama4=Jsoup.parse(veri4).text();//html taglarını texte çevirir.

                Element pozitionelement;
                Element campanyelement;
                Element cityelement;
                Element dateelement;
                String data;

                for (int i = 0; i<pozitionc.size();i++)
                {
                    pozitionelement = pozitionc.get(i);
                    campanyelement = companyc.get(i);
                    cityelement = cityc.get(i);
                    dateelement = datec.get(i);
                    pozitionelement = pozitionc.get(i);

                    String position = pozitionelement.html();
                    String campany = campanyelement.html();
                    String city = cityelement.html();
                    String date = dateelement.html();

                    Model model = new Model();
                       model.setTitle(position);
                        model.setPosition(position);
                        model.setCreator(campany);
                        model.setDate(date);
                        model.setLink(city);

                    modelList.add(model);
                    publishProgress("Liste güncelleniyor");

                  //  data = elementjsoup.outerHtml();
                  //  data += "<br/>";// String dataText = data;

                }

            }catch (Exception e){}
            finally {
                if(bağlanti!=null)
                    bağlanti.disconnect();
            }


            return modelList;//geriye dondurulecek değer gonderilen 3. parametredir
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

            progressDialog = ProgressDialog.show(getActivity(),"Lütfen bekleyiniz..","İşlem yürütülüyor",true);
        }

        @Override
        protected void onPostExecute(List<Model> modelList) {
            super.onPostExecute(modelList);
            //Customadaptor ile Litviewi güncelleyeceğiz.

            CustomAdaptor customAdaptor = new CustomAdaptor(getActivity(),modelList);
            listView.setAdapter(customAdaptor);
            progressDialog.cancel();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            progressDialog.setMessage(values[0]);//güncelleniyor
        }
    }
}
