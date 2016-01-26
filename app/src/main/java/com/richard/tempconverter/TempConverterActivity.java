package com.richard.tempconverter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.text.NumberFormat;

public class TempConverterActivity extends AppCompatActivity implements OnEditorActionListener {

    private EditText fahrenheitEditText;
    private TextView celsiusTextView;

    private String fahrenheitString;

    private SharedPreferences savedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_converter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //get references to the widgets
        fahrenheitEditText = (EditText) findViewById(R.id.fahrenheitEditText);
        celsiusTextView = (TextView) findViewById(R.id.celsiusTextView);

        //set the listener
        fahrenheitEditText.setOnEditorActionListener(this);

        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
    }



    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        fahrenheitString = fahrenheitEditText.getText().toString();
        //^put it here so that it isnt run again when calculateAndDisplay() is called on resume

        if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
            calculateAndDisplay();
        }

        //hides soft keyboard
        return false;
    }

    public void calculateAndDisplay() {

        float fahrenheit;
        if (fahrenheitString == "") {
            fahrenheit = 32;
        }
        else {
            fahrenheit = Float.parseFloat(fahrenheitString);
        }

        //calculate celsius
        float celsius = (fahrenheit - 32) * 5 / 9;

        //set celsius text
        NumberFormat degrees = NumberFormat.getInstance();
        celsiusTextView.setText(degrees.format(celsius));

    }

    @Override
    protected void onPause() {
        Editor editor = savedValues.edit();
        editor.putString("fahrenheitString", fahrenheitString);
        editor.commit();

        super.onPause();
    }

    @Override
    protected void onResume() {
        fahrenheitString = savedValues.getString("fahrenheitString", "");
        fahrenheitEditText.setText(fahrenheitString);
        calculateAndDisplay(); //displays celsius text by calculating it again with the saved fahrenheit string

        super.onResume();
    }


}
