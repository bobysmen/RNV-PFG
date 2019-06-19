package com.example.rnv_pfg.ui.appointmentsDaily;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rnv_pfg.R;
import com.example.rnv_pfg.data.models.Appointment;
import com.example.rnv_pfg.data.models.Patient;
import com.example.rnv_pfg.data.remote.ApiService;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentDailyAdapter extends ListAdapter<Appointment, AppointmentDailyAdapter.ViewHolder> {

    private final OnAppointmentDailyClickListenerDiagnosis onAppointmentDailyClickListenerDiagnosis;

    public AppointmentDailyAdapter(OnAppointmentDailyClickListenerDiagnosis onAppointmentDailyClickListenerDiagnosis) {
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
        this.onAppointmentDailyClickListenerDiagnosis = onAppointmentDailyClickListenerDiagnosis;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_appointment_daily_item, parent, false), onAppointmentDailyClickListenerDiagnosis);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentDailyAdapter.ViewHolder holder, int position) {
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


        private final TextView lblName;
        private final TextView lblSurname;
        private final TextView lblDate;
        private final Button btnDiagnosis;

        public ViewHolder(@NonNull View itemView, OnAppointmentDailyClickListenerDiagnosis onAppointmentDailyClickListenerDiagnosis) {
            super(itemView);
            lblName = ViewCompat.requireViewById(itemView, R.id.lblName);
            lblSurname = ViewCompat.requireViewById(itemView, R.id.lblSurname);
            lblDate = ViewCompat.requireViewById(itemView, R.id.lblDate);
            btnDiagnosis = ViewCompat.requireViewById(itemView, R.id.btnDiagnosis);

            btnDiagnosis.setOnClickListener(v -> onAppointmentDailyClickListenerDiagnosis.onItemClick(getAdapterPosition()));
        }

        void bind(Appointment appointment){
            callPatient(appointment);
            lblDate.setText(appointment.getDate());
        }

        private void callPatient(Appointment appointment) {
            //TODO si hay tiempo cambiar esto por una variable que almacene el nombre y el apellido en appointment
            Call<Patient> call = ApiService.getInstance(lblName.getContext()).getApi().getPatientById(appointment.getPatient());
            call.enqueue(new Callback<Patient>() {
                @Override
                public void onResponse(Call<Patient> call, Response<Patient> response) {
                    if(response.body() != null && response.isSuccessful()){
                        lblName.setText(response.body().getName());
                        lblSurname.setText(response.body().getSurname());
                    }else{
                        Toast.makeText(lblName.getContext(),"A ocurrido un error", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Patient> call, Throwable t) {

                }
            });
        }
    }
}
