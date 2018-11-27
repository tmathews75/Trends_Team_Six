package com.example.kalomidin.topic;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ReadCSV extends AppCompatActivity {


    public ArrayList<Integer> popVals;
    public AssetManager AM;
    public ReadCSV(Context context, String theTopic){
        AssetManager MNGR = context.getAssets();
        this.AM = MNGR;
        this.popVals = readACSVFile(matchTopicToFileName(theTopic));
    }


    public int[] values;
    public static String[] FileNames={"Bruno_Mars.csv","Christiano_Ronaldo.csv","Coldplay","Conor_McGregor.csv","Dwayne_Johnson.csv","Ed Sheeran.csv","Ellen_DeGeneres.csv","Floyd_Mayweather.csv","George_Clooney.csv","Howard_Stern.csv","James_Patterson.csv","Judy_Sheindlin.csv","Katy_Perry.csv","Kylie_Jenner.csv","LeBron_James.csv","Lionel_Messi.csv","Neymar.csv","Robert_Downey.csv","U2.csv" }; //will be an array containing list of names of csv files
    public static final String[] TopicNames = {"Bruno Mars","Christiano Ronaldo","Coldplay", "Conor McGregor","Dwayne Johnson","Ed Sheeran", "Ellen DeGeneres", "FLoyd Mayweather","George Clooney","Howard Stern","James Patterson","Judy Sheindlin", "Katy Perry","Kylie Jenner","Lebron James","Lionel Messi","Neymar","Robert Downey Jr.","U2"}; //fill with search term



    public ArrayList<Integer> getValues(){
        return this.popVals;
    }


    //this method returns an array containing weekly popularity when you pass it the filename
    public ArrayList<Integer> readACSVFile (String filename) {
        ArrayList<Integer> results;
        results = new ArrayList<Integer>(100);
        Log.d("sdsd", "Entering Liked");
        try {
            Log.d("sdsd", "Entering Likedasdasdasdsaerererereed");
            InputStream is = this.AM.open("CSVFiles/" + filename);
            Log.d("sdsd", "Entering Likedasdasdasdsa4545d");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            Log.d("sdsd", "Entering Likedasdasdasdsad1");
            for (int i = 0; i < 3; i++) {
                br.readLine();
            }
            Log.d("sdsd", "Entering Likedasdasdasdsad");
            String line;
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                results.add(Integer.parseInt(row[1]));
            }
        } catch (IOException e) {
            Log.d("sdsd", "Entering Likedfdgrytrt");
            e.printStackTrace();
        }
        return results;
    }
    //this method matches the search term to a topic
    public String matchTopicToFileName(String topic) {
        int i = 0;
        boolean found = false;
        while (i < TopicNames.length) {
            if (topic.equals(TopicNames[i])) {
                found = true;
                break;
            }
            i++;
        }
        //makeListOfFiles();
        Log.d("nonsense", FileNames[0]);
        if(found) return FileNames[i];
        else return null; //should have error message later
    }
    //this method creates a list of filenames in the assets folder
    /*
    public void makeListOfFiles (){
        try {
            AssetManager aMgr = App.getAppContext().getAssets();
            FileNames = aMgr.list("CSVFiles/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
}
