package com.example.harshvardhan.mit_portal.helper;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.harshvardhan.mit_portal.R;

public class Util {

    public static void toast(String text,AppCompatActivity activity){
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
    }

    public static void snackbar(View view,String text){
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
    }

    public static void log(String text){
        //Log.d("logger", text);
    }

    public static boolean isConnectedToInternet(AppCompatActivity activity) {
        Context context = activity.getBaseContext();
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return (haveConnectedWifi || haveConnectedMobile);
    }

    public static void showAlertDialog(AppCompatActivity activity, String title,
                                       String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
