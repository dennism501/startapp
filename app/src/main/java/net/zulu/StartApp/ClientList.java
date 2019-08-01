package net.zulu.StartApp;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;

public class ClientList extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    EditText editTextId, editTextCname, editTextNumber, editTextFname;
    ProgressBar progressBar;
    ListView listView;
    Button buttonAddUpdate;

    List<Record> recordList;

    boolean isUpdating = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientlist);

        editTextId = (EditText) findViewById(R.id.editTextId);
        editTextCname = (EditText) findViewById(R.id.editTextCname);
        editTextFname = (EditText) findViewById(R.id.editTextFname);
        editTextNumber = (EditText) findViewById(R.id.editTextNumber);

        buttonAddUpdate = (Button) findViewById(R.id.buttonAddUpdate);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.listViewRecords);

        recordList = new ArrayList<>();


        buttonAddUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isUpdating) {
                    updateRecord();
                } else {
                    createRecord();
                }
            }
        });
        readRecords();


    }




    private void createRecord() {
        String cname = editTextCname.getText().toString().trim();
        String fname = editTextFname.getText().toString().trim();
        String number = editTextNumber.getText().toString().trim();

        if (TextUtils.isEmpty(cname)) {
            editTextCname.setError("Please enter Company name");
            editTextCname.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(fname)) {
            editTextFname.setError("Please enter full name");
            editTextFname.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(number)) {
            editTextNumber.setError("Please enter phone number");
            editTextNumber.requestFocus();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("cname", cname);
        params.put("fname", fname);
        params.put("number", number);

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CREATE_RECORD, params, CODE_POST_REQUEST);
        request.execute();
    }

    private void readRecords() {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_RECORDS, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void updateRecord() {
        String id = editTextId.getText().toString();
        String cname = editTextCname.getText().toString().trim();
        String fname = editTextFname.getText().toString().trim();
        String number = editTextNumber.getText().toString().trim();


        if (TextUtils.isEmpty(cname)) {
            editTextCname.setError("Please enter company name");
            editTextCname.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(fname)) {
            editTextFname.setError("Please enter full name");
            editTextFname.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(number)) {
            editTextNumber.setError("Please enter phone number");
            editTextNumber.requestFocus();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("cname", cname);
        params.put("fname", fname);
        params.put("number", number);


        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_UPDATE_RECORD, params, CODE_POST_REQUEST);
        request.execute();

        buttonAddUpdate.setText("Add");

        editTextCname.setText("");
        editTextFname.setText("");
        editTextNumber.setText("");

        isUpdating = false;
    }

    private void deleteRecord(int id) {
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_DELETE_RECORD + id, null, CODE_GET_REQUEST);
        request.execute();
    }

    private void refreshRecordList(JSONArray records) throws JSONException {
        recordList.clear();

        for (int i = 0; i < records.length(); i++) {
            JSONObject obj = records.getJSONObject(i);

            recordList.add(new Record(
                    obj.getInt("id"),
                    obj.getString("cname"),
                    obj.getString("fname"),
                    obj.getString("number")
            ));
        }

        RecordAdapter adapter = new RecordAdapter(recordList);
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
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
                    refreshRecordList(object.getJSONArray("records"));
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

    class RecordAdapter extends ArrayAdapter<Record> {
        List<Record> recordList;

        public RecordAdapter(List<Record> recordList) {
            super(ClientList.this, R.layout.layout_clients_list, recordList);
            this.recordList = recordList;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View listViewItem = inflater.inflate(R.layout.layout_clients_list, null, true);

            TextView textViewName = listViewItem.findViewById(R.id.textViewCname);
            TextView textViewFname = listViewItem.findViewById(R.id.textViewFname);
            TextView textViewNumber = listViewItem.findViewById(R.id.textViewNumber);

            TextView textViewUpdate = listViewItem.findViewById(R.id.textViewUpdate);
            TextView textViewDelete = listViewItem.findViewById(R.id.textViewDelete);

            final Record record = recordList.get(position);

            textViewName.setText(record.getCname());
            textViewFname.setText(record.getFname());
            textViewNumber.setText(record.getNumber());

            textViewUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isUpdating = true;
                    editTextId.setText(String.valueOf(record.getId()));
                    editTextCname.setText(record.getCname());
                    editTextFname.setText(record.getFname());
                    editTextNumber.setText(record.getNumber());
                    buttonAddUpdate.setText("Update");
                }
            });

            textViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(ClientList.this);

                    builder.setTitle("Delete " + record.getCname())
                            .setMessage("Are you sure you want to delete it?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteRecord(record.getId());
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            });

            return listViewItem;
        }
    }
}