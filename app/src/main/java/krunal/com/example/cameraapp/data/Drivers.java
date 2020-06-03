package krunal.com.example.cameraapp.data;

public class Drivers {

    private String CarType;
    private String ID;
    private String Name;
    private String SubType;
    private String plate;
    private String  Violation;

    public String getCarType() {
        return CarType;
    }

    public void setCarType(String carType) {
        CarType = carType;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
    public String getViolation() {
        return Violation;
    }

    public void setViolation(String Violation) {
        this.Violation = Violation;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSubType() {
        return SubType;
    }

    public void setSubType(String subType) {
        SubType = subType;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }
}
