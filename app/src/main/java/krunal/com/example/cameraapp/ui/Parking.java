package krunal.com.example.cameraapp.ui;

public class Parking {
    String Name;
   int Capacity;
  String[] CarPlates;

    public Parking(){

    }

    public Parking(String Name,int Capacity,String[] CarPlates){
            this.Name=Name;
            this.Capacity=Capacity;
            this.CarPlates=CarPlates;
    }

    public String getName() {
        return Name;
    }

    public int getCapacity() {
        return Capacity;
    }

    public String[] getCarPlates() {
        return CarPlates;
    }
}
