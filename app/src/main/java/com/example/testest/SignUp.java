package com.example.testest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.URL;
import java.util.ArrayList;

public class SignUp extends AppCompatActivity {
    RadioGroup whoRadioGroup;
    RadioButton trainerCheckBox;
    RadioButton traineeCheckBox;
    com.google.android.material.textfield.TextInputLayout idTextView;
    com.google.android.material.textfield.TextInputLayout pwTextView;
    com.google.android.material.textfield.TextInputLayout nameTextView;
    com.google.android.material.textfield.TextInputLayout ageTextView;
    com.google.android.material.textfield.TextInputLayout phoneTextView;
    Button check_button;
    Spinner genderSpinner;
    Spinner weightSpinner;
    Spinner heightSpinner;
    com.google.android.material.textfield.TextInputLayout purposeTextView;
    Button doneButton;
    ArrayList<String> genderList = new ArrayList<String>();
    ArrayList<String> weightList = new ArrayList<String>();
    ArrayList<String> heightList = new ArrayList<String>();
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        whoRadioGroup = findViewById(R.id.radioGroup);
        trainerCheckBox = findViewById(R.id.trainerCheckBox);
        traineeCheckBox = findViewById(R.id.traineeCheckBox);
        idTextView = findViewById(R.id.idEditText);
        pwTextView = findViewById(R.id.pwEditText);
        nameTextView = findViewById(R.id.nameEditText);
        ageTextView = findViewById(R.id.ageEditText);
        genderSpinner = findViewById(R.id.genderSpinner);
        phoneTextView = findViewById(R.id.contactEditText);
        weightSpinner = findViewById(R.id.weightSpinner);
        heightSpinner = findViewById(R.id.heightSpinner);
        purposeTextView = findViewById(R.id.purposeEditText);
        doneButton = findViewById(R.id.doneButton);
        check_button = findViewById(R.id.id_check);
        trainerCheckBox.setChecked(true);

        // spinner (성별, 몸무게, 키 채우기)
        genderList.add("성별");
        genderList.add("남자");
        genderList.add("여자");
        weightList.add("몸무게");
        heightList.add("키");

        for (int i = 1; i <= 200; i++) {
            weightList.add(Integer.toString(i));
            heightList.add(Integer.toString(i));
        }

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(getApplicationContext(), com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, genderList);
        ArrayAdapter<String> weightAdapter = new ArrayAdapter<>(getApplicationContext(), com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, weightList);
        ArrayAdapter<String> heightAdapter = new ArrayAdapter<>(getApplicationContext(), com.airbnb.lottie.R.layout.support_simple_spinner_dropdown_item, heightList);
        genderSpinner.setAdapter(genderAdapter);
        weightSpinner.setAdapter(weightAdapter);
        heightSpinner.setAdapter(heightAdapter);
        genderSpinner.setSelection(0);
        weightSpinner.setSelection(0);
        heightSpinner.setSelection(0);


        check_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // custom edittext라 getEditText().getText(), 일반적인 edittext: getText()
                if (idTextView.getEditText().getText().toString().equals("") || idTextView.getEditText().getText().toString() == null){
                    Toast.makeText(getApplicationContext(), "Fill the blank!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    // Instantiate the RequestQueue.
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url ="http://172.10.18.125:80/checkid/" + idTextView.getEditText().toString();

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // Display the first 500 characters of the response string.
                                    if (response.equals("ok")){
                                        Log.d("check", "gotok");
                                        Toast.makeText(getApplicationContext(), "사용하실 수 있는 아이디 입니다!", Toast.LENGTH_SHORT).show();
                                        idTextView.setEnabled(false);
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "이미 아이디가 존재합니다!", Toast.LENGTH_SHORT).show();
                                        Log.d("check", "" + response);
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("check", "got error");
                        }
                    });

                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);

                }
        }
        });
        // 로그인 액티비티로 되돌아가기
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton selectedButton_ = (RadioButton) findViewById(whoRadioGroup.getCheckedRadioButtonId());

                if (selectedButton_.getText().toString().equals("TRAINEE")) {
                    if (idTextView.getEditText().getText().toString().length() == 0 ||
                            pwTextView.getEditText().getText().toString().length() == 0 ||
                            nameTextView.getEditText().getText().toString().length() == 0 ||
                            ageTextView.getEditText().getText().toString().length() == 0 ||
                            phoneTextView.getEditText().getText().toString().length() == 0 ||
                            genderSpinner.getSelectedItem().toString() == "성별" ||
                            weightSpinner.getSelectedItem().toString() == "몸무게" ||
                            heightSpinner.getSelectedItem().toString() == "키" ||
                            purposeTextView.getEditText().getText().toString().length() == 0) {
                        Toast.makeText(getApplicationContext(), "Fill the blank!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    intent = getIntent();
                    RadioButton selectedButton = (RadioButton) findViewById(whoRadioGroup.getCheckedRadioButtonId());
                    intent.putExtra("who", selectedButton.getText().toString());
                    intent.putExtra("id", idTextView.getEditText().getText().toString());
                    intent.putExtra("pw", pwTextView.getEditText().getText().toString());
                    intent.putExtra("name", nameTextView.getEditText().getText().toString());
                    intent.putExtra("age", ageTextView.getEditText().getText().toString());
                    intent.putExtra("phone", phoneTextView.getEditText().toString());
                    intent.putExtra("gender", genderSpinner.getSelectedItem().toString());
                    intent.putExtra("weight", weightSpinner.getSelectedItem().toString());
                    intent.putExtra("height", heightSpinner.getSelectedItem().toString());
                    intent.putExtra("purpose", purposeTextView.getEditText().toString());
                } else {
                    if (idTextView.getEditText().getText().toString().length() == 0 ||
                            pwTextView.getEditText().getText().toString().length() == 0 ||
                            nameTextView.getEditText().getText().toString().length() == 0 ||
                            ageTextView.getEditText().getText().toString().length() == 0 ||
                            phoneTextView.getEditText().getText().toString().length() == 0 ||
                            genderSpinner.getSelectedItem().toString() == "성별") {
                        Toast.makeText(getApplicationContext(), "Fill the blank!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    intent = getIntent();
                    RadioButton selectedButton = (RadioButton) findViewById(whoRadioGroup.getCheckedRadioButtonId());
                    intent.putExtra("who", selectedButton.getText().toString());
                    intent.putExtra("id", idTextView.getEditText().getText().toString());
                    intent.putExtra("pw", pwTextView.getEditText().getText().toString());
                    intent.putExtra("name", nameTextView.getEditText().getText().toString());
                    intent.putExtra("age", ageTextView.getEditText().getText().toString());
//                    intent.putExtra("weight", weightSpinner.getSelectedItem().toString());
                    intent.putExtra("phone", phoneTextView.getEditText().toString());
                    intent.putExtra("gender", genderSpinner.getSelectedItem().toString());
                }

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.trainerCheckBox:
                if (checked) {
                    Toast.makeText(getApplicationContext(), "Hello, Trainer!", Toast.LENGTH_SHORT).show();
                    weightSpinner.setVisibility(View.GONE);
                    heightSpinner.setVisibility(View.GONE);
                    purposeTextView.setVisibility(View.GONE);
                }
                break;

            case R.id.traineeCheckBox:
                if (checked) {
                    Toast.makeText(getApplicationContext(), "Hello, Trainee!", Toast.LENGTH_SHORT).show();
                    weightSpinner.setVisibility(View.VISIBLE);
                    heightSpinner.setVisibility(View.VISIBLE);
                    purposeTextView.setVisibility(View.VISIBLE);
                }
                break;

        }
    }
}