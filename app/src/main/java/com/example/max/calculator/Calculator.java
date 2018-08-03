package com.example.max.calculator;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class Calculator extends AppCompatActivity {
    private EditText output = null; //this is a reference to the output for numbers

    //This will tell us if the next key pressed is legal
    private enum Expected {digit, anything};
    Expected nextExpectedEntry = Expected.digit;

    private StringBuilder buffer = new StringBuilder(""); //buffer will hold the digits for the next number until it is time to parse

    //as people enter values into the
    private ArrayList<Double> numbers = new ArrayList<>(15); //Start at 15 so ArrayList does not have to resize unnecessarily
    private ArrayList<Character> symbols = new ArrayList<>(15);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
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

        output = findViewById(R.id.editText); //find the output and set up the reference

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calculator, menu);
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

    public void buttonOnClick(View view) {
        Character nextInput = (((Button)view).getText().charAt(0));

        if ((nextExpectedEntry == Expected.anything || nextExpectedEntry == Expected.digit) && view.getTag().toString().equals("number")) {
            buffer.append(nextInput); //next expected button press is a number, so add it to buffer
            nextExpectedEntry = Expected.anything; //either a number or symbol can come after a number
            output.setText(output.getText() + nextInput.toString()); //update the output so people can see what they've pressed
        } else if (nextExpectedEntry == Expected.anything && view.getTag().toString().equals("symbol")) {
            numbers.add(Double.parseDouble(buffer.toString()));//this number is complete, add it to the arraylist of numbers
            buffer.setLength(0); //reset the buffer
            symbols.add(nextInput);
            nextExpectedEntry = Expected.digit;
            output.setText(output.getText() + nextInput.toString());//update the output so people can see what they've pressed
        } else if(nextExpectedEntry == Expected.digit && view.getTag().toString().equals("symbol")){ //this means that two symbols have been pressed in a row... not allowed!
            Toast.makeText(getApplicationContext(),"Can't enter another operator before pressing a digit",Toast.LENGTH_LONG).show();
        }
    }
    public void Equals(View view){

    }
}
