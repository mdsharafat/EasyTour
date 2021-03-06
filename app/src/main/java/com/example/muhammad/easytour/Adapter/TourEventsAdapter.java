package com.example.muhammad.easytour.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.muhammad.easytour.PojoClass.TourPlanPojo;
import com.example.muhammad.easytour.R;

import java.util.ArrayList;
import java.util.List;

public class TourEventsAdapter extends RecyclerView.Adapter<TourEventsAdapter.TourEventViewHolder> {

    ArrayList<TourPlanPojo> tourEventsList;
    Context context;

    public TourEventsAdapter(Context context, ArrayList<TourPlanPojo> tourEventsList) {
        this.context = context;
        this.tourEventsList = tourEventsList;
    }

    @NonNull
    @Override
    public TourEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_layout_tour_events,parent,false);
        TourEventViewHolder tourEventViewHolder = new TourEventViewHolder(view);


        return tourEventViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TourEventViewHolder holder, int position) {
         TourPlanPojo tourPlanPojo = tourEventsList.get(position);
         holder.tourFrom.setText(tourPlanPojo.getTourFrom());
         holder.tourTo.setText(tourPlanPojo.getTourTo());
         holder.approxBudget.setText(tourPlanPojo.getApproxBudget());
         holder.startingDate.setText(tourPlanPojo.getStartingDate());
         holder.endingDate.setText(tourPlanPojo.getEndingDate());

    }

    @Override
    public int getItemCount() {
        return tourEventsList.size();
    }

    class TourEventViewHolder extends RecyclerView.ViewHolder{
         TextView tourFrom;
         TextView tourTo;
        TextView approxBudget;
         TextView startingDate;
         TextView endingDate;


        public TourEventViewHolder(View itemView) {
            super(itemView);
            tourFrom = itemView.findViewById(R.id.rowLL_tourFromTV);
            tourTo = itemView.findViewById(R.id.rowLL_tourDestinationTV);
            approxBudget = itemView.findViewById(R.id.approxBudgetAmountTV);
            startingDate = itemView.findViewById(R.id.rowLL_srartDateTV);
            endingDate = itemView.findViewById(R.id.rowLL_endDateTV);

        }
    }
}
