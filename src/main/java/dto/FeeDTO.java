/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Fee;

/**
 *
 * @author sebas
 */
public class FeeDTO {
    public String price;

    public FeeDTO(Fee fee) {
        this.price = fee.getPrice();
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    
}
