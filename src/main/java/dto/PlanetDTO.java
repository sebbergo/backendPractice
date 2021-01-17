package dto;

import entities.Planet;

public class PlanetDTO {

    public String name;
    public String climate;
    public String terrain;
    public String population;

    public PlanetDTO(String name, String climate, String terrain, String population) {
        this.name = name;
        this.climate = climate;
        this.terrain = terrain;
        this.population = population;
    }
    
    public PlanetDTO(Planet planet){
        this.name = planet.getName();
        this.climate = planet.getClimate();
        this.terrain = planet.getTerrain();
        this.population = planet.getPopulation();
    }

}
