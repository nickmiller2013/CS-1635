package edu.pitt.cs.cs1635.studybuddies;

import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Iterator;
/**
 * Created by nick on 3/26/17.
 */

public class AnswerQuestionListActivity extends AppCompatActivity implements View.OnClickListener{
    protected static ArrayList<GroupQuestion> currQuestionList = GroupQuestionListActivity.currQuestionList;//way to access static variable using dot operator.
    private static User user;
    private static Group group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answered_questions);

        //set list of buttons to groups
        updateAvailableQuestions(currQuestionList);

        setDummyQuestionButtons();


        final EditText search = (EditText) findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                filterGroupQuestions(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });


    }

    /**
     * create filtered question list and send to update method
     * @param s filter string
     */
    public void filterGroupQuestions(String s){
        if(s.length()==0){
            updateAvailableQuestions(currQuestionList);
        }
        else {
            ArrayList<GroupQuestion> filtered = new ArrayList<>();
            s = s.toLowerCase();
            for (int i = 0; i < currQuestionList.size(); i++) {
                GroupQuestion temp = currQuestionList.get(i);
                if (temp.getQuestion().toLowerCase().contains(s)) {
                    filtered.add(temp);
                }
            }
            updateAvailableQuestions(filtered);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.fav_button) {
            Intent intent = new Intent(this, FavoritesActivity.class);
            ArrayList<String> favorites = new ArrayList<>();
            for (Group g : user.getFavorites()) {
                favorites.add(g.toString());
            }
            intent.putExtra("favorites", favorites);
            startActivity(intent);
            return true;
        }
        if (id == R.id.home_button){
            Intent in = new Intent(this, MainActivity.class);
            startActivity(in);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * Update UI to reflect filtered questions
     * @param updatedQuestions filtered list of questions
     */
    public void updateAvailableQuestions(ArrayList<GroupQuestion> updatedQuestions){

        LinearLayout qList = (LinearLayout) findViewById(R.id.question_list);

        qList.removeAllViews();

        for(int i = 0; i < updatedQuestions.size(); i++){
            if((updatedQuestions.get(i).isAnswered())) {
                Button tempButton = new Button(this);
                GroupQuestion tempGroupQ = updatedQuestions.get(i);
                tempButton.setText(tempGroupQ.getQuestion());
                tempButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                qList.addView(tempButton);
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navbar, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        System.out.println("Here");
        ArrayList<GroupQuestion> g_list = currQuestionList;
        int count = 0;

        for(GroupQuestion g : g_list){

            if ( v instanceof Button) {

                String candidateBtnText = g.getQuestion().toLowerCase().trim();
                String viewText = ((Button) v).getText().toString().toLowerCase().trim();

                if(candidateBtnText.equals(viewText)){

                    /**
                     * NAVIGATE TO A QUESTION PAGE TO BE IMPLEMENTED
                     */

                    Intent i = new Intent(this, ViewAnswer.class);
                    i.putExtra("sampleObject", g);
                    i.putExtra("index", count);
                    startActivityForResult(i, 1);
                    break;

                }
            }
            count++;
        }
    }



    /**
     * Make the dummy question buttons clickable
     */
    private void setDummyQuestionButtons(){

        LinearLayout QuestionList = (LinearLayout) findViewById(R.id.question_list);

        for(Object obj: QuestionList.getTouchables()){
            if (obj instanceof Button) {
                Button tempButton = (Button) obj;
                tempButton.setClickable(true);
                tempButton.setFocusable(true);
                tempButton.setOnClickListener(this);

            }
        }
    }


    public static void setUser(User currentUser) {
        user = currentUser;
    }

    public static void setQList(ArrayList<GroupQuestion> qList){ currQuestionList = qList;}

    public static void setGroup(Group currentGroup) {
        group = currentGroup;
    }


}
