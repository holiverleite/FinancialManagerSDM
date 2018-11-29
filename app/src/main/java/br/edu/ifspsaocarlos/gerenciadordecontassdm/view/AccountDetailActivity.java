package br.edu.ifspsaocarlos.gerenciadordecontassdm.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.gerenciadordecontassdm.R;
import br.edu.ifspsaocarlos.gerenciadordecontassdm.adapter.AccountsListDetailAdapter;
import br.edu.ifspsaocarlos.gerenciadordecontassdm.model.Account;
import br.edu.ifspsaocarlos.gerenciadordecontassdm.model.Transaction;

public class AccountDetailActivity extends AppCompatActivity {

    public static String EXTRA_ACCOUNT = "EXTRA_ACCOUNT";

    private ListView accountDetailListView;
    private List<Transaction> accountListTransactionsArray;
    private AccountsListDetailAdapter accountsListDetailAdapter;

    private Account account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);

        // Fill title Activity with the name of account
        account = new Account();
        Intent intent = getIntent();
        account = (Account) intent.getSerializableExtra(EXTRA_ACCOUNT);
        getSupportActionBar().setTitle(account.getName());

        // ListView Reference
        accountDetailListView = findViewById(R.id.accountDetailListView);
        accountListTransactionsArray = new ArrayList<>();
        accountListTransactionsArray = account.getTransactions();

        accountsListDetailAdapter = new AccountsListDetailAdapter(this,accountListTransactionsArray);
        accountDetailListView.setAdapter(accountsListDetailAdapter);

    }
}
