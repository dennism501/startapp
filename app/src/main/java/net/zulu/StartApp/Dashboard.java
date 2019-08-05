package net.zulu.StartApp;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.zulu.StartApp.SQLite.DbHelper;

import org.w3c.dom.Text;

public class Dashboard extends AppCompatActivity {
    Button click;
    public static TextView data;
    public static TextView data2;
    public static TextView data3;
    private TextView budgetamount;
    private DbHelper db;
    SwipeRefreshLayout mSwipeRefreshLayout;
    LinearLayout client_add;
    LinearLayout budget;

    private Button BtSwitch;
    private Button BtSwitch2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        db = new DbHelper(this);

        BtSwitch = (Button) findViewById(R.id.transaction);
        mSwipeRefreshLayout = findViewById(R.id.swipeToRefresh);
        client_add = findViewById(R.id.clientadd);
        budget = findViewById(R.id.linear);
        budgetamount = findViewById(R.id.budget_dashboard);


        //budgetamount.setText("Current Budget: "+db.getAmount().get(0).getAmount());

        BtSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchActivity();
            }
        });

        client_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                launchActivity2();

            }
        });

        budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), BudgetActivity.class);
                startActivity(intent);

            }
        });

      /* // BtSwitch2 = (Button) findViewById(R.id.clientButton);
        BtSwitch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchActivity2();
            }
        });*/


        //click = (Button) findViewById(R.id.button);
        data = (TextView) findViewById(R.id.textView);
        data2 = (TextView) findViewById(R.id.textView2);
        data3 = (TextView) findViewById(R.id.textView3);

   /*     click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchIncome process = new fetchIncome();
                process.execute();

                fetchCost process2 = new fetchCost();
                process2.execute();

                fetchGross process3 = new fetchGross();
                process3.execute();


            }
        });*/

        fetchIncome process = new fetchIncome();
        process.execute();

        fetchCost process2 = new fetchCost();
        process2.execute();

        fetchGross process3 = new fetchGross();
        process3.execute();


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                fetchIncome process = new fetchIncome();
                process.execute();

                fetchCost process2 = new fetchCost();
                process2.execute();

                fetchGross process3 = new fetchGross();
                process3.execute();

                budgetamount.setText("Current Budget: "+db.getAmount().get(0).getAmount());

                mSwipeRefreshLayout.setRefreshing(false);

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