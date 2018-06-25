package com.example.halim.comhalimbezekisilanlari;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
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

import static android.content.Context.MODE_PRIVATE;


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
        //AsyncTaskServisleri asyncTaskServisleri = new AsyncTaskServisleri();
        //new AsyncTaskServisleri().execute();
        //serv
       // new AsyncTaskServisleri().execute();
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

            String a = ne_ara;
            String b = nerede_ara;
            //String filePath =  getContext().getFilesDir().getPath().toString() + "/bilgilerJson.txt";
            File file = new File(filePath);

            modelList = new ArrayList<Model>();
            HttpURLConnection bağlanti = null;

            JSONObject jsonObjectChild;
            JSONArray jsonArray = new JSONArray();

            if(ne_ara.equals("") && nerede_ara.equals("") || !file.exists() ) {

                try {
                    URL url = new URL(params[0]);//verilen url buraya geliyor.
                    Document document = Jsoup.connect(String.valueOf(url)).get();
                    Elements elements = document.select("div[class=ilan]");
                    Elements pozitionc = elements.select("a[class=link position]");
                    Elements companyc = elements.select("a[class=link company]");
                    Elements cityc = elements.select("span[class=city");
                    Elements datec = elements.select("p[class=tarih]");

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

                    file.delete();

                    for (int i = 0; i < pozitionc.size(); i++) {

                        jsonObjectChild =  new JSONObject();

                        pozitionelement = pozitionc.get(i);
                        campanyelement = companyc.get(i);
                        cityelement = cityc.get(i);
                        dateelement = datec.get(i);
                        pozitionelement = pozitionc.get(i);

                        String position = pozitionelement.html();
                        String campany = campanyelement.html();
                        String city = cityelement.html();
                        String date = dateelement.html();

                        jsonObjectChild.put("pozition", position);
                        jsonObjectChild.put("campany", campany);
                        jsonObjectChild.put("date", date);
                        jsonObjectChild.put("city", city);


                        jsonArray.put(i, jsonObjectChild);

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

                } catch (Exception e) {
                } finally {
                    try {


                    /*
                    File file = new File("bilgilerJson.txt");
                    if(!file.exists()){
                    //    file.createNewFile();
                    }*/

                        if (!file.exists()) {
                            file.createNewFile();
                        }

                        BufferedWriter buf = new BufferedWriter(new FileWriter(file, true));
                        buf.write(jsonArray.toString());
                        //buf.append(jsonObject.toString());
                        // buf.newLine();
                        buf.close();

                   /* Writer output = null;
                    File file = new File("bilgilerJson.txt");
                    output = new BufferedWriter(new FileWriter(file));
                    output.write(jsonObject.toString());
                    output.close();*/
                        //fileWriter.write(jsonObject.toString());
                        //fileWriter.close();
                        //bufferedWriter.write(jsonObject.toString());
                        //bufferedWriter.close();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (bağlanti != null)
                        bağlanti.disconnect();
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

                            String test = campany;

                            if(!ne_ara.equals("") && !nerede_ara.equals(""))
                                if(ne_ara.equals(pozisyon) && nerede_ara.equals(city))
                                {
                                    Model model = new Model();
                                    model.setTitle(pozisyon);
                                    model.setPosition(pozisyon);
                                    model.setCreator(campany);
                                    model.setDate(date);
                                    model.setLink(city);
                                    modelList.add(model);

                                }
                            if(!ne_ara.equals("") && nerede_ara.equals(""))
                                if(ne_ara.equals(pozisyon))
                                {
                                    Model model = new Model();
                                    model.setTitle(pozisyon);
                                    model.setPosition(pozisyon);
                                    model.setCreator(campany);
                                    model.setDate(date);
                                    model.setLink(city);
                                    modelList.add(model);

                                }
                             if (ne_ara.equals("") && !nerede_ara.equals(""))
                                if(nerede_ara.equals(city))
                                {
                                    Model model = new Model();
                                    model.setTitle(pozisyon);
                                    model.setPosition(pozisyon);
                                    model.setCreator(campany);
                                    model.setDate(date);
                                    model.setLink(city);
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
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            progressDialog.setMessage(values[0]);//güncelleniyor
        }
    }
}
