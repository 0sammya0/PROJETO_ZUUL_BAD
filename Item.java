public class Item {

    private String description_item;
    private double weigth_item;

    public Item(String description, double weigth){
        this.description_item = description;
        this.weigth_item = weigth;
    }

    public String getDescription_item() {
        return description_item;
    }
    public double getWeigth_item() {
        return weigth_item;
    }

    public String getItem(){
        return (description_item +" | Weight: " + weigth_item+ "g");
    }

}
