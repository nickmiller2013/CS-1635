package edu.pitt.cs.cs1635.studybuddies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.widget.RatingBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.view.LayoutInflater;
import android.content.DialogInterface;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by nick on 3/26/17.
 */
public class ViewAnswer extends AppCompatActivity implements View.OnClickListener{
    private GroupQuestion question;
    private ArrayList<GroupAnswer> updatedAnswers;
    protected static ArrayList<GroupQuestion> currQuestionList = GroupQuestionListActivity.currQuestionList;//way to access static variable using dot operator.
    private int index = -1;
    private RatingBar ratingBar;
    private float rating;
    private int cur_index = 0;
    /*
    Sets the question and the answer to view on the view question page.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_answers);


        final EditText search = (EditText) findViewById(R.id.search);

        Intent i = getIntent();
        question = (GroupQuestion) i.getSerializableExtra("sampleObject");
        this.index = (int) i.getSerializableExtra("index");
        TextView myAwesomeTextView = (TextView)findViewById(R.id.TEXT_STATUS_ID);
        myAwesomeTextView.setText(question.getQuestion() + "?");
        TextView myAwesomeTextView2 = (TextView)findViewById(R.id.TEXT_STATUS_ID2);
        updatedAnswers = question.getAnswerList();
        updateAvailableAnswers();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button button = (Button)findViewById(R.id.add_answer);
        ButtonsActivate();
        button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        ////System.out.println("HERE");
                        addAnswer();

                    }
                }
        );

    }

    /**
     * create filtered answer list and send to update method
     */
    public void filterGroupQuestions(){

        ArrayList<GroupAnswer> temp = new ArrayList<>();
        for(GroupAnswer answer:updatedAnswers){

                int count = 0;
                for(GroupAnswer temp_answer: updatedAnswers){
                    //System.out.println(answer.getRank() + " and " + temp_answer.getRank());
                    if(answer.getRank() > temp_answer.getRank() && !(answer.getAnswer().equals(temp_answer.getAnswer()))){
                        temp.add(count, answer);
                        break;
                    }else if(temp.size() == count){
                        temp.add(count, answer);
                        break;
                    }else if(answer.getRank() == temp_answer.getRank()) {
                        temp.add(count+1, answer);
                        break;
                    }else{
                        count++;
                        continue;
                    }

                }
        }
        //System.out.println("Right here:  " + temp.size());
        this.updatedAnswers = temp;

    }


    private void addAnswer() {
        Intent i = new Intent(this, AnswerQuestion.class);
        i.putExtra("sampleObject", question);
        i.putExtra("answeredAlready", true);
        startActivityForResult(i, 1);
    }
    /**
     * Make the dummy answers buttons clickable
     */
    private void ButtonsActivate(){
        filterGroupQuestions();

        LinearLayout QuestionList = (LinearLayout) findViewById(R.id.answer_list);

        for(Object obj: QuestionList.getTouchables()){
            if (obj instanceof Button) {
                Button tempButton = (Button) obj;
                tempButton.setClickable(true);
                tempButton.setFocusable(true);
                tempButton.setOnClickListener(this);
                tempButton.setGravity(Gravity.LEFT);
                tempButton.setHeight(30);
                tempButton.setTextSize(20);

            }
        }
    }
    /**
     * Update UI to reflect filtered questions
     */
    public void updateAvailableAnswers(){
        filterGroupQuestions();
        updatedAnswers = this.updatedAnswers;
        LinearLayout qList = (LinearLayout) findViewById(R.id.answer_list);

        qList.removeAllViews();

        for(int i = 0; i < updatedAnswers.size(); i++){

            Button tempButton = new Button(this);
            GroupAnswer tempAnswer = updatedAnswers.get(i);
            String toAdd = String.format(("%.1f\u2605 %s"), tempAnswer.getRank(), tempAnswer.getAnswer());
            tempButton.setText(toAdd);
            tempButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            qList.addView(tempButton);

        }
        ButtonsActivate();

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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                String stredittext=data.getStringExtra("sampleObject");
                //System.out.println("Right here: " + stredittext);
                question.addAddAnswer(new GroupAnswer(stredittext));
                question.setAnswered(true);
                updateAnswered();

            }
        }
        ButtonsActivate();
    }

    public void updateAnswered(){
        filterGroupQuestions();
        int k = 0;
        Iterator<GroupAnswer> iter = updatedAnswers.iterator();

        while (iter.hasNext()) {
            GroupAnswer tempQuest = iter.next();

            if (tempQuest.getAnswer().equals(question.getQuestion()))
                iter.remove();

        }

        updatedAnswers = question.getAnswerList();
        currQuestionList.set(index,question);

        updateAvailableAnswers();
    }

    public void setRating(){
        rating = ratingBar.getRating();
        String ratingStat = "The rating is " + rating;
        Toast.makeText(this, ratingStat, Toast.LENGTH_SHORT).show();
        GroupAnswer answer = updatedAnswers.get(cur_index);
        answer.setRankedBy(MainActivity.currentUser.getName());
        answer.addRank(rating);
        //System.out.println(updatedAnswers.get(cur_index).getAnswer());
        //System.out.println("RIGHT HERHEHERHERHER! " + answer.getRank());

        this.updatedAnswers.set(cur_index, answer);

        updateAnswered();


    }


    @Override
    public void onClick(View v) {
        ArrayList<GroupAnswer> a_list = this.updatedAnswers;
        int count = 0;
        //System.out.println("Here");

        for(GroupAnswer a : a_list){
            //System.out.println(count);

            if ( v instanceof Button) {
                //System.out.println("Here");

                String candidateBtnText = a.getAnswer().toLowerCase().trim();
                String viewText = ((Button) v).getText().toString().toLowerCase().trim();
                viewText = viewText.split("\u2605 ")[1];

                if(candidateBtnText.equals(viewText)){
                    if(!(a.isRankedBy(MainActivity.currentUser.getName()))){
                        //System.out.println("Here");
                        /**
                         * NAVIGATE TO A QUESTION PAGE TO BE IMPLEMENTED
                         */
                        final Dialog rankDialog = new Dialog(v.getContext(), R.style.FullHeightDialog);
                        rankDialog.setContentView(R.layout.rank_dialog);
                        rankDialog.setCancelable(true);
                        ratingBar = (RatingBar) rankDialog.findViewById(R.id.dialog_ratingbar);
                        ratingBar.setRating(3);

                        TextView text = (TextView) rankDialog.findViewById(R.id.rank_dialog_text1);
                        text.setText(a.getAnswer());
                        cur_index = count;
                        Button updateButton = (Button) rankDialog.findViewById(R.id.rank_dialog_button);
                        updateButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                setRating();
                                rankDialog.dismiss();
                            }
                        });
                        //now that the dialog is set up, it's time to show it
                        rankDialog.show();
                        break;
                    }else{
                        LayoutInflater inflater= LayoutInflater.from(this);
                        View view=inflater.inflate(R.layout.display_answer, null);

                        TextView textview=(TextView)view.findViewById(R.id.textmsg);
                        textview.setText(a.getAnswer());
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                        alertDialog.setTitle("Already Ranked");
                        alertDialog.setView(view);
                        alertDialog.setPositiveButton("OK", null);
                        AlertDialog alert = alertDialog.create();
                        alert.show();
                        Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
                        pbutton.setBackgroundColor(Color.parseColor("#D4AF37"));
                        alert.getWindow().setBackgroundDrawableResource(android.R.color.black);
                    }
                }


            }
            count++;

        }
    }
}

