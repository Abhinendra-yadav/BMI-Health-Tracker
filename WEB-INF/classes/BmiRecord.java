
public class BmiRecord {
    private double weight;
    private double height;
    private double bmiValue;
    private String category;
    private String date;

    public BmiRecord(double weight, double height, double bmiValue, String category, String date) {
        this.weight = weight;
        this.height = height;
        this.bmiValue = bmiValue;
        this.category = category;
        this.date = date;
    }

    public double getWeight() { return weight; }
    public double getHeight() { return height; }
    public double getBmiValue() { return bmiValue; }
    public String getCategory() { return category; }
    public String getDate() { return date; }
}