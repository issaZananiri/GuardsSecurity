package krunal.com.example.cameraapp.ui.slideshow;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import krunal.com.example.cameraapp.R;

import krunal.com.example.cameraapp.data.Parkings;

class parkingAdatper extends RecyclerView.Adapter<parkingAdatper.ViewHolder>{

    private Context context;
    private ArrayList<Parkings> parkings;

    public  parkingAdatper(Context context , ArrayList<Parkings> arrayList){
        this.context  = context;
        this.parkings = arrayList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView  name ,capacity,plates;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            capacity = itemView.findViewById(R.id.parking_capacity_value);
            name = itemView.findViewById(R.id.parking_name_value);
           plates = itemView.findViewById(R.id.parking_plates);

        }
    }

    @NonNull
    @Override
    public  parkingAdatper.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.parking_adapter,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  parkingAdatper.ViewHolder viewHolder, int i) {

        Parkings parkings1 = parkings.get(i);

        viewHolder.capacity.setText(parkings1.getCapacity());
        viewHolder.name.setText(parkings1.getName());
       viewHolder.plates.setText(parkings1.getCarplates());


    }

    @Override
    public int getItemCount() {
        return parkings.size();
    }


}
