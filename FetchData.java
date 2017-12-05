package com.example.hafiz.jsonparsing;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.Buffer;

/**
 * Created by Hafiz on 12/5/2017.
 */

public class FetchData extends AsyncTask<Void,Void,Void> {
    String data="";
    String dataParsed="";
    String singleParsed="";
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url=new URL("https://api.myjson.com/bins/poywb");//the link from where data will be parsed
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            InputStream inputStream=httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            while(line!=null){
                line=bufferedReader.readLine();
                data=data+line;
            }
            //The work to be done after fetching data from internet
            JSONArray ja=new JSONArray(data);
            for(int i=0;i<ja.length();i++){
                JSONObject jo=(JSONObject)ja.get(i);
                //as the JSON array is an array of JSON objects, each element taken by "jo" from
                //this array will be a JSON object and now we have to extract the attributes of each
                //object as below:
                singleParsed="Name: "+jo.get("name")+"\n"+
                             "Password: "+jo.get("password")+"\n"+
                             "Contact No: "+jo.get("contact")+"\n"+
                             "Country: "+jo.get("country")+"\n\n";
                dataParsed=dataParsed+singleParsed;
            }

        }catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        MainActivity.data.setText(this.dataParsed);//dataParsed
    }
}
