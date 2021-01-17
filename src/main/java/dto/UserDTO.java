/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Fee;
import entities.SwimStyle;
import entities.User;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sebas
 */
public class UserDTO {

    public String userName;
    public String street;
    public List<FeeDTO> fees;
    public List<SwimStyleDTO> swimStyles;
    public String planetName;
    public String planetClimate;
    public String planetTerrain;
    public String planetPopulation;

    public UserDTO(User user) {
        this.userName = user.getUserName();

        if (user.getAddress() != null) {
            this.street = user.getAddress().getStreet();
        }

        this.fees = new ArrayList();
        if (!user.getFees().isEmpty()) {
            for (Fee fee : user.getFees()) {
                this.fees.add(new FeeDTO(fee));
            }
        }

        this.swimStyles = new ArrayList();
        if (!user.getSwimStyles().isEmpty()) {
            for (SwimStyle swimStyle : user.getSwimStyles()) {
                this.swimStyles.add(new SwimStyleDTO(swimStyle));
            }
        }

        if (user.getPlanet() != null) {
            this.planetName = user.getPlanet().getName();
            this.planetClimate = user.getPlanet().getClimate();
            this.planetTerrain = user.getPlanet().getTerrain();
            this.planetPopulation = user.getPlanet().getPopulation();
        }
    }

}
