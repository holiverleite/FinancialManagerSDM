package br.edu.ifspsaocarlos.gerenciadordecontassdm.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.gerenciadordecontassdm.R;
import br.edu.ifspsaocarlos.gerenciadordecontassdm.adapter.AccountsListDetailAdapter;
import br.edu.ifspsaocarlos.gerenciadordecontassdm.model.Account;
import br.edu.ifspsaocarlos.gerenciadordecontassdm.model.Transaction;

public class AccountDetailActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static String EXTRA_ACCOUNT = "EXTRA_ACCOUNT";

    private ListView accountDetailListView;
    private List<Transaction> accountListTransactionsArray;
    private AccountsListDetailAdapter accountsListDetailAdapter;
    private Spinner statementType;

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

        // setting spinner to show the bank statement
        statementType = findViewById(R.id.selectStatementTypeSpinner);
        String[] expensesType = {"Por período","Por natureza","Por tipo"};
        ArrayAdapter<String> spinnerExpenseType = new ArrayAdapter<>(this,R.layout.spinner_item,expensesType);
        spinnerExpenseType.setDropDownViewResource(R.layout.spinner_item);
        statementType.setAdapter(spinnerExpenseType);
        statementType.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                accountsListDetailAdapter = new AccountsListDetailAdapter(this,accountListTransactionsArray);
                accountDetailListView.setAdapter(accountsListDetailAdapter);
                break;
            case 1:
                List<Transaction> auxArray = new ArrayList<>();
                List<Transaction> internalAuxArray = new ArrayList<>();
                for (Transaction tr : accountListTransactionsArray) {
                    if (tr.getCredit()) {
                        auxArray.add(tr);
                    } else {
                        internalAuxArray.add(tr);
                    }
                }

                auxArray.addAll(internalAuxArray);
                accountsListDetailAdapter = new AccountsListDetailAdapter(this,auxArray);
                accountDetailListView.setAdapter(accountsListDetailAdapter);
                break;
            case 2:
                List<Transaction> mainArray = new ArrayList<>();
                List<Transaction> alimentacaoArray = new ArrayList<>();
                List<Transaction> moradiaArray = new ArrayList<>();
                List<Transaction> transporteArray = new ArrayList<>();
                List<Transaction> entretenimentoArray = new ArrayList<>();
                List<Transaction> outrosArray = new ArrayList<>();
                List<Transaction> noTypeArray = new ArrayList<>();
                for (Transaction tr : accountListTransactionsArray) {
                    if (tr.getTransactionType().equals("Alimentação")) {
                        alimentacaoArray.add(tr);
                    } else
                    if (tr.getTransactionType().equals("Moradia")) {
                        moradiaArray.add(tr);
                    } else
                    if (tr.getTransactionType().equals("Transporte")) {
                        transporteArray.add(tr);
                    } else
                    if (tr.getTransactionType().equals("Entretenimento")) {
                        entretenimentoArray.add(tr);
                    } else
                    if (tr.getTransactionType().equals("Outros")) {
                        outrosArray.add(tr);
                    } else {
                        noTypeArray.add(tr);
                    }
                }

                mainArray.addAll(alimentacaoArray);
                mainArray.addAll(moradiaArray);
                mainArray.addAll(transporteArray);
                mainArray.addAll(entretenimentoArray);
                mainArray.addAll(outrosArray);
                mainArray.addAll(noTypeArray);

                accountsListDetailAdapter = new AccountsListDetailAdapter(this,mainArray);
                accountDetailListView.setAdapter(accountsListDetailAdapter);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        System.out.print("");
    }
}
