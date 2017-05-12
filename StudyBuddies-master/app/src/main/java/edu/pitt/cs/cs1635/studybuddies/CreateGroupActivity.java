package edu.pitt.cs.cs1635.studybuddies;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Jason on 3/14/2017.
 */

public class CreateGroupActivity extends AppCompatActivity {
    public static User currentUser = new User("user");

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

        setContentView(R.layout.activity_create_group);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle b = getIntent().getExtras();

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
        EditText groupName   = (EditText)findViewById(R.id.group_name);
        EditText profName   = (EditText)findViewById(R.id.professor_name);
        String groupString = groupName.getText().toString();
        String profString = profName.getText().toString();
        if(groupString.length() < 5){
            new AlertDialog.Builder(this,R.style.AppCompatAlertDialogStyle)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setMessage("Group name must be 5 characters or longer")
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }

                    })
                    .show();
        }
        else if(profString.length() < 5){
            new AlertDialog.Builder(this,R.style.AppCompatAlertDialogStyle)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setMessage("Professor's name must be 5 characters or longer")
                    .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }

                    })
                    .show();
        }
        else {
            new AlertDialog.Builder(this,R.style.AppCompatAlertDialogStyle)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setMessage("Are you sure you want to create this group?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            createGroup();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }
    public void cancelAction(View v){
        new AlertDialog.Builder(this,R.style.AppCompatAlertDialogStyle)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Are you sure you want to cancel create a group?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
    public void createGroup(){
        EditText groupName   = (EditText)findViewById(R.id.group_name);
        EditText professorsName   = (EditText)findViewById(R.id.professor_name);

        Group g = new Group(groupName.getText().toString());
        g.setProfessor(professorsName.getText().toString());
        Intent data = new Intent();
        data.putExtra("newGroup", g);
        setResult(1,data);
        finish();
    }
}
