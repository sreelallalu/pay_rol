package file.upload.testslide;

import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;

/**
 * Created by SLR on 6/22/2016.
 */
public class Chatwith extends android.support.v4.app.Fragment implements LocationListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST =12 ;
    EditText sendmsg;
    Button sendok,receiveok,clearall;
    TextView replytext;
    String b1;
    String idu;
JSONObject jsonObject;
   
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;
  String  latitudek;
    String idunamec;
    HashMap<String, String>  hashMap,hashMap1;
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.chatwith,
                    container, false);
            sendmsg=(EditText)view.findViewById(R.id.editText);
             sendok=(Button)view.findViewById(R.id.button);
           receiveok=(Button)view.findViewById(R.id.refresh);
            replytext=(TextView)view.findViewById(R.id.textrespose);
            clearall=(Button)view.findViewById(R.id.clearchat);


        clearall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               sendmsg.setText("");
            }
        });
            DataB data=new DataB(getActivity());
            data.open();
            idu=data.getData1();


            data.close();

            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    // The next two lines tell the new client that “this” current class will handle connection stuff

                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    //fourth line adds the LocationServices API endpoint from GooglePlayServices
                    .addApi(LocationServices.API)
                    .build();


            // Create the LocationRequest object
            mLocationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                    .setFastestInterval(1 * 1000); //

            mGoogleApiClient.connect();
            

            sendok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    b1 = sendmsg.getText().toString();
                    mGoogleApiClient.connect();
                    // Toast.makeText(getActivity(),idunamec,Toast.LENGTH_SHORT).show();

                    hashMap = new HashMap<String, String>();
                    hashMap.put("idu", idu);
                    hashMap.put("tag", "sendmsg");
                    hashMap.put("message", b1);
                    hashMap.put("latitude", latitudek);



                    messgop(hashMap);
                }

                });



            receiveok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Toast.makeText(getActivity(), idu, Toast.LENGTH_SHORT).show();

                    hashMap1 = new HashMap<String, String>();
                    hashMap1.put("idu", idu);
                    hashMap1.put("tag", "responsemsg");
                    messgop(hashMap1);
                      /*
                    PostResponseAsyncTask task = new PostResponseAsyncTask(getActivity(), hashMap1, new AsyncResponse() {

                        public void processFinish(String s) {


                            try {
                             Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();

                                JSONObject yu=new JSONObject(s);
                                String fri=(String)yu.getString("response");
                              replytext.setText(fri);

                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }

                            //st.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();


                        }

                    });
                    task.execute("http://payroll.venturesoftwares.org/Work/service/response.php");

                    task.setEachExceptionsHandler(new EachExceptionsHandler() {
                        @Override
                        public void handleIOException(IOException e) {
                            Toast.makeText(getActivity(), "time out", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void handleMalformedURLException(MalformedURLException e) {
                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void handleProtocolException(ProtocolException e) {
                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                        }

                        @Override

                        public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {
                            Toast
                            .makeText(getActivity(), "handle pro", Toast.LENGTH_SHORT).show();
                        }

                    });
                           */


                    //new CheckAvialability().execute();
                }});



                   

                



            
           









            return view;
        }

    private void messgop(HashMap<String, String> hashMapz) {


        PostResponseAsyncTask task = new PostResponseAsyncTask(getActivity(), hashMapz, new AsyncResponse() {

            public void processFinish(String s) {

               Toast.makeText(getActivity(),s.toString(),Toast.LENGTH_SHORT).show();
                try {
                   // Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();

                    JSONObject yu=new JSONObject(s);
                    String fri=(String)yu.getString("response");
                    replytext.setText(fri);

                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                //st.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();


            }

        });
        task.execute("http://payroll.venturesoftwares.org/Work/service/message.php");

        task.setEachExceptionsHandler(new EachExceptionsHandler() {
            @Override
            public void handleIOException(IOException e) {
                Toast.makeText(getActivity(), "time out", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleMalformedURLException(MalformedURLException e) {
                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleProtocolException(ProtocolException e) {
                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override

            public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {
                Toast
                        .makeText(getActivity(), "handle pro", Toast.LENGTH_SHORT).show();
            }

        });
        
        
    }


    @Override
    public void onConnected(Bundle bundle) {


        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);

        } else {
            //If everything went fine lets get latitude and longitude
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();
            latitudek=""+currentLatitude+ "," +currentLongitude;
          //Toast.makeText(getActivity(),latitudek,Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(getActivity(), CONNECTION_FAILURE_RESOLUTION_REQUEST);
                    /*
                     * Thrown if Google Play services canceled the original
                     * PendingIntent
                     */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
                /*
                 * If no resolution is available, display a dialog to the
                 * user with the error.
                 */
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }

    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
   


}
