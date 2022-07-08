package com.example.testest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class SignUp extends AppCompatActivity {
    RadioGroup whoRadioGroup;
    RadioButton trainerCheckBox;
    RadioButton traineeCheckBox;
    com.google.android.material.textfield.TextInputLayout nameTextView;
    com.google.android.material.textfield.TextInputLayout ageTextView;
    com.google.android.material.textfield.TextInputLayout phoneTextView;
    Spinner genderSpinner;
    Spinner weightSpinner;
    Spinner heightSpinner;
    com.google.android.material.textfield.TextInputLayout purposeTextView;
    Button doneButton;
    ArrayList<String> genderList = new ArrayList<String>();
    ArrayList<String> weightList = new ArrayList<String>();
    ArrayList<String> heightList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        whoRadioGroup = findViewById(R.id.radioGroup);
        trainerCheckBox = findViewById(R.id.trainerCheckBox);
        traineeCheckBox = findViewById(R.id.traineeCheckBox);
        nameTextView = findViewById(R.id.nameEditText);
        ageTextView = findViewById(R.id.ageEditText);
        genderSpinner = findViewById(R.id.genderSpinner);
        phoneTextView = findViewById(R.id.contactEditText);
        weightSpinner = findViewById(R.id.weightSpinner);
        heightSpinner = findViewById(R.id.heightSpinner);
        purposeTextView = findViewById(R.id.purposeEditText);
        doneButton = findViewById(R.id.doneButton);

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

        // 로그인 액티비티로 되돌아가기
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameTextView.getEditText().toString().length() == 0 ||
                ageTextView.getEditText().toString().length() == 0 ||
                phoneTextView.getEditText().toString().length() == 0 ||
                genderSpinner.getSelectedItem().toString() == "성별" ||
                weightSpinner.getSelectedItem().toString() == "몸무게" ||
                heightSpinner.getSelectedItem().toString() == "키" ||
                purposeTextView.getEditText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Fill the blank!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent();
                RadioButton selectedButton = (RadioButton) findViewById(whoRadioGroup.getCheckedRadioButtonId());
                intent.putExtra("who", selectedButton.getText().toString());
                intent.putExtra("name", nameTextView.getEditText().toString());
                intent.putExtra("age", ageTextView.getEditText().toString());
                intent.putExtra("phone", phoneTextView.getEditText().toString());
                intent.putExtra("gender", genderSpinner.getSelectedItem().toString());
                intent.putExtra("weight", weightSpinner.getSelectedItem().toString());
                intent.putExtra("height", heightSpinner.getSelectedItem().toString());
                intent.putExtra("purpose", purposeTextView.getEditText().toString());
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