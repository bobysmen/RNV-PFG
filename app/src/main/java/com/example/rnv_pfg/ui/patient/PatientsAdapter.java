package com.example.rnv_pfg.ui.patient;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.rnv_pfg.R;
import com.example.rnv_pfg.data.models.Patient;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class PatientsAdapter extends ListAdapter<Patient, PatientsAdapter.ViewHolder> {

    private final OnPatientClickListenerAdd onPatientClickListenerAdd;
    private final OnPatientClickListener onPatientClickListener;
    private final OnPatientClickListenerAppoinment onPatientClickListenerAppoinment;

    public PatientsAdapter(OnPatientClickListenerAdd onPatientClickListenerAdd, OnPatientClickListenerAppoinment onPatientClickListenerAppoinment, OnPatientClickListener onPatientClickListener) {
        super(new DiffUtil.ItemCallback<Patient>() {
            @Override
            public boolean areItemsTheSame(@NonNull Patient oldItem, @NonNull Patient newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Patient oldItem, @NonNull Patient newItem) {
                return TextUtils.equals(oldItem.getName(), newItem.getName()) &&
                        TextUtils.equals(oldItem.getSurname(), newItem.getSurname()) &&
                        TextUtils.equals(oldItem.getPhone(), newItem.getPhone()) &&
                        TextUtils.equals(oldItem.getEmail(), newItem.getEmail()) &&
                        TextUtils.equals(oldItem.getAddress(), newItem.getAddress());
            }
        });
        this.onPatientClickListenerAdd = onPatientClickListenerAdd;
        this.onPatientClickListenerAppoinment = onPatientClickListenerAppoinment;
        this.onPatientClickListener = onPatientClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.patient_list_item, parent, false), onPatientClickListenerAdd, onPatientClickListenerAppoinment, onPatientClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientsAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    protected Patient getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItem(position).getId();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView lblName;
        private final TextView lblSurname;
        private final Button btnAddAppointment;
        private final Button btnListAppointment;

        public ViewHolder(@NonNull View itemView, OnPatientClickListenerAdd onPatientClickListenerAdd, OnPatientClickListenerAppoinment onPatientClickListenerAppoinment, OnPatientClickListener onPatientClickListener) {
            super(itemView);
            lblName = ViewCompat.requireViewById(itemView, R.id.lblName);
            lblSurname = ViewCompat.requireViewById(itemView, R.id.lblSurname);
            btnAddAppointment = ViewCompat.requireViewById(itemView, R.id.btnAddAppointment);
            btnListAppointment = ViewCompat.requireViewById(itemView, R.id.btnListAppointment);

            btnAddAppointment.setOnClickListener(v -> onPatientClickListenerAdd.onItemClick(getAdapterPosition()));
            btnListAppointment.setOnClickListener(v -> onPatientClickListenerAppoinment.onItemClick(getAdapterPosition()));
            itemView.setOnClickListener(v -> onPatientClickListener.onItemClick(getAdapterPosition()));
        }

        void bind(Patient patient){
            lblName.setText(patient.getName());
            lblSurname.setText(patient.getSurname());
        }
    }


}
