package file.upload.testslide;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Salary extends android.support.v4.app.Fragment {
    CalendarView calendar;
    Button df,clearall;
    TextView ghu;
    String idu;
    Spinner syear,smonth;
    String ios="6/22/2016";
    String year[]={"2016","2017"};
    String month[]={"January","February","March","April","May","June","June","July","August","Septemper","October","November","December"};
    ArrayAdapter<String> acourse;
    ArrayAdapter<String> asem;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cfg,
                container, false);
        syear=(Spinner)view.findViewById(R.id.year);
        smonth=(Spinner)view.findViewById(R.id.month);
        df=(Button)view.findViewById(R.id.salarycurrent);
        ghu=(TextView)view.findViewById(R.id.textcurrent);
        clearall=(Button)view.findViewById(R.id.clearrt);
        clearall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ghu.setText("");
            }
        });

        List<String> lyear= new ArrayList(Arrays.asList(year));
        List<String>  lmonth=new ArrayList(Arrays.asList(month));
        acourse=new  ArrayAdapter<String>(getActivity(),
                R.layout.spinner_item,lyear);
        acourse.setDropDownViewResource(R.layout.spinner_compo);
        syear.setAdapter(acourse);

        asem=new  ArrayAdapter<String>(getActivity(),
                R.layout.spinner_item,lmonth);
        acourse.setDropDownViewResource(R.layout.spinner_compo);
        smonth.setAdapter(asem);

        asem.setDropDownViewResource(R.layout.spinner_compo);

       // ghu.setText("6/6/2016");
        DataB data=new DataB(getActivity());
        data.open();
        idu=data.getData1();
        data.close();

       // initializeCalendar();
     df.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             Senddate();
         }
     });




        return view;
    }

    private void Senddate() {
       HashMap hashMap = new HashMap<String, String>();
        String hop=syear.getSelectedItem().toString();
        String hop1=smonth.getSelectedItem().toString();
        hashMap.put("idu",idu);
        hashMap.put("year",hop);
        hashMap.put("month",hop1);

        PostResponseAsyncTask task = new PostResponseAsyncTask(getActivity(), hashMap, new AsyncResponse() {

            public void processFinish(String s) {


                try {
                    JSONObject ioj=new JSONObject(s);
                    String succ= ioj.getString("succ");
                    if(succ.trim().contains("succ"))
                    {
                     String hui=ioj.getString("total");
                        ghu.setText(hui+"Rs");

                    }
                    if(succ.trim().contains("error"))
                    {

                        ghu.setText("not updated");
                    }



                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                //st.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();


            }

        });
        task.execute("http://payroll.venturesoftwares.org/Work/service/salary.php");

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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private String initializeCalendar() {


        // sets whether to show the week number.
        calendar.setShowWeekNumber(false);

        // sets the first day of week according to Calendar.
        // here we set Monday as the first day of the Calendar
        calendar.setFirstDayOfWeek(2);

        //The background color for the selected week.
        calendar.setSelectedWeekBackgroundColor(getResources().getColor(R.color.green));

        //sets the color for the dates of an unfocused month.
        calendar.setUnfocusedMonthDateColor(getResources().getColor(R.color.transparent));

        //sets the color for the separator line between weeks.
        calendar.setWeekSeparatorLineColor(getResources().getColor(R.color.transparent));

        //sets the color for the vertical bar shown at the beginning and at the end of the selected date.
        calendar.setSelectedDateVerticalBar(R.color.darkgreen);

        //sets the listener to be notified upon selected date change.
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            //show the selected date as a toast

            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                //Toast.makeText(getActivity(), day + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
                ghu.setText(day + "/" + month + "/" + year);
                ios=""+day+"/"+month+"/"+year;

            }
        });
        return ios;
    }

    }

