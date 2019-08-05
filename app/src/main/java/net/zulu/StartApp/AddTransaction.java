package net.zulu.StartApp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import net.zulu.StartApp.SQLite.Budget;
import net.zulu.StartApp.SQLite.DbHelper;
import net.zulu.startapp.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddTransaction extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    DbHelper db;

    EditText editTextId2, editTextAmount, cost;
    Spinner spinnerType, spinnerCname;
    ProgressBar progressBar;
    ListView listView;
    Button buttonAddTransaction;

    List<Transaction> transactionList;

    boolean isUpdating = false;

    ArrayList<String> listItems=new ArrayList<>();
    ArrayAdapter<String> adapter;
    Spinner sp;
    private Button mBtLaunchActivity;

    InputStream is = null;
    String result = null;
    String line = null;

    HttpURLConnection urlConnection = null;


    String[] cname;

    Spinner spinner1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        sp=(Spinner)findViewById(R.id.spinnerCname);
        adapter=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.txt,listItems);
        sp.setAdapter(adapter);
        db = new DbHelper(this);

        mBtLaunchActivity = (Button) findViewById(R.id.addbutton);

   /*     mBtLaunchActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchActivity();
            }
        });*/


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy);
        spinner1 = (Spinner) findViewById(R.id.spinnerCname);

        final List<String> list1 = new ArrayList<>();

        try {


            URL url = new URL("http://"+Api.IP+"/startappphp/Includes/demo_spinner.php");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();
            is = urlConnection.getInputStream();
        }
        catch (Exception e)
        {
            Log.e("Fail 1", e.toString());
        }

        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
        }
        catch(Exception e)
        {
            Log.e("Fail 2", e.toString());
        }


        try
        {
            JSONArray JA=new JSONArray(result);
            JSONObject json= null;

            cname = new String[JA.length()];


            for(int i=0;i<JA.length();i++)
            {
                json=JA.getJSONObject(i);
                cname[i] = json.getString("cname");

            }

            Toast.makeText(getApplicationContext(), "sss", Toast.LENGTH_LONG).show();

            for(int i = 0; i< cname.length; i++) {
                list1.add(cname[i]);
            }

            Toast.makeText(getApplicationContext(), "len", Toast.LENGTH_LONG).show();
            spinner_fn();
        }

        catch(Exception e) {
            Log.e("Fail 3", e.toString());
        }

        editTextId2 = (EditText) findViewById(R.id.editTextId);
        cost = findViewById(R.id.editTextAmount1);
        spinnerType = (Spinner) findViewById(R.id.spinnerType);
        spinnerCname = (Spinner) findViewById(R.id.spinnerCname);
        editTextAmount = (EditText) findViewById(R.id.editTextAmount);

        buttonAddTransaction = (Button) findViewById(R.id.buttonAddTransaction);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.listViewTransactions);

        transactionList = new ArrayList<>();


        buttonAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isUpdating) {
                    updateTransaction();
                } else {
                    createTransaction();
                }
            }
        });
        readTransactions();

    }

    private void spinner_fn(){

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(AddTransaction.this,
                android.R.layout.simple_spinner_item, cname);
        spinner1.setAdapter(dataAdapter1);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                spinner1.setSelection(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    private void launchActivity() {

        Intent intent = new Intent(this, ClientList.class);
        startActivity(intent);
    }

    private void createTransaction() {
        String type = spinnerType.getSelectedItem().toString();
        String cname2 = spinnerCname.getSelectedItem().toString();
        String amount = editTextAmount.getText().toString().trim();
        String strcost = cost.getText().toString().trim();

        if (TextUtils.isEmpty(amount)) {
            editTextAmount.setError("Please enter full amount");
            editTextAmount.requestFocus();
            return;
        }

        List<Budget> list = db.getAmount();
        list.get(0).setAmount(list.get(0).getAmount()-Integer.parseInt(strcost));
        db.updateBudget(list.get(0));

        HashMap<String, String> params = new HashMap<>();
        params.put("cname2", cname2);
        params.put("amount", amount);
        params.put("cost",strcost);
        params.put("type", type);

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CREATE_TRANSACTION, params, CODE_POST_REQUEST);
        request.execute();
    }

    private void readTransactions() {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_TRANSACTIONS, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void updateTransaction() {
        String id = editTextId2.getText().toString();
        String cname2 = spinnerCname.getSelectedItem().toString();
        String type = spinnerType.getSelectedItem().toString();
        String number = editTextAmount.getText().toString().trim();


        if (TextUtils.isEmpty(number)) {
            editTextAmount.setError("Please enter phone number");
            editTextAmount.requestFocus();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("cname2", cname2);
        params.put("number", number);
        params.put("type", type);



        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_UPDATE_TRANSACTION, params, CODE_POST_REQUEST);
        request.execute();

        buttonAddTransaction.setText("Add");

        editTextAmount.setText("");
        spinnerType.setSelection(0);
        spinnerCname.setSelection(0);

        isUpdating = false;
    }

    private void deleteTransaction(int id) {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_DELETE_TRANSACTION + id, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void refreshTransactionList(JSONArray Transactions) throws JSONException {
        transactionList.clear();

        for (int i = 0; i < Transactions.length(); i++) {
            JSONObject obj = Transactions.getJSONObject(i);
            transactionList.add(new Transaction(
                    obj.getInt("id"),
                    obj.getString("cname2"),
                    obj.getString("cost"),
                    obj.getString("type"),
                    obj.getString("amount"),
                    obj.getString("created_on")
            ));
        }

        TransactionAdapter adapter = new TransactionAdapter(transactionList);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

    }

    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {
        String url;
        HashMap<String, String> params;
        int requestCode;

        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    //Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    refreshTransactionList(object.getJSONArray("Transactions"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler requestHandler = new RequestHandler();

            if (requestCode == CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);


            if (requestCode == CODE_GET_REQUEST)
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }
    class TransactionAdapter extends ArrayAdapter<Transaction> {
        List<Transaction> transactionList;

        public TransactionAdapter(List<Transaction> transactionList) {
            super(AddTransaction.this, R.layout.layout_transaction_list, transactionList);
            this.transactionList = transactionList;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_transaction_list, null, true);

            TextView textViewCname2 = listViewItem.findViewById(R.id.textViewCname2);
            TextView textViewType = listViewItem.findViewById(R.id.textViewType);
            TextView textViewAmount = listViewItem.findViewById(R.id.textViewAmount);
            TextView textView = listViewItem.findViewById(R.id.cost_amount);
            TextView date = listViewItem.findViewById(R.id.transaction_date);

            //TextView textViewUpdate = listViewItem.findViewById(R.id.textViewUpdate);
            //TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);

            final Transaction transaction = transactionList.get(position);

            textViewCname2.setText(transaction.getCname2());
            textViewType.setText(transaction.getType());
            textViewAmount.setText(transaction.getCost());
            textView.setText(transaction.getAmount());

            date.setText(transaction.getDate());

            return listViewItem;
        }
    }
}
