package edu.pitt.cs.cs1635.nam99.postagecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;


public class MainActivity extends Activity {
    private RadioGroup radioGroup;
    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Initialize Radio Group and attach click handler */
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.clearCheck();

        editText = (EditText) findViewById(R.id.editText2);
        editText.addTextChangedListener(textWatcher);

        checkFieldsForEmptyValues();
    }

    public void onClear(View v) {
        /* Clears all selected radio buttons to default */
        radioGroup.clearCheck();
        editText.setText("");
        TextView tv1 = (TextView)findViewById(R.id.textView2);
        tv1.setText("");

    }

    public void onRadioButtonClicked(View v){
        checkFieldsForEmptyValues();
    }


    //TextWatcher
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3)
        {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            checkFieldsForEmptyValues();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public void onSubmit(View v) {
        RadioButton rb = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
        Toast.makeText(MainActivity.this, rb.getText(), Toast.LENGTH_SHORT).show();
        double weight = priceGrab(rb.getText().toString(), editText.getText().toString());
        TextView tv1 = (TextView)findViewById(R.id.textView2);
        InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        tv1.setText(getString(R.string.forA) + rb.getText().toString() +getString(R.string.is) + String.format( "%.2f", weight ));
    }

    public double priceGrab(String option, String w8){
        double weight = Double.parseDouble(w8);
        double original=0;
        double addition=0;
        double multi;
        switch(option){

            case "Letter":
                if(weight > 3.5){
                    original = 1.4;
                }else{
                    original = .46;
                }
                addition = 0;
                multi = .21;
                if(weight > 12){
                    addition = 10 * multi;
                }else if(weight > 11){
                    addition = 9 * multi;
                }else if(weight > 10){
                    addition = 8 * multi;
                }else if(weight > 9){
                    addition = 7 * multi;
                }else if(weight > 8) {
                    addition = 6 * multi;
                }else if(weight > 7){
                    addition = 5 * multi;
                }else if(weight > 6){
                    addition = 4 * multi;
                }else if(weight > 5){
                    addition = 3 * multi;
                }else if(weight > 4){
                    addition = 2 * multi;
                }else if(weight > 3.5){
                    addition = multi;
                }else if(weight > 3){
                    addition = 3 * multi;
                }else if(weight > 2){
                    addition = 2 * multi;
                }else if(weight > 1){
                    addition = multi;
                }

                break;
            case "Large Envelope":
                addition = 0;
                original = .98;
                multi = .21;
                if(weight > 12){
                    addition = 12 * multi;
                }else if(weight > 11){
                    addition = 11 * multi;
                }else if(weight > 10){
                    addition = 10 * multi;
                }else if(weight > 9){
                    addition = 9 * multi;
                }else if(weight > 8) {
                    addition = 8 * multi;
                }else if(weight > 7){
                    addition = 7 * multi;
                }else if(weight > 6){
                    addition = 6 * multi;
                }else if(weight > 5){
                    addition = 5 * multi;
                }else if(weight > 4){
                    addition = 4 * multi;
                }else if(weight > 3){
                    addition = 3 * multi;
                }else if(weight > 2){
                    addition = 2 * multi;
                }else if(weight > 1){
                    addition = multi;
                }

                break;
            case "Package":

                addition = 0;
                original = 2.67;
                multi = .18;
                if(weight > 12){
                    addition = 9 * multi;
                }else if(weight > 11){
                    addition = 8 * multi;
                }else if(weight > 10){
                    addition = 7 * multi;
                }else if(weight > 9){
                    addition = 6 * multi;
                }else if(weight > 8) {
                    addition = 5 * multi;
                }else if(weight > 7){
                    addition = 4 * multi;
                }else if(weight > 6){
                    addition = 3 * multi;
                }else if(weight > 5){
                    addition = 2 * multi;
                }else if(weight > 4){
                    addition = 1 * multi;
                }


                break;
        }
        return original + addition;

    }
    private  void checkFieldsForEmptyValues(){
        Button b = (Button) findViewById(R.id.submitBtn);

        String s1 = editText.getText().toString();

        if(s1.equals(""))
        {
            b.setEnabled(false);
        }

        else if(radioGroup.getCheckedRadioButtonId() == -1){
            b.setEnabled(false);
        }

        else if(Double.parseDouble(s1) > 13){
            b.setEnabled(false);
            Toast.makeText(MainActivity.this, getString(R.string.high_13), Toast.LENGTH_SHORT).show();

        }

        else if(Double.parseDouble(s1) <= 0){
            b.setEnabled(false);
            Toast.makeText(MainActivity.this, getString(R.string.non_zero), Toast.LENGTH_SHORT).show();

        }

        else if(!s1.equals("") && radioGroup.getCheckedRadioButtonId() != -1){
            b.setEnabled(true);
        }

    }

}