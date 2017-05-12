package edu.pitt.cs.cs1635.studybuddies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by nick on 3/21/17.
 */


public class AnswerQuestion extends AppCompatActivity implements View.OnClickListener{

    private boolean answered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question);


        final EditText search = (EditText) findViewById(R.id.search);

        Intent i = getIntent();
        GroupQuestion question = (GroupQuestion) i.getSerializableExtra("sampleObject");
        answered = (boolean) i.getSerializableExtra("answeredAlready");
        TextView myAwesomeTextView = (TextView)findViewById(R.id.TEXT_STATUS_ID);
        myAwesomeTextView.setText(question.getQuestion() + "?");
        final TextView myAwesomeTextView2 = (TextView)findViewById(R.id.TEXT_STATUS_ID2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button button = (Button)findViewById(R.id.button2);

        button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        //System.out.println("HERE");
                        submit();

                    }
                }
        );

        myAwesomeTextView2.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            submit();
                            return true;
                        }
                        if (actionId == EditorInfo.IME_NULL
                                && event.getAction() == KeyEvent.ACTION_DOWN) {
                            submit();
                            return true;
                        }
                        return false;
                    }
                }
        );



    }

    //Pushing the answer back to the GroupQuestionListActivity
    public void submit(){
        Intent i;
        final EditText edit =  (EditText) findViewById(R.id.TEXT_STATUS_ID2);
        //System.out.println(edit.getText());
        if(answered){
            i = new Intent(this, ViewAnswer.class);
        }else {
            i = new Intent(this, GroupQuestionListActivity.class);
        }
        i.putExtra("sampleObject",  edit.getText().toString());
        setResult(RESULT_OK, i);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navbar, menu);
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
        if (id == R.id.fav_button) {
            Toast.makeText(this, "Favorites Not Available from this screen", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.home_button) {
            Intent in = new Intent(this, MainActivity.class);
            startActivity(in);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        //System.out.println("HERE");

        if (v.getId() == R.id.button2) {
            //System.out.println("HERE");
            final EditText edit =  (EditText) findViewById(R.id.TEXT_STATUS_ID2);
            Intent i = new Intent(this, GroupQuestionListActivity.class);

            i.putExtra("sampleObject", edit.getText());
            setResult(RESULT_OK, i);
            finish();


        }
    }
}
