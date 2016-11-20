package com.example.hd.sns_orders;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class AdminLoginCredentials extends AppCompatActivity {

    ProgressDialog pDialog;
    EditText et1,et2;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login_credentials);

        et1=(EditText)findViewById(R.id.editText);
        et2=(EditText)findViewById(R.id.editText2);
        btn=(Button)findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((et1.getText().toString()).equals("Admin") && (et2.getText().toString()).equals("dashinghd"))
                {
                    new Delete().execute();
                }
                else
                    Toast.makeText(getApplicationContext(),"Wrong Id or Password!!",Toast.LENGTH_SHORT).show();
            }
        });

    }

    class Delete extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AdminLoginCredentials.this);
            pDialog.setMessage("Deleting all orders ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            String str = null;
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(
                        "http://spicynsiizzling.comli.com/del_menu.php");
                HttpResponse response = httpclient.execute(httppost);
                str = EntityUtils.toString(response.getEntity());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return str;

        }



        @Override
        protected void onPostExecute(String result) {

            pDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Deleted all Orders.", Toast.LENGTH_LONG).show();
        }
    }

}
