package com.example.kalomidin.topic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuView;
import com.example.kalomidin.topic.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ProgressBar progressBar;
    public static ArrayAdapter<String> topic_adapter;
    public static ArrayList<String> topic_list;
    public static Button topic;
    public static String result;
    public static int k=0;
    public static Switch switch1;
    public static SwipeMenuCreator topic_SwipeMenu;
    public static SwipeMenuCreator result_SwipeMenu;
    public static ArrayAdapter<String> topic_result_adapter;
    public static ArrayList<String> liked_list=new ArrayList<String>();
    public static ArrayList<String> liked_listlinks=new ArrayList<String>();
    private TextView mTextMessage;
    public static SwipeMenuListView listView;
    public static ProgressBar getProgressBar() {
        return progressBar;
    }
    public ArrayList<String> get_Likes(){
        return liked_list;
    }
    public static Button addinterest;
    public static String get_Topic() {
        return result;
    }
    public static ArrayList<String> get_Topics(){ return topic_list;}
    public void setResult(String result){
        this.result=result;
    }
    public void topic_activity(){
        switch1=(Switch)findViewById(R.id.switch1);
        switch1.setVisibility(View.GONE);
        topic_list=new ArrayList<String>();
        for(int i=0;i<topic_list.size();i++)
            Log.d("dsds",topic_list.get(i));
        topic_adapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,topic_list);
        listView.setAdapter(topic_adapter);
        topic_SwipeMenu = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0x33,
                        0xff, 0xcc)));
                // set item width
                deleteItem.setWidth(180);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_remove);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        listView.setMenuCreator(topic_SwipeMenu);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                ArrayList<String> topiclist1=new ArrayList<String>();
                topiclist1=topic_list;
                topiclist1.remove(position);
                ArrayAdapter<String> topic_adapter1=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,topiclist1);
                listView.setAdapter(topic_adapter1);
                topic_adapter=topic_adapter1;
                topic_list=topiclist1;
                //print the message
                Toast toast = Toast.makeText(getApplicationContext(), "Removed Topic", Toast.LENGTH_SHORT);
                toast.show();
                return false;
                // false : close the menu; true : not close the menu
            }
        });
        listView.setOnItemClickListener(new SwipeMenuListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast toast = Toast.makeText(getApplicationContext(), "Topic"+position+" selected", Toast.LENGTH_SHORT);
                toast.show();
                result=topic_list.get(position);
                topicActivity();

            }});
        TopicPage a=new TopicPage();
        a.setLinks(new ArrayList<String>());
        a.setTitles(new ArrayList<String>());
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //addinterest botton
        addinterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AddPage.class);
                startActivity(intent);
            }
        });
        AddPage p=new AddPage();
        ArrayList<String> k=p.getLiked_list();
        Log.d("Reading",""+k.size());
        if(k!=null){
            Log.d("K is ",""+k.size());
            for(int i=0;i<k.size();i++){
                Log.d("K is ",""+k.get(i));
                topic_list.add(k.get(i));
            }
            topic_adapter.notifyDataSetChanged();
            listView.setAdapter(topic_adapter);
        }
    }
    public void topicActivity(){
        Intent intent=new Intent(MainActivity.this, TopicPage.class);
        startActivity(intent);
    }
    public void liked_activity(){
        switch1.setVisibility(View.GONE);
        final ArrayAdapter<String> liked_adapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,liked_list);
        listView.setAdapter(liked_adapter);
        SwipeMenuCreator liked_SwipeMenu = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem shareItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                shareItem.setBackground(new ColorDrawable(Color.rgb(0x33,
                        0xff, 0xcc)));
                // set item width
                shareItem.setWidth(180);
                // set a icon
                shareItem.setIcon(R.drawable.ic_share);
                // add to menu
                menu.addMenuItem(shareItem);
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0x33,
                        0xff, 0xcc)));
                // set item width
                deleteItem.setWidth(180);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_remove);
                // add to menu
                menu.addMenuItem(deleteItem);

            }
        };
        listView.setMenuCreator(liked_SwipeMenu);
        listView.setOnItemClickListener(new SwipeMenuListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast toast = Toast.makeText(getApplicationContext(), "Topic"+position+" selected", Toast.LENGTH_SHORT);
                toast.show();
                String substring=liked_list.get(position).substring(0,4);
                if(substring.equals("http")){
                    Intent searching=new Intent(Intent.ACTION_VIEW, Uri.parse(liked_list.get(position)));
                    startActivity(searching);}
                else{
                    Intent searching=new Intent(Intent.ACTION_VIEW, Uri.parse("https://"+liked_listlinks.get(position)));
                    startActivity(searching);}
            }
        });
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                //      topic_result_adapter.notifyDataSetChanged();
                switch (index){
                    case 0:
                        //share bottom
                        Intent a=new Intent(Intent.ACTION_SEND);
                        a.setType("text/plain");
                        String shareBody="Your body here";
                        String shareSub="Your Subject here";
                        a.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                        a.putExtra(Intent.EXTRA_SUBJECT,shareBody);
                        startActivity(Intent.createChooser(a,"share options"));
                        break;
                    case 1:
                        Toast toast = Toast.makeText(getApplicationContext(), "Topic"+liked_list.get(position)+" removed from favorities", Toast.LENGTH_SHORT);
                        toast.show();
                        liked_list.remove(liked_list.get(position));
                        liked_adapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_topics:
                    addinterest.setVisibility(View.VISIBLE);
                    topic_activity();
                    return true;
                case R.id.nav_liked:
                    TopicPage a=new TopicPage();
                    liked_list=a.getLikes();
                    addinterest.setVisibility(View.GONE);
                    Log.d("sdsd","Entering Liked");
                    liked_activity();
                    return true;
                case R.id.nav_notifications:
                    Log.d("sdsd", "Entering Notifications");

                    switch1.setVisibility(View.VISIBLE);
                    addinterest.setVisibility(View.GONE);
                    switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                Log.d("sdsd", "Entering Likeddsddsd");
                                Intent intent = new Intent(MainActivity.this, Background.class);
                                startService(intent);
                                Log.d("sdsd", "Entering Likedyyeye");
                            }}
                    });
                    return true;
            }
            return false;
        }
    };//function for navigation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=(SwipeMenuListView)findViewById(R.id.listView);
        mTextMessage = (TextView) findViewById(R.id.message);
        addinterest=(Button)findViewById(R.id.add);
        topic_activity();
        AddPage sdd=new AddPage();
        ArrayList<String> likedones=sdd.getLiked_list();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}