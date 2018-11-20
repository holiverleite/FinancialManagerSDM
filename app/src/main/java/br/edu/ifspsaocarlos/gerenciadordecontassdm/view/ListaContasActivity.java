package br.edu.ifspsaocarlos.gerenciadordecontassdm.view;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.gerenciadordecontassdm.R;
import br.edu.ifspsaocarlos.gerenciadordecontassdm.adapter.AccountsListAdapter;
import br.edu.ifspsaocarlos.gerenciadordecontassdm.model.Account;

public class ListaContasActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private final int NEW_ACCOUNT_CODE = 0;
    // Constant to get value from another activity
    public static String EXTRA_ACCOUNT = "EXTRA_ACCOUNT";

    private ListView accountsListView;
    private List<Account> accountListArray;
    private AccountsListAdapter accountsListAdapter;

    private View createButtonAndTotalView;
    private EditText nameTotalView;
    private EditText amountTotalView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contas);

        // ListView reference
        createButtonAndTotalView = findViewById(R.id.createTransactionAndTotalView);

        accountsListView = findViewById(R.id.accountsListView);
        accountListArray = new ArrayList<>();

        accountsListAdapter = new AccountsListAdapter(this, accountListArray);
        accountsListView.setAdapter(accountsListAdapter);

        if (accountListArray.isEmpty()) {
            createButtonAndTotalView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (accountListArray.isEmpty()) {
            createButtonAndTotalView.setVisibility(View.GONE);
        } else {
            createButtonAndTotalView.setVisibility(View.VISIBLE);
        }

        Double total = 0.0;
        for (Account account : accountListArray) {
            Double accountAmount = Double.parseDouble(account.getAmount());
            total = total + accountAmount;
        }

        loadTotalAmount(total.toString());
    }

    // Menu methods
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent createAccountIntent = new Intent(this, CreateAccountActivity.class);
        startActivityForResult(createAccountIntent,NEW_ACCOUNT_CODE);
        return true;
    }

    // Navigation
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            Account newAccount = (Account)data.getSerializableExtra(EXTRA_ACCOUNT);

            if (newAccount != null) {
                accountListArray.add(newAccount);
                accountsListAdapter.notifyDataSetChanged();
                Toast.makeText(this,R.string.new_account_created_message, Toast.LENGTH_SHORT).show();
            }
        }

        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this,R.string.canceled_account_message,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    // Other Methods
    public void loadTotalAmount(String totalAmount) {
        // Set the title of the Total cell and the amount
        View totalView = createButtonAndTotalView.findViewById(R.id.totalAmountView);
        TextView totalTextView = totalView.findViewById(R.id.accountNameTextView);
        TextView amountTextView = totalView.findViewById(R.id.amountTextView);
        totalTextView.setText(R.string.total_string);
        amountTextView.setText(totalAmount);

    }
}