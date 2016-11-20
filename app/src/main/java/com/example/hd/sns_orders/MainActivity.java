package com.example.hd.sns_orders;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

        ProgressDialog pDialog;
        ArrayList<Integer> tick_array=new ArrayList<Integer>();
        ArrayList<String> pid_array = new ArrayList<String>();
        ArrayList<String> name_array = new ArrayList<String>();
        ArrayList<String> order_array = new ArrayList<String>();
        ArrayList<String> address_array = new ArrayList<String>();
        ArrayList<String> phone_array = new ArrayList<String>();
        ArrayList<String> time_array = new ArrayList<String>();

        ListView list;
        BaseAdapter2 adapter;


    public void alertDialogShow(Context context, String message)
    {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setCancelable(true);
        alertDialog.setTitle("Alert!!");
        alertDialog.setMessage(message);
        alertDialog.setButton("Yes", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                Intent in2=new Intent(MainActivity.this,AdminLoginCredentials.class);
                startActivity(in2);
            }
        });
        alertDialog.show();
    }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.main_layout);
            list = (ListView) findViewById(R.id.listView);
            new TheTask().execute();

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent in = new Intent(MainActivity.this,Order.class);
                    Bundle bu=new Bundle();
                    bu.putString("pid",pid_array.get(position));
                    bu.putString("name",name_array.get(position));
                    bu.putString("ord",order_array.get(position));
                    bu.putString("add",address_array.get(position));
                    bu.putString("phone",phone_array.get(position));
                    bu.putString("time",time_array.get(position));
                    bu.putInt("position",position);
                    in.putExtra("order",bu);
                    startActivityForResult(in,100);
                }
            });

        }

        class TheTask extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(MainActivity.this);
                pDialog.setMessage("Fetching orders ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            @Override
            protected String doInBackground(Void... params) {
                String str = "hd ";
                try {

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost(
                            "http://spicynsiizzling.comli.com/all_menu.php");
                    HttpResponse response = httpclient.execute(httppost);

                    str = EntityUtils.toString(response.getEntity());
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                return str;

            }

            @Override
            protected void onPostExecute(String result1) {

                String[] trial=result1.split("<");
                String result=trial[0];

                try {

                    JSONObject object=new JSONObject(result);
                    JSONArray new_array = object.getJSONArray("menu");

                    for (int i = 0, count = new_array.length(); i < count; i++) {
                        try {
                            JSONObject jsonObject = new_array.getJSONObject(i);
                            pid_array.add(jsonObject.getString("Pid"));
                            name_array.add(jsonObject.getString("Name"));
                            order_array.add(jsonObject.getString("Order"));
                            address_array.add(jsonObject.getString("Address"));
                            phone_array.add(jsonObject.getString("Phone No."));
                            time_array.add(jsonObject.getString("Date Time"));
                            tick_array.add(0);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    adapter = new BaseAdapter2(MainActivity.this, name_array, phone_array,tick_array);
                    adapter.notifyDataSetChanged();
                    list.setAdapter(adapter);

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    // tv.setText("error2");
                }

                pDialog.dismiss();

            }
        }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 100) {
            if(resultCode == Activity.RESULT_OK){
                Integer result=data.getIntExtra("result",0);
                if(tick_array.get(result)==0)
                tick_array.set(result, 1);
                else tick_array.set(result, 0);
                //View namebar = (ListView)findViewById(R.id.listView);
                //((ViewGroup) namebar.getParent()).removeView(namebar);
                adapter.notifyDataSetChanged();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.delete) {
            alertDialogShow(MainActivity.this, "Sure to delete all items?");
            return true;
        }
        if(id==R.id.refresh){
            startActivity(new Intent(MainActivity.this,MainActivity.class));
        }
        if(id==R.id.exit){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
