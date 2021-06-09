package aniket.tikariha.vaccinateindia;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CustomAdapter.VaccineItemClicked {
    public static RecyclerView recyclerView;
    public static TextView showdatetv;
    private static String newdate;
    ArrayList<Vaccines> numbers;
    CustomAdapter customAdapter;
    EditText pincode;
    EditText datee;
    DatePicker simpleDatePicker;
    TextView textView;
    TextView textView2;

    public static void setdate(String date) {
        newdate = date;
    }

    public void btnclick() {
        fetchdata();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showdatetv = findViewById(R.id.showdateTv);
        recyclerView = findViewById(R.id.recylerView);
        pincode = findViewById(R.id.pincode);
        textView2 = findViewById(R.id.notavail);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        customAdapter = new CustomAdapter(this);
        recyclerView.setAdapter(customAdapter);


    }

    void fetchdata() {
        recyclerView.setVisibility(View.GONE);


        String newpincode = pincode.getText().toString();
        //change this code


        String url = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByPin?pincode=" + newpincode + "&date=" + newdate;
        Log.d("tag", "onErrorResponse: called");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {


                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("sessions");
                            if (jsonArray.length() == 0) {

                                textView2.setText("No vaccine available! Try another Pin/Date");
                                textView2.setVisibility(View.VISIBLE);

                            } else {
                                textView2.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                ArrayList<Vaccines> vaccinesArray = new ArrayList<Vaccines>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject vaccines = jsonArray.getJSONObject(i);

                                    Vaccines vacciness = new Vaccines(vaccines.getString("available_capacity_dose1"), vaccines.getString("available_capacity_dose2"), vaccines.getString("min_age_limit"), vaccines.getString("vaccine"), vaccines.getString("fee_type"), vaccines.getString("name"), vaccines.getString("block_name"));
                                    vaccinesArray.add(vacciness);

                                }
                                customAdapter.updateVaccines(vaccinesArray);
                            }
                        } catch (JSONException e) {
                            Log.d("tag", "onErrorResponse: notnoice");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("tag", "onErrorResponse: errorrr");

                        Toast.makeText(MainActivity.this, "Internet Connection/Wrong Pin code/Wrong Date format", Toast.LENGTH_SHORT).show();
                    }
                });
// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onItemClicked(Vaccines item) {
        //Toast.makeText(this, "noice", Toast.LENGTH_SHORT).show();

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse("https://selfregistration.cowin.gov.in/"));

    }


    public void newbutton(View view) {
        Intent intent = new Intent(this, MainActivity2.class);

        startActivity(intent);
    }

    public void showDatePickerDialog(View v) {

        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");


    }


}
