public class Product
{
    private String ID;
    private String name;
    private String description;
    private double cost;
    static private int IDSeed = 1;


    public Product(String ID, String name, String description, double cost) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.cost = cost;
    }

    public Product(String name, String description, double cost) {
        this.ID = this.genIDNum();
        this.name = name;
        this.description = description;
        this.cost = cost;
    }

    private String genIDNum() {
        String newID = "" + IDSeed;
        while(newID.length() < 8){
            newID = "0" + newID;
        }
        IDSeed++;

        return newID;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Product{" +
                "ID='" + ID + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                '}';
    }

    public String toCSVRecord() {
        return ID + ", " + name + ", " + description + ", " + cost;
    }
}
