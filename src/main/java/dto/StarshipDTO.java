package dto;

public class StarshipDTO {

    public String name;
    public String model;
    public int cost_in_credits;

    public StarshipDTO(String name, String model, int cost_in_credits) {
        this.name = name;
        this.model = model;
        this.cost_in_credits = cost_in_credits;
    }
}
