/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author seb
 */
public class PeopleDTO {
    public String name;
    public String height;
    public String hair_color;
    public String eye_color;
    public String gender;

    public PeopleDTO(String name, String height, String hair_color, String eye_color, String gender) {
        this.name = name;
        this.height = height;
        this.hair_color = hair_color;
        this.eye_color = eye_color;
        this.gender = gender;
    }
}
