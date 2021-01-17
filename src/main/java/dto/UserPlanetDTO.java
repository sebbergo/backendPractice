/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author sebas
 */
public class UserPlanetDTO {
    public String userName;
    public String planetName;
    public String planetClimate;
    public String planetTerrain;
    public String planetPopulation;

    public UserPlanetDTO(String userName, String planetName, String planetClimate, String planetTerrain, String planetPopulation) {
        this.userName = userName;
        this.planetName = planetName;
        this.planetClimate = planetClimate;
        this.planetTerrain = planetTerrain;
        this.planetPopulation = planetPopulation;
    }
    
    
}
