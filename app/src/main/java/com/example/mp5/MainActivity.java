package com.example.mp5;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkButtonBuilder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView succ = findViewById(R.id.succ);
        TextView fail = findViewById(R.id.fail);
        TextView goal = findViewById(R.id.goal);
        TextView succs = findViewById(R.id.editText);
        TextView fails = findViewById(R.id.editText2);
        Object oks = findViewById(R.id.action_settings);
        TextView email = findViewById(R.id.editText6);

        TextView goals = findViewById(R.id.editText3);
        FloatingActionButton fab = findViewById(R.id.fab);
        SparkButton ok = findViewById(R.id.spark_button);
        //FloatingActionButton ok = findViewById(R.id.ok);
        email.setText(getUsername(getApplicationContext()));
        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        //mActionBarToolbar.setVisibility(View.GONE);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Simple Mindfulness Check");
        ok.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {

                if (email.getVisibility() == View.VISIBLE) {
                    if (email.getText().toString().equals("") || !(email.getText().toString().contains("@"))) {
                        Snackbar.make(v, "Set valid email", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                       setUsername(getApplicationContext(), email.getText().toString());
                        Snackbar.make(v, "Email set", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        ok.playAnimation();
                    }
                }
                if (email.getVisibility() == View.INVISIBLE) {
                    email.setVisibility(View.VISIBLE);
                }
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       if ((getUsername(getApplicationContext()) == "") || !(email.getText().toString().contains("@"))) {
                                           Snackbar.make(view, "Set valid email first", Snackbar.LENGTH_LONG)
                                                   .setAction("Action", null).show();
                                       } else {
                                           String emailsend = email.getText().toString();
                                           String emailbody = succ.getText().toString() + " " + succs.getText().toString() + " " +
                                                   System.getProperty("line.separator") +
                                                   fail.getText().toString() + " " + fails.getText().toString() +
                                                   System.getProperty("line.separator") +
                                                   goal.getText().toString() + " " + goals.getText().toString();

                                           String emailsubject = "Simple Mindfulness Check-in";

                                           // define Intent object
                                           // with action attribute as ACTION_SEND
                                           Intent intent = new Intent(Intent.ACTION_SEND);

                                           // add three fiels to intent using putExtra function
                                           intent.putExtra(Intent.EXTRA_EMAIL,
                                                   new String[]{emailsend});
                                           intent.putExtra(Intent.EXTRA_SUBJECT, emailsubject);
                                           intent.putExtra(Intent.EXTRA_TEXT, emailbody);

                                           // set type of intent
                                           intent.setType("message/rfc822");

                                           // startActivity with intent with chooser
                                           // as Email client using createChooser function
                                           startActivity(
                                                   Intent.createChooser(intent,
                                                           "Choose an Email client :"));
                                       }
                                   }


        });
    }
    public static void setUsername(Context context, String username) {
        SharedPreferences prefs = context.getSharedPreferences("mp5", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", username);
        editor.commit();
    }
    public static String getUsername(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("mp5", 0);
        return prefs.getString("username", "");

    }
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
