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
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class frgTumIlanlar extends Fragment
{
    private ListView listView;
    private static ProgressDialog progressDialog;
    private static List<Model> modelList;

    public static String filePath;

    private String ne_ara, nerede_ara;
    public void setNe_ara(String ne_ara) {
        this.ne_ara = ne_ara;
    }

    public void setNerede_ara(String nerede_ara) {
        this.nerede_ara = nerede_ara;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         filePath=  getContext().getFilesDir().getPath().toString() + "/bilgilerJson.txt";
        View view = inflater.inflate(R.layout.frgtum,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView = (ListView) getView().findViewById(R.id.lvTumListe);

        String urls[] = {"https://www.kariyer.net/is-ilanlari/",
                "https://www.yenibiris.com/is-ilanlari",
                "https://www.eleman.net/is-ilanlari/?aranan=&sehir=&ilce="};

        new IlanServisiAsyncTask().execute(urls);//parametre gonderilmese paremetre tanımlansa da olur

        IsIlanlari isIlanlari = new IsIlanlari();

        isIlanlari.setBtnFavColor("#6dacea");
        isIlanlari.setBtnTumColor("#FFB6CFF5");

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

            File file = new File(filePath);

            modelList = new ArrayList<Model>();
            HttpURLConnection bağlanti = null;

            JSONObject jsonObjectChild;
            JSONArray jsonArray = new JSONArray();
            JSONArray jsonArrayChildKariyer = new JSONArray();
            JSONArray jsonArrayChildYenibiriş = new JSONArray();

            if(ne_ara.equals("") && nerede_ara.equals("") || !file.exists() ) {
                file.delete();
                for(int k=0; k<params.length; k++) {
                    try {
                        URL url = new URL(params[k]);//verilen url buraya geliyor.
                        Document document = null;
                        Elements elements = null;
                        Elements pozitionc = null;
                        Elements companyc = null;
                        Elements cityc = null;
                        Elements datec = null;
                        Integer bitmapResimyolu = null;
                        Elements linkForelemanNet = null;

                        if(k==0) { //kariyer.net
                            document = Jsoup.connect(String.valueOf(url)).get();
                            elements = document.select("div[class=ilan]");
                            pozitionc = elements.select("a[class=link position]");
                            companyc = elements.select("a[class=link company]");
                            cityc = elements.select("span[class=city]");
                            datec = elements.select("p[class=tarih]");
                            bitmapResimyolu = R.mipmap.kariyernet_logo;
                        //    bitmapReasim = BitmapFactory.decodeResource(getActivity().getResources(),R.mipmap.kariyernet_logo);

                        }else if(k==1)//yenibiriş.com
                        {
                            document = Jsoup.connect(String.valueOf(url)).get();
                            elements = document.select("div[class=listViewRowsContainer]");
                            pozitionc = elements.select("a[class=truncate]");
                            companyc = elements.select("div[class=truncate jobCompanyLnk]");
                            cityc = elements.select("div[class=truncate fs13]");
                            datec = elements.select("div[class=col-lg-2 col-md-2 col-sm-2 col-xs-4 orderDateTxt]");
                            bitmapResimyolu = R.mipmap.yenibiris_logo;
                        }
                        else if(k==2)//eleman.net
                        {
                            document = Jsoup.connect(String.valueOf(url)).get();
                            elements = document.select("div[class=c-box c-box--small c-box--animation@lg-up u-gap-bottom]");
                            pozitionc = elements.select("h5[class=c-showcase-box__title]");
                            companyc = elements.select("span[itemprop=name]");
                            cityc = elements.select("span[itemprop=addressLocality]");
                            datec = elements.select("meta[itemprop=datePosted]");
                            linkForelemanNet = elements.select("meta[itemprop=url]");
                            bitmapResimyolu = R.mipmap.elemannet_logo;
                        }

                        //String strJson = getJsonFromUrl(URL);
                        //Elements status = elements.select("a[class=link position]");//incelendi mi, başvuruldu mu bilgisi :)
                        // Elements titlea = elements.select("a[data-title]");
                        // String veri4=titlea.html();//istenilen html taglarını çeker.
                        // String aciklama4=Jsoup.parse(veri4).text();//html taglarını texte çevirir.

                        Element pozitionelement;
                        Element campanyelement;
                        Element cityelement;
                        Element dateelement;
                        String data;

                        for (int i = 0; i < pozitionc.size(); i++) {

                            jsonObjectChild = new JSONObject();

                            pozitionelement = pozitionc.get(i);
                            campanyelement = companyc.get(i);
                            cityelement = cityc.get(i);
                            dateelement = datec.get(i);

                            String position = pozitionelement.html();
                            String campany = campanyelement.html();
                            String city = "";
                            String date= "";
                            if(k==1)
                               city = cityelement.attr("title");
                            else city = cityelement.html();

                            if(k==2)
                                date = dateelement.attr("content");
                            else  date = dateelement.html();
                            String link=null;
                            Bitmap bitmapResim = BitmapFactory.decodeResource(getActivity().getResources(), bitmapResimyolu);

                            if(k==0)
                               link = "https://www.kariyer.net" + pozitionc.get(i).attr("href").toString();
                            else if(k==1)
                               link = pozitionc.get(i).attr("href").toString();
                            else if(k==2)
                               link = linkForelemanNet.get(i).attr("content");

                            jsonObjectChild.put("pozition", position);
                            jsonObjectChild.put("campany", campany);
                            jsonObjectChild.put("date", date);
                            jsonObjectChild.put("city", city);
                            jsonObjectChild.put("link", link);
                            jsonObjectChild.put("bitmapYolu",bitmapResimyolu);

                            jsonArray.put(i, jsonObjectChild);

                            Model model = new Model();
                            model.setTitle(position);
                            model.setPosition(position);
                            model.setCreator(campany);
                            model.setDate(date);
                            model.setCity(city);
                            model.setLink(link);
                            model.setResim(bitmapResim);

                            modelList.add(model);
                            publishProgress("Liste güncelleniyor");

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        publishProgress(e.getMessage().toString());
                    } finally {
                        try {

                            if (!file.exists()) {
                                file.createNewFile();
                            }

                            if(k==params.length-1) {//son veri çekilip jsona yazıldıktan onra dosyaya yazdır
                                BufferedWriter buf = new BufferedWriter(new FileWriter(file, true));
                                buf.write(jsonArray.toString());

                                buf.close();
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (bağlanti != null)
                            bağlanti.disconnect();
                    }
                }

            }else //if(ne_ara.equals(null) && nerede_ara.equals(null) && !file.exists() )
            {
                try {
                    StringBuilder sb;

                    File file_read = new File(filePath);

                    FileInputStream fis = new FileInputStream(file_read);
                    DataInputStream in = new DataInputStream(fis);
                    BufferedReader br =
                            new BufferedReader(new InputStreamReader(in));
                    String strLine;
                    sb = new StringBuilder();
                    while ((strLine = br.readLine()) != null) {
                        sb.append(strLine + "\n");
                    }
                    in.close();

                    try {

                        for (int j = 0; j<sb.length();j++) {
                            JSONArray jsonArrayread = new JSONArray(sb.toString());
                            JSONObject ilanObject = jsonArrayread.getJSONObject(j);
                            String pozisyon = ilanObject.getString("pozition");
                            String campany = ilanObject.getString("campany");
                            String date = ilanObject.getString("date");
                            String city = ilanObject.getString("city");
                            String link = ilanObject.getString("link");
                            Integer bitmapResimyolu = ilanObject.getInt("bitmapYolu");

                            String test = campany;

                            if(!ne_ara.equals("") && !nerede_ara.equals(""))
                                if((pozisyon.toLowerCase().indexOf(ne_ara.toLowerCase())) == -1)
                                    if ((city.toLowerCase().indexOf(nerede_ara.toLowerCase())) == -1)
                                {
                                    Model model = new Model();
                                    model.setTitle(pozisyon);
                                    model.setPosition(pozisyon);
                                    model.setCreator(campany);
                                    model.setDate(date);
                                    model.setCity(city);
                                    model.setLink(link);
                                    model.setResim(BitmapFactory.decodeResource(getActivity().getResources(), bitmapResimyolu));
                                    modelList.add(model);

                                }

                             String ne_ = ne_ara.toLowerCase();
                             String pozisyonsadf= pozisyon.toLowerCase();
                             int i = (pozisyon.toLowerCase().indexOf(ne_ara.toLowerCase()));
                             int sd = i;
                             String a = ne_ + pozisyonsadf ;

                            if(!ne_ara.equals("") && nerede_ara.equals(""))
                                if((pozisyon.toLowerCase().indexOf(ne_ara.toLowerCase())) != -1)
                                {
                                    Model model = new Model();
                                    model.setTitle(pozisyon);
                                    model.setPosition(pozisyon);
                                    model.setCreator(campany);
                                    model.setDate(date);
                                    model.setCity(city);
                                    model.setLink(link);
                                    model.setResim(BitmapFactory.decodeResource(getActivity().getResources(), bitmapResimyolu));
                                    modelList.add(model);

                                }
                             if (ne_ara.equals("") && !nerede_ara.equals(""))
                                 if ((city.toLowerCase().indexOf(nerede_ara.toLowerCase())) != -1)
                                {
                                    Model model = new Model();
                                    model.setTitle(pozisyon);
                                    model.setPosition(pozisyon);
                                    model.setCreator(campany);
                                    model.setDate(date);
                                    model.setCity(city);
                                    model.setLink(link);
                                    model.setResim(BitmapFactory.decodeResource(getActivity().getResources(), bitmapResimyolu));
                                    modelList.add(model);

                                }

                            publishProgress("Liste güncelleniyor");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                    if (bağlanti != null)
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
            if(modelList.size() <= 0)
                Toast.makeText(getActivity(), "Aradığınız kriterlerde ilan bulunamadı.", Toast.LENGTH_LONG).show();

        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            progressDialog.setMessage(values[0]);//güncelleniyor
        }
    }
}
