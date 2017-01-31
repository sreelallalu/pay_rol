package file.upload.testslide;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;

import static file.upload.testslide.NetworkChecker.isConnected;
import static file.upload.testslide.NetworkChecker.isConnectedMobile;
import static file.upload.testslide.NetworkChecker.isConnectedWifi;

/**
 * Created by SLR on 7/1/2016.
 */
public class Registerfg extends AppCompatActivity {
    EditText t1,t2;
    TextView y1;
    Button b1;
    String fg;
    String fg1;

    protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_mainx);
        t1=(EditText)findViewById(R.id.editTextbj);
        t2=(EditText)findViewById(R.id.editText1bj);
        b1=(Button)findViewById(R.id.loginbj);
        y1=(TextView)findViewById(R.id.textViewbj);
        b1.setOnClickListener(new View.OnClickListener() {

       //


            @Override
            public void onClick(View v) {

                if( isConnectedWifi(Registerfg.this)||isConnectedMobile(Registerfg.this))
                {
                    if (isConnected(Registerfg.this))
                    {
                        fg=t1.getText().toString();
                        fg1=t2.getText().toString();

                        // new CheckAvialability().execute();
                        HashMap hashMap = new HashMap<String, String>();
                        hashMap.put("logui","login");


                        hashMap.put("username", fg);
                        hashMap.put("userpass", fg1);

                        // Intent ik=new Intent(Registerfg.this,MainActivity.class);
                        // startActivity(ik);


//99 95 55 09 21

                        PostResponseAsyncTask task = new PostResponseAsyncTask(Registerfg.this, hashMap, new AsyncResponse() {

                            public void processFinish(String s) {
                                //sToast.makeText(Registerfg.this,s,Toast.LENGTH_SHORT).show();

                                try {

                                    JSONObject jsonObject=new JSONObject(s);
                                    String empid= (String) jsonObject.getString("empid");
                                    String succf= (String) jsonObject.getString("succ");
                                    //String succf="ch";

                                    if(succf.trim().equals("succb"))
                                    {
                                        String dfi=fg;
                                        y1.setText("");
                                        // Toast.makeText(Registerfg.this,empid,Toast.LENGTH_SHORT).show();
                                        DataB data=new DataB(Registerfg.this);
                                        data.open();

                                        data.createEntry(empid);
                                        // data.createEntry(dfi);
                                        data.close();
                                        Intent ik=new Intent(Registerfg.this,MainActivity.class);
                                        startActivity(ik);

                                    }

                                    if(succf.trim().equals("error")){
                                        y1.setText("error login");
                                    }


                                    //finish();
                                    //  System.exit(0);

                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }

                                //st.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();


                            }

                        });
                        task.execute("http://payroll.venturesoftwares.org/Work/service/userlogout.php");

                        task.setEachExceptionsHandler(new EachExceptionsHandler() {
                            @Override
                            public void handleIOException(IOException e) {
                                Toast.makeText(Registerfg.this, "time out", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void handleMalformedURLException(MalformedURLException e) {
                                Toast.makeText(Registerfg.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void handleProtocolException(ProtocolException e) {
                                Toast.makeText(Registerfg.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {
                                Toast.makeText(Registerfg.this, "handle pro", Toast.LENGTH_SHORT).show();
                            }
                        });



                    }
                    else
                    {
                        AlertDialog.Builder editBuilder=new AlertDialog.Builder(Registerfg.this);
                        editBuilder.setMessage("ERR_CONNECTION_REFUSED");
                        editBuilder.setTitle("refused to connect");
                        editBuilder.setCancelable(true);
                        editBuilder.setPositiveButton( "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert11 =editBuilder.create();
                        alert11.show();
                    }

                }
                else
                {
                    AlertDialog.Builder editBuilder=new AlertDialog.Builder(Registerfg.this);
                    editBuilder.setMessage("Turn on wifi");
                    editBuilder.setTitle("refused to connect");
                    editBuilder.setCancelable(true);
                    editBuilder.setPositiveButton( "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 =editBuilder.create();
                    alert11.show();

                }












            }
        });

    }
   /* class CheckAvialability extends AsyncTask<String, Void, JSONObject> {

        ProgressDialog p = new ProgressDialog(Registerfg.this);

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub

            super.onPreExecute();
            p.setCancelable(false);
            p.show();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            // TODO Auto-generated method stub

            jsonObject = getJSONFromUrl("http://payroll.venturesoftwares.org/Work/service/userlogout.php");
            if (jsonObject != null) {
                try {
                    Log.d("json values",
                            "json" + jsonObject.getString("success"));
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject result) {

            p.dismiss();
            if (result != null)

            {
                try {
                    JSONObject qrjson = result.getJSONObject("loginy");


                    //JSONObject jsonObject=new JSONObject(s);
                    String empid = (String) qrjson.getString("empid");
                    String succf = (String) qrjson.getString("succ");
                    //String succf="ch";

                    if (succf.trim().equals("succb")) {
                        String dfi = fg;
                        y1.setText("");
                        // Toast.makeText(Registerfg.this,empid,Toast.LENGTH_SHORT).show();
                        DataB data = new DataB(Registerfg.this);
                        data.open();

                        data.createEntry(empid);
                        // data.createEntry(dfi);
                        data.close();
                        Intent ik = new Intent(Registerfg.this, MainActivity.class);
                        startActivity(ik);

                    }

                    if (succf.trim().equals("error")) {
                        y1.setText("error login");
                    }


                    //finish();
                    //  System.exit(0);

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }  else {
                    Toast.makeText(Registerfg.this, "Error in connection",
                            Toast.LENGTH_LONG).show();
                }

            // TODO Auto-generated method stub
            super.onPostExecute(result);
        }

        public JSONObject getJSONFromUrl(String url) {

            InputStream is = null;
            JSONObject jObj = null;
            String json = "";
            URL obj;
            HttpURLConnection con = null;

            // Making HTTP request
            try {
                System.out.println("url" + url);

                obj = new URL(url);
                con = (HttpURLConnection) obj.openConnection();

                // optional default is GET
                con.setRequestMethod("POST");
                con.setDoOutput(true);
                // add request header
                con.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
                // con.setRequestProperty("User-Agent", USER_AGENT);
                // con.setRequestProperty("Accept", "application/json");
                // con.setRequestProperty("tag", "scan");
                // con.setRequestProperty("code", qrcode);
                String charset = "UTF-8";
                String s = "logui=" + URLEncoder.encode("login", charset);
                s += "&username=" + URLEncoder.encode(fg, charset);
                s += "&userpass=" + URLEncoder.encode(fg1, charset);
                con.setFixedLengthStreamingMode(s.getBytes().length);
                PrintWriter out = new PrintWriter(con.getOutputStream());
                out.print(s);
                out.close();
                int responseCode = con.getResponseCode();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // print result

                json = response.toString();
                Log.e("JSON", json);
            } catch (Exception e) {
                Log.e("Buffer Error", "Error converting result " + e.toString());
            }

            // try parse the string to a JSON object
            try {
                jObj = new JSONObject(json);
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }

            // return JSON String
            return jObj;

        }
    }*/
}
