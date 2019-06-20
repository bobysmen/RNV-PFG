package com.example.rnv_pfg.ui.patient.listAppointmentPerPatient;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.rnv_pfg.R;
import com.example.rnv_pfg.data.models.Appointment;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class ListAppointmentPatientAdapter extends ListAdapter<Appointment, ListAppointmentPatientAdapter.ViewHolder> {

    private final OnAppointmentClickListenerAdd onAppointmentClickListenerAdd;
    private final OnAppointmentClickListenerDelete onAppointmentClickListenerDelete;


    protected ListAppointmentPatientAdapter(OnAppointmentClickListenerAdd onAppointmentClickListenerAdd, OnAppointmentClickListenerDelete onAppointmentClickListenerDelete) {
        super(new DiffUtil.ItemCallback<Appointment>() {
            @Override
            public boolean areItemsTheSame(@NonNull Appointment oldItem, @NonNull Appointment newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Appointment oldItem, @NonNull Appointment newItem) {
                return (oldItem.getEmployee() == newItem.getEmployee()) &&
                        (oldItem.getPatient() == newItem.getPatient()) &&
                        TextUtils.equals(oldItem.getDate(), newItem.getDate());
            }
        });
        this.onAppointmentClickListenerAdd = onAppointmentClickListenerAdd;
        this.onAppointmentClickListenerDelete = onAppointmentClickListenerDelete;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.appointment_list_item, parent, false), onAppointmentClickListenerAdd, onAppointmentClickListenerDelete);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAppointmentPatientAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    protected Appointment getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItem(position).getId();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView lblDate;
        private final Button btnDiagnosis;
        private final Button btnDelete;

        public ViewHolder(@NonNull View itemView, OnAppointmentClickListenerAdd onAppointmentClickListenerAdd, OnAppointmentClickListenerDelete onAppointmentClickListenerDelete) {
            super(itemView);
            lblDate = ViewCompat.requireViewById(itemView, R.id.lblDate);
            btnDiagnosis = ViewCompat.requireViewById(itemView, R.id.btnDiagnosis);
            btnDelete = ViewCompat.requireViewById(itemView, R.id.btnDeleteAppointment);

            btnDiagnosis.setOnClickListener(v -> onAppointmentClickListenerAdd.onItemClick(getAdapterPosition()));
            btnDelete.setOnClickListener(v -> onAppointmentClickListenerDelete.onItemClick(getAdapterPosition()));
        }

        void bind(Appointment appointment){
            lblDate.setText(appointment.getDate());
        }
    }
}
