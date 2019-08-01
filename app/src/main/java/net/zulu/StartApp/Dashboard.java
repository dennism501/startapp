package net.zulu.StartApp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {
    Button click;
    public  static TextView data;
    public  static TextView data2;
    public  static TextView data3;

    private Button BtSwitch;
    private Button BtSwitch2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        BtSwitch = (Button) findViewById(R.id.transaction);

        BtSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchActivity();
            }
        });

        BtSwitch2 = (Button) findViewById(R.id.clientButton);
        BtSwitch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchActivity2();
            }
        });

        click = (Button) findViewById(R.id.button);
        data = (TextView) findViewById(R.id.textView);
        data2 = (TextView) findViewById(R.id.textView2);
        data3 = (TextView) findViewById(R.id.textView3);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchIncome process = new fetchIncome();
                process.execute();

                fetchCost process2 = new fetchCost();
                process2.execute();

                fetchGross process3 = new fetchGross();
                process3.execute();


            }
        });

    }
    private void launchActivity() {

        Intent intent = new Intent(this, AddTransaction.class);
        startActivity(intent);
    }

    private void launchActivity2() {

        Intent intent = new Intent(this, ClientList.class);
        startActivity(intent);
    }
}