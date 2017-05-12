package edu.pitt.cs.cs1635.studybuddies;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.Window;
import android.content.Intent;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import static edu.pitt.cs.cs1635.studybuddies.MainActivity.mPrefs;


public class GroupHome extends AppCompatActivity implements View.OnClickListener {

    private Button studyGroupListButton;
    private Button groupQButton;
    private Button answeredQuestion;
    private ImageButton favoriteButton;
    private static Group group;
    private static User user;
    protected static GroupList allGroups;
    //private static ArrayList<GroupQuestion> currQuestionList = new ArrayList<>() ;
    private TextView groupNameDisp;
    private TextView numMemsDisp;


    Intent inIntent = getIntent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_group_home);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set the buttons to correspond to the correct ids
        studyGroupListButton = (Button) findViewById(R.id.studyGroupsButton);
        groupQButton = (Button) findViewById(R.id.groupQsButton);
        answeredQuestion = (Button) findViewById(R.id.answeredQuestions);
        favoriteButton = (ImageButton) findViewById(R.id.favorite_indicator);

        //set onClick listeners for the buttons
        studyGroupListButton.setOnClickListener(GroupHome.this);
        groupQButton.setOnClickListener(GroupHome.this);
        answeredQuestion.setOnClickListener(GroupHome.this);
        favoriteButton.setOnClickListener(GroupHome.this);

        //get the group name and number of members in the library views
        groupNameDisp = (TextView) findViewById(R.id.groupNameHeader);
        numMemsDisp = (TextView) findViewById(R.id.textView2);

        //get the group name and number of member in the library
        String name = group.getName();
        String numOfMembers = group.getNumMembers() + "";

        //set the group name and number of member in the library
        groupNameDisp.setText(name);
        numMemsDisp.setText(numOfMembers);

        //check if group has already been favorited
        for(Group g : user.getFavorites()){
            if(group.equals(g)){
                group.isFavorite = true;
                ImageButton fav = (ImageButton) findViewById(R.id.favorite_indicator);
                fav.setImageResource(R.drawable.ic_favorite_black_24dp);
                break;
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
            intent.putExtra("favorites", favorites);
            intent.putExtra("user", user);
            intent.putExtra("groups", allGroups);
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

    public static void setUser(User currentUser) {
        user = currentUser;
    }


    public static void setGroup(Group currentGroup) {
        group = currentGroup;
    }

    public static  void setAllGroups(GroupList a){allGroups = a;}

    //public static void setStudyGroupList(ArrayList<GroupQuestion> qList){ currQuestionList = qList;}

    /**
     * Method for when the buttons are clicked
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.studyGroupsButton) {

            Intent intent = new Intent(this, StudyGroupList.class);

            StudyGroupList sGL = new StudyGroupList();
            sGL.setStudyGroupList(group.getStudyGroupList());
            sGL.setGroup(group);

            startActivity(intent);

        } else if (v.getId() == R.id.groupQsButton) {
            Intent intent = new Intent(this, GroupQuestionListActivity.class);

            GroupQuestionListActivity gQLA = new GroupQuestionListActivity();
            gQLA.setQList(group.getQuestionList());
            gQLA.setUser(user);
            gQLA.setGroup(group);

            startActivity(intent);
        } else if (v.getId() == R.id.answeredQuestions){
            Intent intent = new Intent(this, AnswerQuestionListActivity.class);

            AnswerQuestionListActivity gQLA = new AnswerQuestionListActivity();
            gQLA.setQList(group.getQuestionList());
            gQLA.setUser(user);
            gQLA.setGroup(group);
            startActivity(intent);

        }
        else if(v.getId() == R.id.favorite_indicator){
            ImageButton fav = (ImageButton) v;
            if(!group.isFavorite){
                System.out.println("HERE");

                group.setFavorite();
                user.addFavorite(group);
                group.addMember();
                numMemsDisp = (TextView) findViewById(R.id.textView2);
                String numOfMembers = group.getNumMembers() + "";
                numMemsDisp.setText(numOfMembers);
                fav.setImageResource(R.drawable.ic_favorite_black_24dp);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(user.getFavorites());
                prefsEditor.putString("MyObject", json);
                prefsEditor.commit();
            }
            else{
                ArrayList<Group> newGroups = user.getFavorites();
                newGroups.remove(newGroups.indexOf(group));
                user.setFavorites(newGroups);
                group.removeFavorite();
                group.removeMember();
                numMemsDisp = (TextView) findViewById(R.id.textView2);
                String numOfMembers = group.getNumMembers() + "";
                numMemsDisp.setText(numOfMembers);
                fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(user.getFavorites());
                prefsEditor.putString("MyObject", json);
                prefsEditor.commit();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("user",user);
        setResult(RESULT_OK,intent);
        finish();
    }
}

