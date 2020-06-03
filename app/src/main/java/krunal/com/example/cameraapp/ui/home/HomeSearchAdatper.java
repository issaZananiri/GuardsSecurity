package krunal.com.example.cameraapp.ui.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import krunal.com.example.cameraapp.R;
import krunal.com.example.cameraapp.data.Drivers;

public class HomeSearchAdatper extends RecyclerView.Adapter<HomeSearchAdatper.ViewHolder>{

    private Context context;
    private ArrayList<Drivers> drivers;

    public HomeSearchAdatper(Context context , ArrayList<Drivers> arrayList){
        this.context  = context;
        this.drivers = arrayList;
    }

     class ViewHolder extends RecyclerView.ViewHolder {

        TextView plate , name , plate_id , subtype , cartype,violation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            plate = itemView.findViewById(R.id.plate_vlaue);
            name = itemView.findViewById(R.id.driver_name_value);
            plate_id = itemView.findViewById(R.id.car_id_value);
            subtype = itemView.findViewById(R.id.subtype_value);
            cartype = itemView.findViewById(R.id.car_type_value);
            violation =itemView.findViewById(R.id.violation_value);
        }
    }

    @NonNull
    @Override
    public HomeSearchAdatper.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_home_adapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeSearchAdatper.ViewHolder viewHolder, int i) {

        Drivers drivers1 = drivers.get(i);

        viewHolder.cartype.setText(drivers1.getCarType());
        viewHolder.subtype.setText(drivers1.getSubType());
        viewHolder.plate_id.setText(drivers1.getID());
        viewHolder.name.setText(drivers1.getName());
        viewHolder.plate.setText(drivers1.getPlate());
        viewHolder.violation.setText(drivers1.getViolation());

    }

    @Override
    public int getItemCount() {
        return drivers.size();
    }


}
