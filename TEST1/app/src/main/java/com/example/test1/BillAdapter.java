package com.example.test1;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BillAdapter  extends RecyclerView.Adapter<BillAdapter.BillView> {
    ArrayList<Bill> billList = new ArrayList<>();
    private OnBillListener monBillClickListener;
    public BillAdapter(ArrayList<Bill> bills, OnBillListener onBillClickListener){
        this.billList = bills;
        this.monBillClickListener = onBillClickListener;
    }


    @NonNull
    @Override
    public BillView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_purchased_row,parent,false);

        return new BillView(view, monBillClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BillView holder, int position) {

        Bill bill= billList.get(position);
        holder.textstoreName.setText(bill.getStoreName());
        holder.textdate.setText(bill.getDate());
        if(bill.getProcessed().equals("no")){
            holder.process.setText("Processing...");
        }
        else{
            holder.process.setText("");
        }
    }
    @Override
    public int getItemCount() {
        return billList.size();
    }

    public class BillView extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView deleteBill;
        TextView textstoreName,textdate;
        OnBillListener onBillClickListener;
        TextView process;
        public BillView(@NonNull View itemView,OnBillListener onBillClickListener) {
            super(itemView);
            this.onBillClickListener = onBillClickListener;
            textstoreName = (TextView)itemView.findViewById(R.id.storeeName);
            textdate = (TextView)itemView.findViewById(R.id.displayDate);
            deleteBill = (ImageView)itemView.findViewById(R.id.delete_bill);
            process = (TextView)itemView.findViewById(R.id.process);
            itemView.setOnClickListener(this);
            deleteBill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    monBillClickListener.onDeleteClick(getAdapterPosition());
                }
            });
        }

        @Override
        public void onClick(View view) {
            monBillClickListener.onBillClick(getAdapterPosition());

        }


    }

    public interface OnBillListener{
        void onBillClick(int position);
        void onDeleteClick(int position);
    }

}

