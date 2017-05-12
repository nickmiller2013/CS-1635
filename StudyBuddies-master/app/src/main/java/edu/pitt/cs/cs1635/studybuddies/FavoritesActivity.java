package edu.pitt.cs.cs1635.studybuddies;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Louie on 3/14/17.
 */

public class FavoritesActivity extends AppCompatActivity {
    User currentUser;
    GroupList groups;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_favorites);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentUser = (User) getIntent().getSerializableExtra("user");
        groups = (GroupList) getIntent().getSerializableExtra("groups");

        Bundle b = getIntent().getExtras();
        ArrayList<String> favorites = (ArrayList<String>) b.getSerializable("favorites");
        Adapter1<String> itemsAdapter = new Adapter1<String>(this, android.R.layout.simple_list_item_1, favorites);
        ListView favs = (ListView) findViewById(R.id.favorite_list);
        favs.setAdapter(itemsAdapter);
        favs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //here is the onClick where you should be able to make call
                String name =(String)parent.getItemAtPosition(position);
                goToGroup(name);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToGroup(String s){
        Group g = groups.getGroupByName(s);
        Intent intent = new Intent(this, GroupHome.class);
        GroupHome.setGroup(g);
        GroupHome.setUser(currentUser);
        startActivity(intent);
    }

    public class Adapter1<String> extends ArrayAdapter<String> {

        public Adapter1(Context context, int resID, ArrayList<String> items) {
            super(context, resID, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = super.getView(position, convertView, parent);
            ((TextView) v).setTextColor(Color.BLACK);
            return v;
        }

    }
}

