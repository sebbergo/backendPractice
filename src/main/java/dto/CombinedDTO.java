/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author lukas
 */
public class CombinedDTO {
    public String peopleName;
    public String planetName;
    public String speciesName;
    public String starshipName;
    public String vehicleName;

    public CombinedDTO(PeopleDTO people, PlanetDTO planet, SpeciesDTO species, StarshipDTO starship, VehicleDTO vehicle) {
        this.peopleName = people.name;
        this.planetName = planet.name;
        this.speciesName = species.name;
        this.starshipName = starship.name;
        this.vehicleName = vehicle.name;
    }

}
