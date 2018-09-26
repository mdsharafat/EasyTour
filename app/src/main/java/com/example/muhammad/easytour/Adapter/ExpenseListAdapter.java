package com.example.muhammad.easytour.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.muhammad.easytour.PojoClass.AddExpensePojo;
import com.example.muhammad.easytour.R;

import java.util.ArrayList;
import java.util.List;

public class ExpenseListAdapter extends RecyclerView.Adapter<ExpenseListAdapter.ExpenseListViewHolder> {

    ArrayList<AddExpensePojo> expensePojoList;
    Context context;

    public ExpenseListAdapter(Context context, ArrayList<AddExpensePojo> expensePojoList) {
        this.context = context;
        this.expensePojoList = expensePojoList;
    }

    @NonNull
    @Override
    public ExpenseListAdapter.ExpenseListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_layout_expense_list,parent,false);
        ExpenseListAdapter.ExpenseListViewHolder expenseListViewHolder = new ExpenseListAdapter.ExpenseListViewHolder(view);


        return expenseListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseListAdapter.ExpenseListViewHolder holder, int position) {
        AddExpensePojo addExpensePojo = expensePojoList.get(position);
        holder.expenseDetails.setText(addExpensePojo.getmExpenseDetails());
        holder.expenseAmount.setText(addExpensePojo.getmExpenseAmount());
        //holder.dateTime.setText(String.valueOf(addExpensePojo.getDateTime()));

    }

    @Override
    public int getItemCount() {
        return expensePojoList.size();
    }

    class ExpenseListViewHolder extends RecyclerView.ViewHolder{
        TextView expenseDetails;
        TextView expenseAmount;
       // TextView dateTime;



        public ExpenseListViewHolder(View itemView) {
            super(itemView);
            expenseDetails = itemView.findViewById(R.id.rowLL_expenseDetailsTV);
            expenseAmount = itemView.findViewById(R.id.rowLL_expenseAmountTV);
           // dateTime = itemView.findViewById(R.id.dateOfExpense);

        }
    }


}
