package krunal.com.example.cameraapp.ui;

public class user {

    int id;
    String name;
    String plate;

    public user(){

    }

    public user(int id, String name, String plate) {
        this.id = id;
        this.name = name;
        this.plate = plate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPlate() {
        return plate;
    }
}
