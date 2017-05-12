package edu.pitt.cs.cs1635.studybuddies;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class AddAQuestion extends AppCompatActivity {
    public static User currentUser = new User("user");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_aquestion);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navbar, menu);
        return true;
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

    public void confirmCreateQuestion(View v){
        final EditText question   = (EditText)findViewById(R.id.group_name);
        String groupString = question.getText().toString();
//        if(!groupString.endsWith("?")){
//            question.append("?");
//        }

       // else {
            new AlertDialog.Builder(this,R.style.AppCompatAlertDialogStyle)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setMessage("Are you sure you want to ask this question?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            createQuestion(question);
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        //}
    }
    public void cancelAction(View v){
        new AlertDialog.Builder(this,R.style.AppCompatAlertDialogStyle)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Are you sure you want to cancel asking this question?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
    public void createQuestion(EditText o){
       // EditText question   = (EditText)findViewById(R.id.group_name);
        //EditText professorsName   = (EditText)findViewById(R.id.professor_name);

        GroupQuestion gq = new GroupQuestion();
        gq.setQuestion(o.getText().toString());
       // Group g = new Group(groupName.getText().toString());
       // g.setProfessor(professorsName.getText().toString());
        Intent data = new Intent();
        data.putExtra("newGroup", gq);
        setResult(RESULT_OK,data);
        finish();
    }

}
