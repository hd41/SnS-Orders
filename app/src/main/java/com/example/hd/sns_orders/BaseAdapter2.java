package com.example.hd.sns_orders;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hd on 5/9/16.
 */
public class BaseAdapter2 extends BaseAdapter {

    private Activity activity;
    // private ArrayList&lt;HashMap&lt;String, String&gt;&gt; data;
    private static ArrayList title,notice,tick1;
    private static LayoutInflater inflater = null;

    public BaseAdapter2(Activity a, ArrayList b, ArrayList bod,ArrayList tick) {
        activity = a;
        this.title = b;
        this.notice=bod;
        this.tick1=tick;

        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        return title.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.layout, null);

        TextView title2 = (TextView) vi.findViewById(R.id.row); // title
        String song = title.get(position).toString();
        title2.setText(song);


        TextView title22 = (TextView) vi.findViewById(R.id.row2); // notice
        String song2 = notice.get(position).toString();
        title22.setText(song2);

        ImageView iv=(ImageView) vi.findViewById(R.id.imageView);//image
        int kb = (Integer)tick1.get(position);
        Log.d("babes(o)",""+position+"="+kb);
        if(kb==1){
            iv.setImageResource(R.drawable.tick);
        }
        if(kb==0){
            iv.setImageResource(0);
        }
        return vi;

    }
}