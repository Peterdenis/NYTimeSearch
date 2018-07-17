package com.example.peterson.newyorktimesearch.activities;

import java.util.Calendar;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.peterson.newyorktimesearch.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

public class Settings extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText edtDatePicker;
    TextView txtView;
    int mDay, mMonth, mYear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        edtDatePicker = (EditText) findViewById(R.id.edtDatePicker);
        txtView = (TextView) findViewById(R.id.textView);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        CheckBox checkFashion = (CheckBox) findViewById(R.id.checkBox2);
        CheckBox checkSports = (CheckBox) findViewById(R.id.checkBox3);
        CheckBox checkArts = (CheckBox) findViewById(R.id.checkBox);

        // Check if checkbox is checked
//        boolean isCheckedFashion = checkFashion.isChecked();
//        boolean isCheckedSports = checkSports.isChecked();
//        boolean isCheckedArts = checkArts.isChecked();
//        checkFashion.setChecked(true);
//        checkSports.setChecked(true);
//        checkArts.setChecked(true);



        txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get Current date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);



                DatePickerDialog datePickerDialog = new DatePickerDialog(Settings.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edtDatePicker.setText(dayOfMonth+"/"+(month + 1)+"/"+year);
                    }
                },mDay, mMonth, mYear);
                datePickerDialog.show();


            }
        });




        // septup the spinner
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.orders,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        // Defines a listener for every time a checkbox is checked or unchecked
        CompoundButton.OnCheckedChangeListener checkListener = new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch(buttonView.getId()) {
                    case R.id.checkBox:
                        if (isChecked) {
                            Toast.makeText(getApplicationContext(), "Arts is check", Toast.LENGTH_SHORT).show();
                        } else {
                            // Remove the meat
                        }
                        break;
                    case R.id.checkBox2:
                        if (isChecked) {
                            Toast.makeText(getApplicationContext(), "Fashion et Style is check", Toast.LENGTH_SHORT).show();
                        } else {
                            // I'm lactose intolerant
                        }
                        break;
                    case R.id.checkBox3:
                        if (isChecked) {
                            Toast.makeText(getApplicationContext(), "Sports is check", Toast.LENGTH_SHORT).show();
                        } else {
                            // I'm lactose intolerant
                        }
                        break;
                }
            }
        };
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
//        AsyncHttpClient client = new AsyncHttpClient();
//        String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";
//
//        RequestParams params = new RequestParams();
//        params.put("api-key", "823891cb183a4c6093883dbf7f9e6542");
//        params.put("page", 0);
//        params.put("q", );

    }

    public void click(View view) {

    }
}
