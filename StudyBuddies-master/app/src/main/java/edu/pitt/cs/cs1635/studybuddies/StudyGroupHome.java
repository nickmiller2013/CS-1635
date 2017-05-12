package edu.pitt.cs.cs1635.studybuddies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class StudyGroupHome extends AppCompatActivity implements View.OnClickListener{

    private static User user;
    private Button submitButton;
    private TextView studyGroupName;
    private TextView floor;
    private TextView timeRemaining;
    private TextView chat;
    private EditText submitChat;
    private String chatText = " ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_group_home);

        //current = (StudyGroup)inIntent.getSerializableExtra("StudyGroup");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        user = MainActivity.currentUser;
        String namePrint = (String) i.getSerializableExtra("name");
        String floorPrint = (String) i.getSerializableExtra("floor");
        String durationPrint = (String) i.getSerializableExtra("duration");


        //GroupQuestion question = (GroupQuestion) i.getSerializableExtra("sampleObject");

        //set the buttons to correspond to the correct ids
        studyGroupName = (TextView) findViewById(R.id.studyGroupNameHeader);
        floor = (TextView) findViewById(R.id.floor);
        timeRemaining = (TextView) findViewById(R.id.timeRemaining);
        chatText = chatText + user + " has joined " + namePrint;
        chat = (TextView) findViewById(R.id.chat);
        chat.setText(chatText);

        submitChat = (EditText) findViewById(R.id.submitChat);
        submitButton = (Button) findViewById(R.id.submitButton);


        studyGroupName.setText(namePrint);
        floor.setText("Location: " + floorPrint);
        timeRemaining.setText("Time Remaining: " + durationPrint + " hours");

        //set onClick listeners for the buttons
        submitButton.setOnClickListener(StudyGroupHome.this);

        submitChat.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            submitButton.performClick();
                            return true;
                        }
                        if (actionId == EditorInfo.IME_NULL
                                && event.getAction() == KeyEvent.ACTION_DOWN) {
                            submitButton.performClick();
                            return true;
                        }
                        return false;
                    }
                }
        );

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

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
            Intent intent = new Intent(this, FavoritesActivity.class);
            ArrayList<String> favorites = new ArrayList<>();
            for (Group g : user.getFavorites()) {
                favorites.add(g.toString());
            }
            intent.putExtra("user", user);
            intent.putExtra("groups", GroupHome.allGroups);
            intent.putExtra("favorites", favorites);
            startActivity(intent);
            return true;
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
        if (v.getId() == R.id.submitButton) {
            chatText = chatText + "\n " + submitChat.getText();
            chat.setText(chatText);
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            submitChat.setText("");
        }
    }

}
