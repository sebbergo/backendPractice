/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.SwimStyle;

/**
 *
 * @author sebas
 */
public class SwimStyleDTO {
    public String style;

    public SwimStyleDTO(SwimStyle swimStyle) {
        this.style = swimStyle.getStyle();
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
    
}
