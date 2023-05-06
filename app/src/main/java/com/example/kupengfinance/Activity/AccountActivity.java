package com.example.kupengfinance.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kupengfinance.API.Account_Model_Cash;
import com.example.kupengfinance.API.RetrofitInterface;
import com.example.kupengfinance.API.Signup_model;
import com.example.kupengfinance.API.Spinner_Model;
import com.example.kupengfinance.Adapter.SpinnerAdapter;
import com.example.kupengfinance.R;
import com.google.gson.internal.bind.ArrayTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountActivity extends AppCompatActivity {

    ImageView cancelaccount;
    EditText accnameEdt, accamountEdt;
    Button confirmBtn;
    Spinner spinner;
    ArrayList<Spinner_Model> spinner_models;
    SpinnerAdapter adapter;
    String val;
    String userId = "48";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        accnameEdt= (EditText) findViewById(R.id.accname);
        accamountEdt = (EditText) findViewById(R.id.accamount);
        spinner = (Spinner) findViewById(R.id.spin);

        cancelaccount = (ImageView) findViewById(R.id.cancelaccount);
        cancelaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent (AccountActivity.this, MainActivity.class);
                startActivity(loginIntent);
            }
        });

        confirmBtn = (Button) findViewById(R.id.btnconfirm);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (val.equals("Select One") || accnameEdt.getText().toString().equals("") || accamountEdt.getText().toString().equals("")){
                    Toast.makeText(AccountActivity.this, "Please Fill The Fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(val.equals("Cash") || accnameEdt.getText().toString().equals("") || accamountEdt.getText().toString().equals("")){
                    Toast.makeText(AccountActivity.this, "Please Fill The Fields", Toast.LENGTH_SHORT).show();
                    if (val.equals("Cash")) {
                        handleAccountCash(userId, accnameEdt.getText().toString(), accamountEdt.getText().toString());
                    }
                }
            }
        });
        spinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            val = spinner.getSelectedItem().toString();
                        }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
    }
    private void handleAccountCash(String userId, String cashName, String cashBalance) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://kucing-finance-backend-production.up.railway.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // below line is to create an instance for our retrofit api class.
        RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);

        Account_Model_Cash account_model_cash = new Account_Model_Cash(userId, cashName, cashBalance);
//        Log.i(username.toString(),"Username");
//        Log.i(email.toString(),"Email");
//        Log.i(password.toString(),"Password");
        Call<Account_Model_Cash> call = retrofitInterface.addCash(account_model_cash);

        call.enqueue(new Callback<Account_Model_Cash>() {
            @Override
            public void onResponse(Call<Account_Model_Cash> call, Response<Account_Model_Cash> response) {
                if (response.code() == 201) {
                    Toast.makeText(AccountActivity.this, "Cash Added", Toast.LENGTH_LONG).show();
                    Intent loginIntent = new Intent(AccountActivity.this, AccountActivity.class);
                    startActivity(loginIntent);
                } else if (response.code() == 400) {
                    Toast.makeText(AccountActivity.this, "Already Registered", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(AccountActivity.this, "API Reached", Toast.LENGTH_SHORT).show();

                // we are getting response from our body
                // and passing it to our modal class.
                Account_Model_Cash responseFromAPI = response.body();

                // on below line we are getting our data from modal class
                // and adding it to our string.
//                String responseString = "Response Code : " + response.code() + "\nName : " + responseFromAPI.getUsername() + "\n" + "Email : " + responseFromAPI.getEmail();

                // below line we are setting our
                // string to our text view.
                Log.i(String.valueOf(response.code()), "ingfo");

            }


            @Override
            public void onFailure(Call<Account_Model_Cash> call, Throwable t) {
                Toast.makeText(AccountActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initList()
    {
        spinner_models = new ArrayList<>();
        spinner_models.add(new Spinner_Model("Card"));
        spinner_models.add(new Spinner_Model("Cash"));
    }
}
