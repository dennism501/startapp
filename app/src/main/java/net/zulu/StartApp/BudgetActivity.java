package net.zulu.StartApp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.zulu.StartApp.SQLite.Budget;
import net.zulu.StartApp.SQLite.DbHelper;

import java.util.List;

public class BudgetActivity extends AppCompatActivity {

    private EditText etBudgetAmount;
    private Button btnAddBudget;
    private DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        db = new DbHelper(this);

        etBudgetAmount = findViewById(R.id.budget_amount);
        btnAddBudget = findViewById(R.id.add_budget);


        btnAddBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Budget> list = db.getAmount();
                int current_amount = 0;
                if(list==null) {
                    current_amount = list.get(0).getAmount();
                }
                if (current_amount == 0) {
                    long id = db.insertBudget(Integer.parseInt(etBudgetAmount.getText().toString()));
                    Toast.makeText(v.getContext(), "Budget Stored", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


}
