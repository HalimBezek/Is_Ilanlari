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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
        //new IlanServisiAsyncTask().execute("view-source:https://www.kariyer.net/is-ilanlari/");//parametre gonderilmese paremetre tanımlansa da olur
        new IlanServisiAsyncTask().execute("https://www.wired.com/feed/");//parametre gonderilmese paremetre tanımlansa da olur

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
                URL url = new URL(params[0]);
                bağlanti =(HttpURLConnection)url.openConnection();
                int baglantiDurumu = bağlanti.getResponseCode();
                if(baglantiDurumu == HttpURLConnection.HTTP_OK){

                    BufferedInputStream bufferedInputStream = new BufferedInputStream(bağlanti.getInputStream());//web servisleri
                    publishProgress("Ilanlar Listeleniyor..");
                    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();  //tek seferlik kullanım için boyle kullanılır.

                    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                    Document document = documentBuilder.parse(bufferedInputStream);

                    NodeList ilanNodeList = document.getElementsByTagName("item");//div olan kısımları alıp bir liste oluşturuyoruz
                                                                                   // (item da olabilirdi siteye göre değişir)
                    for (int i = 0; i<ilanNodeList.getLength();i++ )
                    {
                        Element element = (Element)ilanNodeList.item(i); //her bir divi bir element olarak aldık(item yapılmış uygulamada :))
                      //elementin içindede listeler olacak

                       // NodeList nodeListBaslik = element.getElementsByTagName("div class=\"ilan\\");
                        NodeList nodeListBaslik = element.getElementsByTagName("title");
                        // NodeList nodeListdate = element.getElementsByTagName("p class=\"tarih\\");
                        NodeList nodeListdate = element.getElementsByTagName("pubDate");
                        //NodeList nodeListPosition = element.getElementsByTagName("a class=\"link position\\");
                        NodeList nodeListPosition = element.getElementsByTagName("link");
                        //NodeList nodeListCreator = element.getElementsByTagName("a class=\"link company\\");
                        NodeList nodeListCreator = element.getElementsByTagName("dc:creator");
                        //NodeList nodeListResim = element.getElementsByTagName("div class=\"logo-wrapper logosuz\\");

                        String title = nodeListBaslik.item(0).getFirstChild().getNodeValue();
                        String date = nodeListdate.item(0).getFirstChild().getNodeValue();
                        String position = nodeListPosition.item(0).getFirstChild().getNodeValue();
                        String creator = nodeListCreator.item(0).getFirstChild().getNodeValue();
                        String link = nodeListCreator.item(0).getFirstChild().getNodeValue();


                        Model model = new Model();
                        model.setTitle(title);
                        model.setPosition(position);
                        model.setCreator(creator);
                        model.setDate(date);
                        model.setLink(link);

                        modelList.add(model);
                        publishProgress("Liste güncelleniyor");

/*
                      //resim alma yontemi
                        Pattern p = Pattern.compile(".*<img[^>]*src=\"([^\"]*)", Pattern.CASE_INSENSITIVE);
                        Matcher m = p.matcher(description);
                        String photoUrl = null;

                        while (m.find()) {
                            photoUrl = m.group(1);
                            Bitmap bitmap = null;

                            try {
                                URL  urlResim = new URL(photoUrl.toString().trim());
                                InputStream is = urlResim.openConnection().getInputStream();
                                bitmap = BitmapFactory.decodeStream(is);



                            }

*/}

                } else{
                    //internet bağlantısı yok

                    Toast.makeText(getActivity(), "İnternet bağlantınızı kontrol ediniz", Toast.LENGTH_SHORT).show();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } finally {
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
