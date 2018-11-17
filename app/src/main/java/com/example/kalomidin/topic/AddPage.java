package com.example.kalomidin.topic;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

public class AddPage extends AppCompatActivity {
    public static ArrayList<String> likes=new ArrayList<String>(); //saved topics added by user
    public static ArrayList<String> copy_likes=new ArrayList<String>();
    public static final ArrayList<String> liked_list=new ArrayList<String>();
    public static final String SharedPref="SharedPref";
    public static final String Texts="Text";
    public static final String Swtich="Switch";
    public static Button adding;
    public static ArrayAdapter<String> add;
    public void Data(String topic_int,int i){
        SharedPreferences sharedPreferences=getSharedPreferences(SharedPref,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(likes.get(i),topic_int);
        editor.apply();
    }
    public static ArrayList<String >getLiked_list(){
        return liked_list;//list of liked topics
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addbutton);
        getSupportActionBar().setTitle("Topics Page");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        adding=(Button)findViewById(R.id.adding);
        SwipeMenuListView listView=(SwipeMenuListView)findViewById(R.id.listView2);
        add=new ArrayAdapter<String>(AddPage.this,android.R.layout.simple_list_item_1,likes);
        listView.setAdapter(add);
        SwipeMenuCreator add_SwipeMenu = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
        SwipeMenuItem favoriteItem = new SwipeMenuItem(
                getApplicationContext());
        // set item background
        favoriteItem.setBackground(new ColorDrawable(Color.rgb(0x33,
                0xff, 0xcc)));
        // set item width
        favoriteItem.setWidth(180);
        // set a icon
        favoriteItem.setIcon(R.drawable.ic_favorite);
        // add to menu
        menu.addMenuItem(favoriteItem);
        }};
        listView.setMenuCreator(add_SwipeMenu);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                liked_list.add(likes.get(position));
                Toast toast = Toast.makeText(getApplicationContext(), "Topic "+likes.get(position)+" added to list", Toast.LENGTH_SHORT);
                toast.show();
                return false;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.search,menu);
        MenuItem searchItem=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView)searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                final String a=query;
              adding.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v){
                          likes.add(a);
                          add.notifyDataSetChanged();
                  }
              });
              return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final String a=newText;
                adding.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        likes.add(a);
                        add.notifyDataSetChanged();
                    }
                });
                return false;
            }
        });
        return true;
    }
}