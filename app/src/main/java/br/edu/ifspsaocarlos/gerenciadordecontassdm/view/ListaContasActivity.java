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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.gerenciadordecontassdm.R;
import br.edu.ifspsaocarlos.gerenciadordecontassdm.adapter.AccountsListAdapter;
import br.edu.ifspsaocarlos.gerenciadordecontassdm.model.Account;
import br.edu.ifspsaocarlos.gerenciadordecontassdm.model.Transaction;

public class ListaContasActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private final int NEW_ACCOUNT_CODE = 0;
    private final int NEW_TRANSACTION = 1;

    // Constant to get value from another activity
    public static String EXTRA_ACCOUNT = "EXTRA_ACCOUNT";
    public static String EXTRA_TRANSACTION = "EXTRA_TRANSACTION";

    private ListView accountsListView;
    private List<Account> accountListArray;
    private AccountsListAdapter accountsListAdapter;

    private View createButtonAndTotalView;
    private Button createTransactionButton;
    private ImageView placeHolderImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contas);

        getSupportActionBar().setTitle("Minhas Contas");

        // ListView reference
        createButtonAndTotalView = findViewById(R.id.createTransactionAndTotalView);
        placeHolderImageView = findViewById(R.id.placeholderImage);

        accountsListView = findViewById(R.id.accountsListView);
        accountListArray = new ArrayList<>();

        accountsListAdapter = new AccountsListAdapter(this, accountListArray);
        accountsListView.setAdapter(accountsListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (accountListArray.isEmpty()) {
            placeHolderImageView.setVisibility(View.VISIBLE);
            createButtonAndTotalView.setVisibility(View.GONE);
        } else {
            placeHolderImageView.setVisibility(View.GONE);
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

        switch (requestCode) {
            case NEW_ACCOUNT_CODE:
                if (resultCode == RESULT_OK) {
                    Account newAccount = (Account) data.getSerializableExtra(EXTRA_ACCOUNT);

                    if (newAccount != null) {
                        accountListArray.add(newAccount);
                        accountsListAdapter.notifyDataSetChanged();
                        Toast.makeText(this, R.string.new_account_created_message, Toast.LENGTH_SHORT).show();
                    }
                }

                if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this,R.string.canceled_account_message,Toast.LENGTH_SHORT).show();
                }

                break;
            case NEW_TRANSACTION:
                if (resultCode == RESULT_OK) {
                    Transaction newTransaction = (Transaction) data.getSerializableExtra(EXTRA_TRANSACTION);

                    if (newTransaction != null) {
                        int position = 0;
                        for (Account account : accountListArray) {
                            if (account.getName().matches(newTransaction.getAccountName())) {

                                account.addTransaction(newTransaction);
                                account.executeTransaction(newTransaction.getCredit(), newTransaction.getValue());

                                View accountCell = accountsListView.getChildAt(position);
                                TextView amountAccount = accountCell.findViewById(R.id.amountTextView);
                                amountAccount.setText("R$" + account.getAmount());
                            }
                            position++;
                        }
                    }
                }
                break;
        }
    }

    // Other Methods
    public void loadTotalAmount(String totalAmount) {
        // Set the title of the Total cell and the amount
        View totalView = createButtonAndTotalView.findViewById(R.id.totalAmountView);
        TextView totalTextView = totalView.findViewById(R.id.accountNameTextView);
        TextView amountTextView = totalView.findViewById(R.id.amountTextView);
        totalTextView.setText(R.string.total_string);
        amountTextView.setText("R$" + totalAmount);

        // get createButton Reference
        createTransactionButton = createButtonAndTotalView.findViewById(R.id.createTransactionButton);
        createTransactionButton.setOnClickListener(this);
    }

    // Interface implementations
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.createTransactionButton) {
            Intent createTransaction = new Intent(this, TransactionActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(EXTRA_TRANSACTION, (Serializable) accountListArray);
            createTransaction.putExtras(bundle);
            startActivityForResult(createTransaction,NEW_TRANSACTION);
        }
    }
}