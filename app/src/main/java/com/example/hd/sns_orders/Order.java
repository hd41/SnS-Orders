package com.example.hd.sns_orders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Order extends AppCompatActivity {

    TextView tv1,tv2,tv3,tv4,tv5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        getSupportActionBar();

        Intent i2=getIntent();
        Bundle b2=i2.getBundleExtra("order");
        String pid=b2.getString("pid");
        String name=b2.getString("name");
        String ord=b2.getString("ord");
        String add=b2.getString("add");
        String phone=b2.getString("phone");
        final Integer pos=b2.getInt("position");
        String time=b2.getString("time");

        tv1=(TextView)findViewById(R.id.tv1);
        tv2=(TextView)findViewById(R.id.tv2);
        tv3=(TextView)findViewById(R.id.tv3);
        tv4=(TextView)findViewById(R.id.tv4);
        tv5=(TextView)findViewById(R.id.tv5);

        tv1.setText(pid);
        tv2.setText(name);
        tv3.setText(ord);
        tv4.setText(add);
        tv5.setText(phone);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("babe(fab)", "" + pos);
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",pos);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}