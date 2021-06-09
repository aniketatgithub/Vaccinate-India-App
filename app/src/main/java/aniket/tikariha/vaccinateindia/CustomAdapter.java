package aniket.tikariha.vaccinateindia;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.myViewHolder> {

    @NonNull

    ArrayList<ArrayList<Vaccines>> numberslist = new ArrayList<ArrayList<Vaccines>>();
    ArrayList<Vaccines> numbers = new ArrayList<Vaccines>();


    VaccineItemClicked listner;

    public CustomAdapter(VaccineItemClicked listner) {
        this.listner = listner;
    }


    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_view, parent, false);
        myViewHolder newview = new myViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.onItemClicked(numbers.get(newview.getAdapterPosition()));
            }
        });
        return newview;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        String fees;

        Object currentitem = numbers.get(position);


        holder.available_capacity_dose1.setText("1st Dose : " + ((Vaccines) currentitem).available_capacity_dose1);
        holder.available_capacity_dose2.setText("2nd Dose : " + ((Vaccines) currentitem).available_capacity_dose2);
        holder.min_age_limit.setText("AGE : " + ((Vaccines) currentitem).min_age_limit + "+");
        holder.vaccine.setText(((Vaccines) currentitem).vaccine);
        holder.fee.setText(((Vaccines) currentitem).fee);
        holder.name.setText(((Vaccines) currentitem).name);


    }

    @Override
    public int getItemCount() {

        return numbers.size();

    }

    void updateVaccines(ArrayList<Vaccines> updatedVaccine) {
        Log.d(TAG, "true" + updatedVaccine.toString());
        numberslist.clear();

        numberslist.add(updatedVaccine);
        numbers = numberslist.get(0);
        notifyDataSetChanged();
    }

    interface VaccineItemClicked {
        void onItemClicked(Vaccines numbers);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        TextView available_capacity_dose1;
        TextView available_capacity_dose2;
        TextView min_age_limit;
        TextView vaccine;
        TextView fee;
        TextView name;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);


            available_capacity_dose1 = itemView.findViewById(R.id.available_capacity_dose1);
            available_capacity_dose2 = itemView.findViewById(R.id.available_capacity_dose2);
            min_age_limit = itemView.findViewById(R.id.min_age_limit);
            vaccine = itemView.findViewById(R.id.vaccine);
            fee = itemView.findViewById(R.id.fee);
            name = itemView.findViewById(R.id.name);


        }


    }


}