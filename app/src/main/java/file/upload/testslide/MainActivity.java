package file.upload.testslide;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

public class MainActivity extends AppCompatActivity {
    private JSONObject jsonObject;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private  String idub;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    int resorce[]={R.drawable.ic_message,R.drawable.ic_camera,R.drawable.ic_rupees};
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DataB data=new DataB(MainActivity.this);
        data.open();
        idub=data.getData1();
        data.close();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setIcon(resorce[i]);
        }




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }

    @Override
    protected void onStop() {
        super.onStop();

        DataB data=new DataB(MainActivity.this);
        data.open();
        data.deleteEntry();
        data.close();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
           // new CheckAvialability().execute();

            HashMap hashMap = new HashMap<String, String>();
            hashMap.put("idu",idub);
            hashMap.put("logui","logout");



            //Toast.makeText(MainActivity.this,idub,Toast.LENGTH_SHORT).show();





            PostResponseAsyncTask task = new PostResponseAsyncTask(MainActivity.this, hashMap, new AsyncResponse() {

                public void processFinish(String s) {


                    try {
                        //Toast.makeText(MainActivity.this,s.toString(),Toast.LENGTH_SHORT).show();
                        JSONObject u=new JSONObject(s);
                        String yu=(String)u.getString("succ");
                        if(yu.trim().equals("succ"))
                        {
                            DataB data=new DataB(MainActivity.this);
                            data.open();
                            data.deleteEntry();
                            data.close();

                            finish();
                            //System.exit(0);

                        }
                        else{
                            Toast.makeText(MainActivity.this, "server not responding", Toast.LENGTH_SHORT).show();
                        }




                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                    //st.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();


                }

            });
            task.execute("http://payroll.venturesoftwares.org/Work/service/userout.php");

            task.setEachExceptionsHandler(new EachExceptionsHandler() {
                @Override
                public void handleIOException(IOException e) {
                    Toast.makeText(MainActivity.this, "time out", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void handleMalformedURLException(MalformedURLException e) {
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void handleProtocolException(ProtocolException e) {
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {
                    Toast.makeText(MainActivity.this, "handle pro", Toast.LENGTH_SHORT).show();
                }
            });







            return true;
        }



        return super.onOptionsItemSelected(item);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Fragment fragment=null;
            if(position==0){
                fragment = new Chatwith();
            }
            else if(position==1){
                fragment=new CameraClick();
            }else if(position==2){
                fragment = new Salary();
            }

            return fragment;


    }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }



    }
   /* class CheckAvialability extends AsyncTask<String, Void, JSONObject> {

        ProgressDialog p = new ProgressDialog(MainActivity.this);

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

            jsonObject = getJSONFromUrl("http://payroll.venturesoftwares.org/Work/service/userout.php");
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
            if (result != null) {
                try {



                    JSONObject qrjson = result.getJSONObject("logouty");
                    String yu=(String)qrjson.getString("succ");
                    if(yu.trim().equals("succ"))
                    {
                        DataB data=new DataB(MainActivity.this);
                        data.open();
                        data.deleteEntry();
                        data.close();

                        finish();
                        //System.exit(0);

                    }
                    else{
                        Toast.makeText(MainActivity.this, "server not responding", Toast.LENGTH_SHORT).show();
                    }




                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            } else {
                Toast.makeText(MainActivity.this, "Error in connection",
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
                String s = "logui=" + URLEncoder.encode("logout", charset);
                s += "&idu=" + URLEncoder.encode(idub, charset);

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
