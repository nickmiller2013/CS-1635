package edu.pitt.cs.cs1635.studybuddies;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.util.ArrayList;

/**
 * Created by Jason on 3/27/2017.
 */

public class CreateStudyGroupActivity extends AppCompatActivity {

    public static User currentUser = new User("user");
    NumberPicker length;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navbar, menu);
        return true;
    }
    @Override

    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_study_group);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle b = getIntent().getExtras();
        length = (NumberPicker)findViewById(R.id.length);
        length.setMaxValue(8);
        length.setMinValue(1);
        length.setDisplayedValues( new String[] { "1 hour", "2 hours", "3 hours", "4 hours", "5 hours", "6 hours"
                , "7 hours", "8 hours"} );
        length.setValue(1);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fav_button:
                Intent intent = new Intent(this, FavoritesActivity.class);
                ArrayList<String> favorites = new ArrayList<>();
                for(Group g : currentUser.getFavorites()){
                    favorites.add(g.toString());
                }
                intent.putExtra("favorites", favorites);
                startActivity(intent);
                return true;

            case R.id.home_button:
                Intent in = new Intent(this, MainActivity.class);
                startActivity(in);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    public void confirmCreateGroup(View v){
        EditText subject   = (EditText)findViewById(R.id.subject);
        EditText location   = (EditText)findViewById(R.id.location);
        String subjectString = subject.getText().toString();
        String locationString = location.getText().toString();
        if(subjectString.length() < 5){
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                    builder.setMessage("Subject must be 5 characters or longer");
                    builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }

                    });
                    builder.show();
        }
        else if(locationString.length() < 5){
            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
            builder.setIcon(android.R.drawable.ic_dialog_alert);
            builder.setMessage("Location must be 5 characters or longer");
            builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }

            });
            builder.show();
        }
        else {
            new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setMessage("Are you sure you want to create this study group?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            createStudyGroup();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }
    public void cancelAction(View v){
        new AlertDialog.Builder(this,R.style.AppCompatAlertDialogStyle)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Are you sure you want to cancel create a study group?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
    public void createStudyGroup(){
        EditText subject   = (EditText)findViewById(R.id.subject);
        EditText location   = (EditText)findViewById(R.id.location);
        TimePicker time   = (TimePicker)findViewById(R.id.time);
        int hour = time.getHour();
        int minute = time.getMinute();
        int duration = length.getValue();
        String timeString = Integer.toString(hour)+ " " + Integer.toString(minute);
        String durationString = Integer.toString(duration);
        String subjectString = subject.getText().toString();
        String locationString = location.getText().toString();

        StudyGroup g = new StudyGroup(subjectString, locationString,
                timeString, durationString);
        Intent data = new Intent();
        data.putExtra("newStudyGroup", g);
        setResult(1,data);
        finish();
    }

}