package edu.pitt.cs.cs1635.studybuddies;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;


public class StudyGroupList extends AppCompatActivity implements View.OnClickListener {

    private static ArrayList<StudyGroup> currStudyGroupList = new ArrayList<>();
    private static User user;

    private static Group group;
    //private static int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_group_list);
        user = MainActivity.currentUser;

        //create dummy studyGroups
        createDummyStudyGroups();

        //set list of buttons to groups
        updateAvailableStudyGroups(currStudyGroupList);

        //set up the dummy group buttons
        //setDummyStudyGroupButtons();

        final EditText search = (EditText) findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                filterStudyGroups(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });


}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navbar, menu);
        return true;
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
            intent.putExtra("user", user);
            intent.putExtra("groups", GroupHome.allGroups);
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

        //public static void setUser(User currentUser) {
            //user = currentUser;
        //}

        public static void setStudyGroupList(ArrayList<StudyGroup> qList){ currStudyGroupList = qList;}

        public static void setGroup(Group currentGroup) {
        group = currentGroup;
    }



        /**
         * Hard code some questions
         */
        public void createDummyStudyGroups(){
            if(group.c1 <=0){
                currStudyGroupList.add(new StudyGroup("Homework help"));
                currStudyGroupList.add(new StudyGroup("Cram for exam"));
                group.c1++;
            }

        }

        /**
         * Update UI to reflect filtered questions
         * @param updatedStudyGroups filtered list of questions
         */
        public void updateAvailableStudyGroups(ArrayList<StudyGroup> updatedStudyGroups){

            LinearLayout qList = (LinearLayout) findViewById(R.id.study_list);

            qList.removeAllViews();

            for(int i = 0; i < updatedStudyGroups.size(); i++){
                Button tempButton = new Button(this);
                StudyGroup tempStudyGroup = updatedStudyGroups.get(i);
                tempButton.setText(tempStudyGroup.getName());
                tempButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                qList.addView(tempButton);
                setDummyStudyGroupButtons();
            }
        }

        /**
         * create filtered question list and send to update method
         * @param s filter string
         */
        public void filterStudyGroups(String s){
            if(s.length()==0){
                updateAvailableStudyGroups(currStudyGroupList);
            }
            else {
                ArrayList<StudyGroup> filtered = new ArrayList<>();
                s = s.toLowerCase();
                for (int i = 0; i < currStudyGroupList.size(); i++) {
                    StudyGroup temp = currStudyGroupList.get(i);
                    if (temp.getName().toLowerCase().contains(s)) {
                        filtered.add(temp);
                    }
                }
                updateAvailableStudyGroups(filtered);
            }
        }


        /**
         * Make the dummy question buttons clickable
         */
        private void setDummyStudyGroupButtons(){

            LinearLayout QuestionList = (LinearLayout) findViewById(R.id.study_list);

            for(Object obj: QuestionList.getTouchables()){
                if (obj instanceof Button) {
                    Button tempButton = (Button) obj;
                    tempButton.setClickable(true);
                    tempButton.setFocusable(true);
                    tempButton.setOnClickListener(this);

                }
            }
        }


        /**
         * Create the appropriate behavior for the dummy buttons upon being clicked
         * @param v
         */
        @Override
        public void onClick(View v) {

            ArrayList<StudyGroup> g_list = currStudyGroupList;

            if ( v instanceof Button) {
                for(StudyGroup g : g_list){

                    String candidateBtnText = g.getName().toLowerCase().trim();
                    String viewText = ((Button) v).getText().toString().toLowerCase().trim();

                    if(candidateBtnText.equals(viewText)){

                        Intent intent = new Intent(this, StudyGroupHome.class);
                        intent.putExtra("user", user);
                        intent.putExtra("name", g.getName());
                        intent.putExtra("floor", g.getFloor());
                        intent.putExtra("duration", g.getDuration());
                        startActivity(intent);

                    }
                }
            }

        }
    public void addStudyGroup(View v){
        //this is the add buttons onCLick method as defined in main_activity.xml
        Intent createStudyGroup = new Intent(this, CreateStudyGroupActivity.class);
        startActivityForResult(createStudyGroup, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == 1) {
                Bundle b = data.getExtras();
                if(b != null) {
                    StudyGroup newGroup = (StudyGroup)b.getSerializable("newStudyGroup");
                    currStudyGroupList.add(newGroup);
                    updateAvailableStudyGroups(currStudyGroupList);
                    setDummyStudyGroupButtons();
                    new AlertDialog.Builder(this,R.style.AppCompatAlertDialogStyle)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setMessage(newGroup.getName() + " created!")
                            .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }

                            })
                            .show();
                }
            }
        }
    }

        /**
         * ADD A QUESTION PAGE TO BE IMPLEMENTED
         */


    }

