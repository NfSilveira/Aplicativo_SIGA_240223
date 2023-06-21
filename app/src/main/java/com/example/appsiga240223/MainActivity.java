package com.example.appsiga240223;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.text.TextWatcher;

import java.text.SimpleDateFormat;
import java.util.Date;


class DateMask implements TextWatcher {
    private final String mask = "##/##/####";
    private final EditText editText;

    public DateMask(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        String current = s.toString();
        if (current.length() == 2 || current.length() == 5) {
            current += "/";
            editText.setText(current);
            editText.setSelection(current.length());
        }
    }
}


public class MainActivity extends AppCompatActivity {

    EditText birth_edit_text, enrollment_edit_text;
    Button submit_button;

    String birthDateString;
    String enrollmentDateString;

    Date birthDate, enrollmentDate;

    TextView layout;

    String text = "";
    SimpleDateFormat simpledateformat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        birth_edit_text = findViewById(R.id.birthDate);
        birth_edit_text.addTextChangedListener(new DateMask(birth_edit_text));

        enrollment_edit_text = findViewById(R.id.enrollmentDate);
        enrollment_edit_text.addTextChangedListener(new DateMask(enrollment_edit_text));

        submit_button = findViewById(R.id.button);

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    View toast_layout = getLayoutInflater().inflate(R.layout.toast_layout, null);
                    TextView toastText = toast_layout.findViewById(R.id.toast_text);

                    birthDateString = birth_edit_text.getText().toString();
                    birthDate = simpledateformat.parse(birthDateString);

                    enrollmentDateString = enrollment_edit_text.getText().toString();
                    enrollmentDate = simpledateformat.parse(birthDateString);

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                    double[] school_years_collection = SchoolYearsCalculator.calculate(sdf.format(birthDate), sdf.format(enrollmentDate));
                    String[] school_levels = new String[]{"Infantil", "Fundamental", "Médio", "Superior", "Alumni"};

                    for (int i = 0; i < school_years_collection.length; i++) {
                        text += "Anos na Unidade " + school_levels[i] + ": " + Double.toString(school_years_collection[i]) + "\n";
                    }

                    toastText.setText(text);

                    Toast toast = new Toast(getApplicationContext());
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(toast_layout);
                    toast.show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Ocorreu um erro:" + e, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}