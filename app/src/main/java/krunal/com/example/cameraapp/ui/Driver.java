package krunal.com.example.cameraapp.ui;

public class Driver {

    int id;
    String name;
    String plate;
    String CarType;
    String SubType;
    int violation;

    public Driver(){

    }

    public Driver(int id, String name, String plate, String carType,int violation, String SubType) {
        this.id = id;
        this.name = name;
        this.plate = plate;
        this.CarType=CarType;
        this.SubType=SubType;
        this.violation=violation;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public int getViolation() { return violation; }

    public String getPlate() {
        return plate;
    }

    public String getCarType(){
        return CarType;
    }

    public String getSubType(){
        return SubType;
    }

}

