package dto;

public class VehicleDTO {
    
    public String name;
    public String model;
    public String manufacturer;
    public String cost_in_credits;

    public VehicleDTO(String name, String model, String manufacturer, String cost_in_credits) {
        this.name = name;
        this.model = model;
        this.manufacturer = manufacturer;
        this.cost_in_credits = cost_in_credits;
    }
}
