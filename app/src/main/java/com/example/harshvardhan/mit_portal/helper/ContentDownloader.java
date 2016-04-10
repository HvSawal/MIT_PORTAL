package com.example.harshvardhan.mit_portal.helper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ContentDownloader {

    public static String downloadContent(final String URL, ArrayList<String> postName,
                                         ArrayList<String> postParameter,HttpClient client) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            HttpPost httpPost = new HttpPost(URL);
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                try{
                    for(int i=0;i<postName.size();i++){
                        nameValuePairs.add(new BasicNameValuePair(postName.get(i),postParameter.get(i)));
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = client.execute(httpPost);
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                if (statusCode == 200) {
                    HttpEntity entity = response.getEntity();
                    InputStream content = entity.getContent();
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(content));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                } else {
                    return "";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return stringBuilder.toString();
    }

}
