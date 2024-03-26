package com.example.foodplanner.model.Pojos;

import java.util.List;

public class IngriendientPojoResponce {


    private List<IngeriedientPojo> meals;
    public List<IngeriedientPojo> getIngerdiants() {
        return meals;
    }
    public void setIngerdiants(List<IngeriedientPojo> ingerdiants) {
        this.meals = ingerdiants;
    }
}
