package br.edu.ifspsaocarlos.gerenciadordecontassdm.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.ifspsaocarlos.gerenciadordecontassdm.R;
import br.edu.ifspsaocarlos.gerenciadordecontassdm.model.Account;

public class AccountsListAdapter extends ArrayAdapter<Account> {
    private LayoutInflater layoutInflater;

    public AccountsListAdapter(Context context, List<Account> accountList) {
        super(context, R.layout.layout_view_account_adapter, accountList);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_view_account_adapter, null);

            holder = new Holder();
            holder.accountNameTextView = convertView.findViewById(R.id.accountNameTextView);
            holder.amountTextView = convertView.findViewById(R.id.amountTextView);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        Account account = getItem(position);

        holder.accountNameTextView.setText(account.getName());
        holder.amountTextView.setText("R$" + account.getAmount());

        if ((long)Double.parseDouble(account.getAmount()) > 0) {
            holder.amountTextView.setTextColor(Color.GREEN);
        } else {
            holder.amountTextView.setTextColor(Color.BLACK);
        }

        return convertView;
    }

    private class Holder {
        public TextView accountNameTextView;
        public TextView amountTextView;
    }
}
