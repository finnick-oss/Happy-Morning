package com.anvelopers.happymorning.ConnectivityCheck;

//Imported packages and classes

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.widget.Toast;

public class InternetConnectivityCheck {


     /*

    Method wil check Network is available or not
    if available then it will return true else false

     */

    public boolean isNetworkAvailable(Context context) {     //method

        //ConnectivityManager object

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {  //Check weather connectivityManage is not null


            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { //Testing build version

                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());

                //Testing all the type of network availabilities


                Toast.makeText(context, ""+capabilities.getLinkDownstreamBandwidthKbps(), Toast.LENGTH_SHORT).show();

                if (capabilities != null) {  //Testing Network Capabilities

                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {

                        return true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {

                        return true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {

                        return true;
                    }
                }
            }

        }  //if

        return false;

    }  //method


}
