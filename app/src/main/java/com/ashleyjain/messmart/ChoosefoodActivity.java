package com.ashleyjain.messmart;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class ChoosefoodActivity extends AppCompatActivity {


    Button buttontoday;
    Button buttontomorrow;
    Button buttontomorrow2;
    Button buttontomorrow3;
    Button buttontomorrow4;
    Button buttontomorrow5;
    Button buttontomorrow6;
    Button buttontomorrow7;
    Calendar c = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
    DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    String formattedDate;
    GregorianCalendar gc = new GregorianCalendar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosefood);

        buttontoday = (Button)findViewById(R.id.buttontoday);
        buttontomorrow = (Button)findViewById(R.id.buttontomorrow);
        buttontomorrow2 = (Button)findViewById(R.id.buttontomorrow2);
        buttontomorrow3 = (Button)findViewById(R.id.buttontomorrow3);
        buttontomorrow4 = (Button)findViewById(R.id.buttontomorrow4);
        buttontomorrow5 = (Button)findViewById(R.id.buttontomorrow5);
        buttontomorrow6 = (Button)findViewById(R.id.buttontomorrow6);
        buttontomorrow7 = (Button)findViewById(R.id.buttontomorrow7);
        final Context context = ChoosefoodActivity.this;


        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 2);
        buttontomorrow2.setText(dateFormat.format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        buttontomorrow3.setText(dateFormat.format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        buttontomorrow4.setText(dateFormat.format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        buttontomorrow5.setText(dateFormat.format(calendar.getTime()));
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        buttontomorrow6.setText(dateFormat.format(calendar.getTime()));

        buttontomorrow7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog = ProgressDialog.show(context, "", "Working...", true);
                String url = "http://192.168.0.102/mess/index.php/ajaxactions";

                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Response", response);
                                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                //response JSON from url
                                /*try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    String message = jsonResponse.getString("name");
                                    //System.out.println("Message: " + message);
                                    //Toast.makeText(context, (String)message, Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } catch (JSONException e) {
                                    Toast.makeText(context, "yo error", Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                }*/

                            }
                        },
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, "yoyo error", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }

                ) {
                    @Override
                    public Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("action","getinit");
                        return params;
                    }
                };

                // add it to the RequestQueue
                Volley.newRequestQueue(context).add(postRequest);
            }
        });

        buttontoday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                Date today = calendar.getTime();
                String todayAsString = dateFormat.format(today);
                Toast.makeText(ChoosefoodActivity.this, todayAsString,
                        Toast.LENGTH_SHORT).show();
            }
        });
        buttontomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                String date = dateFormat.format(calendar.getTime());
                Toast.makeText(ChoosefoodActivity.this, date,
                        Toast.LENGTH_SHORT).show();
            }
        });
        buttontomorrow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, 2);
                String date = dateFormat.format(calendar.getTime());
                Toast.makeText(ChoosefoodActivity.this, date,
                        Toast.LENGTH_SHORT).show();
            }
        });
        buttontomorrow3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, 3);
                String date = dateFormat.format(calendar.getTime());
                Toast.makeText(ChoosefoodActivity.this, date,
                        Toast.LENGTH_SHORT).show();
            }
        });
        buttontomorrow4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, 4);
                String date = dateFormat.format(calendar.getTime());
                Toast.makeText(ChoosefoodActivity.this, date,
                        Toast.LENGTH_SHORT).show();
            }
        });
        buttontomorrow5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, 5);
                String date = dateFormat.format(calendar.getTime());
                Toast.makeText(ChoosefoodActivity.this, date,
                        Toast.LENGTH_SHORT).show();
            }
        });
        buttontomorrow6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, 6);
                String date = dateFormat.format(calendar.getTime());
                Toast.makeText(ChoosefoodActivity.this, date,
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}
