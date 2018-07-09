package com.example.halim.comhalimbezekisilanlari;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class CustomAdaptor extends BaseAdapter {

    private Context context;
    private List<Model> modelList;
    public CustomAdaptor(Context context, List<Model> modelList) {

        this.context = context;
        this.modelList = modelList;

    }

    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public Object getItem(int position) {
        return modelList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LinearLayout rootView = (LinearLayout) ((Activity) context).
                getLayoutInflater().inflate(R.layout.custom_list, null);

        ImageView resim = (ImageView) rootView.findViewById(R.id.resim);
        TextView date_and_creator = (TextView) rootView.findViewById(R.id.date_and_creator);
        TextView title = (TextView) rootView.findViewById(R.id.title);
        TextView city = (TextView) rootView.findViewById(R.id.city);

        final Model model = modelList.get(i);

        resim.setImageBitmap(model.getResim()); //ilan resmi alınacak
        title.setText(model.getTitle());
        city.setText(model.getCity());
        date_and_creator.setText(model.getDate() + " / " + model.getCreator());


        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linkiAc(model.getLink());
            }
        });

        return rootView;
    }


    private void linkiAc(String link) {
        Intent intent=new Intent(context,Icerik.class);
        intent.putExtra("link",link);
        context.startActivity(intent);
    }

}
