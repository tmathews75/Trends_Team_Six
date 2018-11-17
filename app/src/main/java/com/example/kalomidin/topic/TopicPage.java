package com.example.kalomidin.topic;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.kalomidin.topic.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class TopicPage extends AppCompatActivity {
    private static String topic;
    private static ArrayList<String> titles=new ArrayList<String>();
    private static ArrayList<String> links=new ArrayList<String>();
    public static ProgressBar progressBar;
    public static ArrayAdapter<String> topic_adapter;
    public static ArrayList<String> topic_list;
    public static String result=null;
    public static SwipeMenuCreator topic_SwipeMenu;
    public static SwipeMenuCreator result_SwipeMenu;
    public static ArrayAdapter<String> topic_result_adapter;
    public static ArrayList<String> liked_list=new ArrayList<String>();
    public static ArrayList<String> liked_listlinks=new ArrayList<String>();
    public static SwipeMenuListView listView;
    public static ArrayList<String> getLikes(){
        return liked_list;
    }
    Integer responseCode;
    String responseMessage = "";
    public static int k=0;
    private static final String TAG = "Google Custom Search";
    public static ArrayList<String> getLinks() {
        return links;
    }
    public static void setLinks(ArrayList<String> linkss) {
         links=linkss;
    }
    public static void setTitles(ArrayList<String> titless) {
        titles=titless;
    }
    public static ArrayList<String> getTitles() { return titles; }

    public static ProgressBar getProgressBars(){
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar){
        this.progressBar=progressBar;
    }

    protected void onCreate(Bundle savedInstanceState) {
        MainActivity a=new MainActivity();
        topic=a.get_Topic();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_page);
        getSupportActionBar().setTitle("Results");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.d(TAG, "***** APP STARTED *****");
        progressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        listView=(SwipeMenuListView)findViewById(R.id.listView_resultspage);
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(listView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        String searchStringNoSpaces = topic.replace(" ", "+");

                //Api Key
                String APIKey = "AIzaSyBP8aw1sWdDsd9zkRjOE_nRJ3vyI8e4t-4";

                //Search Engine ID
                String searchEngineID = "010128440179048027073:g7abrcahlou";

                //Set the URL String
                String URLString = "https://www.googleapis.com/customsearch/v1?q=" + searchStringNoSpaces + "&key=" + APIKey + "&cx=" + searchEngineID + "&alt=json";

                URL searchURL = null;

                try {

                    searchURL = new URL(URLString);

                } catch (MalformedURLException e) {

                    Log.e(TAG, "***** ERROR Converting String to URL *****");

                }

                Log.d(TAG, "***** URL is " + URLString);

                GoogleSearchAsyncTask searchTask = new GoogleSearchAsyncTask();
                try {
                    result = searchTask.execute(searchURL).get();
                    Log.d("IThasnotcausedException", URLString);
                    //Hide Progress Bar
                    progressBar.setVisibility(View.GONE);
                    try {

                        JSONObject resultReader = new JSONObject(result);

                        JSONArray items = resultReader.getJSONArray("items");

                        //Loop
                        for (int i = 1; i <= items.length(); i++) {

                            JSONObject pointer = items.getJSONObject(i);
                                Log.d("I am in", titles.size() + "fdsfsd");
                                titles.add(pointer.getString("title"));
                                links.add(pointer.getString("formattedUrl"));
                        }

                    } catch (final JSONException e) {
                        Log.d("I am out", titles.size() + "fdsfsd");
                    }
                }catch(Exception E){
                    Log.d("IT has caused Exception"," Not Reading return");}
                    topic_result_adapter=new ArrayAdapter<String>(TopicPage.this,android.R.layout.simple_list_item_1,titles);
                    listView.setAdapter(topic_result_adapter);
                    result_SwipeMenu = new SwipeMenuCreator() {
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

                        }
                    };
                    listView.setMenuCreator(result_SwipeMenu);
                    //listView.requestLayout();
                    listView.setOnItemClickListener(new SwipeMenuListView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //topic_result_adapter.notifyDataSetChanged();
                            Toast toast = Toast.makeText(getApplicationContext(), "Topic"+position+" selected", Toast.LENGTH_SHORT);
                            toast.show();
                            String substring=links.get(position).substring(0,4);
                            if(substring.equals("http")){
                                Intent searching=new Intent(Intent.ACTION_VIEW, Uri.parse(links.get(position)));
                                startActivity(searching);}
                            else{
                                Intent searching=new Intent(Intent.ACTION_VIEW, Uri.parse("https://"+links.get(position)));
                                startActivity(searching);}
                        }
                    });
                    listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                            //      topic_result_adapter.notifyDataSetChanged();
                            if(index==0){
                                    //share bottom
                                    Intent a=new Intent(Intent.ACTION_SEND);
                                    a.setType("text/plain");
                                    String shareBody="Your body here";
                                    String shareSub="Your Subject here";
                                    a.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                                    a.putExtra(Intent.EXTRA_SUBJECT,shareBody);
                                    startActivity(Intent.createChooser(a,"share options"));
                                    return false;}
                                    else if(index==1){
                                    Toast toast = Toast.makeText(getApplicationContext(), "Article "+titles.get(position)+" added to favorities", Toast.LENGTH_SHORT);
                                    toast.show();
                                    liked_list.add(titles.get(position));
                                    liked_listlinks.add(links.get(position));
                                    topic_result_adapter.notifyDataSetChanged();
                                    return false;}
                                    return false;
                        }
                    });
                }


    private class GoogleSearchAsyncTask extends AsyncTask<URL, Integer, String> {

        protected void onPreExecute() {


            Log.d(TAG, "***** AsyncTask - onPreExecute() *****");

            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(URL... newURLString) {

            URL url = newURLString[0];

            Log.d(TAG, "***** AsyncTask - doInBackground() *****");

            HttpURLConnection connection = null;

            try {

                connection = (HttpURLConnection) url.openConnection();

            } catch (IOException e) {

                Log.e(TAG, "***** AsyncTask - doInBackground() -> HTTP Connection Error " + e.toString() + " *****");

            }

            try {

                responseCode = connection.getResponseCode();
                responseMessage = connection.getResponseMessage();

            } catch (IOException e) {

                Log.e(TAG, "***** AsyncTask - doInBackground() -> HTTP Getting Response Code ERROR " + e.toString() + " *****");

            }

            Log.d(TAG, "***** AsyncTask - doInBackground() -> HTTP Response Code is " + responseCode + " HTTP Message is " + responseMessage + " *****");

            try {

                //Successful Response
                if (responseCode == 200) {

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    StringBuilder stringBuilder = new StringBuilder();

                    String line;

                    while ((line = reader.readLine()) != null) {

                        stringBuilder.append(line + "\n");

                    }

                    reader.close();

                    connection.disconnect();

                    result = stringBuilder.toString();

                    return result;

                } else {

                    String errorMessage = "HTTP ERROR Response " + responseMessage + "\n";

                    result = errorMessage;

                    Log.d(TAG, "***** AsyncTask - doInBackground() -> HTTP Response Code != 200 *****");

                    return result;

                }

            } catch (IOException e) {

                Log.e(TAG, "***** AsyncTask - doInBackground() -> HTTP Response ERROR " + e.toString() + " *****");

            }

            return null;

        }

        protected void onProgressUpdate(Integer... progress) {

            Log.d(TAG, "***** AsyncTask - onProgressUpdate() *****");

        }

}}