package file.upload.testslide;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;

/**
 * Created by SLR on 6/22/2016.
 */
public class CameraClick extends android.support.v4.app.Fragment implements LocationListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST =12 ;
    final int CAMERA_DATA = 0;
    final int CAMERA_DATAD = 13323;
    final int GALLERY_DATAD = 1332;
    String idu;
    CameraPhoto photocr;
    GalleryPhoto gallery;
ImageView c1,c2;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;
    String  latitudek;
    Button sendik,clearall;
    EditText editText;
    String picString,m1;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.lics,
                    container, false);
        photocr = new CameraPhoto(getActivity());
        gallery = new GalleryPhoto(getActivity());
             c1=(ImageView)view.findViewById(R.id.imageView);
            c2=(ImageView)view.findViewById(R.id.imgview);
            sendik=(Button)view.findViewById(R.id.sendimg);
             editText=(EditText)view.findViewById(R.id.editText2);
            clearall=(Button)view.findViewById(R.id.clearimg);
        clearall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             c2.setImageResource(android.R.color.transparent);
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


           /* c1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // Toast.makeText(getActivity(), "time out", Toast.LENGTH_SHORT).show();
                    gallareydf();
                }
            });*/
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getActivity(), "time out", Toast.LENGTH_SHORT).show();

                final String[] option = new String[] { "Take from Camera",
                        "Select from Gallery"};
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.select_dialog_item, option);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select Option");
                builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        Log.e("Selected Item", String.valueOf(which));
                        if (which == 0) {
                            callcameraxc();
                        }
                        if (which == 1) {
                            gallareydf();
                        }

                    }




                });
                final AlertDialog dialog = builder.create();
                dialog.show();


            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ImageViewPopUpHelper.enablePopUpOnClick(getActivity(), c2);

            }
        });
        sendik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleApiClient.connect();
                String encodeImage="no pic";
                m1=editText.getText().toString();
                Bitmap bitmap = null;
                if(picString!=null){
                    try {
                        bitmap = ImageLoader.init().from(picString).requestSize(200, 200).getBitmap();
                        encodeImage = ImageBase64.encode(bitmap);
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }}
                HashMap<String, String> hashMap = new HashMap<String, String>();
                //String encodeImagez="cool";
                hashMap.put("idu",idu);
                hashMap.put("imgjpg", encodeImage);
                hashMap.put("imgtag", m1);
                hashMap.put("latitude",latitudek);
                PostResponseAsyncTask task = new PostResponseAsyncTask(getActivity(), hashMap, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {


                        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                    }

                });
                task.execute("http://payroll.venturesoftwares.org/Work/service/photocap.php");

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
                        Toast.makeText(getActivity(), "handle pro", Toast.LENGTH_SHORT).show();
                    }
                });




            }
        });

            return view;
        }

    private void gallareydf() {

        try {
            startActivityForResult(gallery.openGalleryIntent(),GALLERY_DATAD);
            //gallery.addToGallery();


        } catch (Exception e1) {
            e1.printStackTrace();


        }



    }

    private void callcameraxc() {

        try {
            startActivityForResult(photocr.takePhotoIntent(),CAMERA_DATAD);
            photocr.addToGallery();


        } catch (Exception e1) {
            e1.printStackTrace();


        }


    }
    public  void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (resultCode == Activity.RESULT_OK)
        {


            switch (requestCode) {

                case CAMERA_DATAD:


                    Bitmap bitmap=null;
                    try {

                        String photoPath=photocr.getPhotoPath();
                        picString=photoPath;
                        bitmap= ImageLoader.init().from(photoPath).requestSize(512,512).getBitmap();
                        // try{
                        final Bitmap finalBitmap = bitmap;
                        //Thread set=new Thread(){
                        //  public void run(){
                       c2.setImageBitmap(getRealoaded(bitmap, 90));
                        // }
                        // };
                        //}catch (Exception e){
                        //  Toast.makeText(Mainclass.this,"error",Toast.LENGTH_SHORT).show();
                    }
                    catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }



                    break;
                case GALLERY_DATAD:

                    try {
                        Uri uri=data.getData();
                        gallery.setPhotoUri(uri);
                        Bitmap bitmapc=null;
                        String photoPath=gallery.getPath();
                        picString=photoPath;
                        bitmapc= ImageLoader.init().from(photoPath).requestSize(512,512).getBitmap();
                        // try{
                        final Bitmap finalBitmap = bitmapc;
                        //Thread set=new Thread(){
                        //  public void run(){
                        c2.setImageBitmap(getRealoaded(bitmapc, 90));
                        // }
                        // };
                        //}catch (Exception e){
                        //  Toast.makeText(Mainclass.this,"error",Toast.LENGTH_SHORT).show();
                    }
                    catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }





                    break;






            }
        }else{
            Toast.makeText(getActivity()," ", Toast.LENGTH_SHORT).show();}
    }

   private Bitmap getRealoaded(Bitmap source,float angle) {

        Matrix matrix=new Matrix();
        matrix.postRotate(angle);
        Bitmap bitmap1=Bitmap.createBitmap(source,0,0,source.getWidth(),source.getHeight(),matrix,true);

        return bitmap1;
    }

    @Override
    public void onConnected( Bundle bundle) {

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
            Toast.makeText(getActivity(),latitudek,Toast.LENGTH_SHORT).show();
            return;
        }


    }

    @Override
    public void onConnectionSuspended(int i) {

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
}
