package com.example.foodplanner.model.Pojos;

import java.util.List;

public class IngeriedientPojo {
    private String idIngredient;
    private String strIngredient;
    private String strDescription;
    private String strType;
    private  String name;
    private String img;

    public String getIngredientPhotoUrl() {
        return img;
    }

    public void setIngredientPhotoUrl(String ingredientPhotoUrl) {
        this.img = img;
    }

    private String ingredientPhotoUrl;


    public IngeriedientPojo(String idIngredient, String strIngredient, String strDescription, String strType , String Name , String Img) {
        this.idIngredient = idIngredient;
        this.strIngredient = strIngredient;
        this.strDescription = strDescription;
        this.strType = strType;
        this.name = Name;
        this.img = Img;
    }

    public IngeriedientPojo(String name, String img) {
        this.name = name;
        this.img = img;
    }


    public String getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(String idIngredient) {
        this.idIngredient = idIngredient;
    }

    public String getStrIngredient() {
        return strIngredient;
    }

    public void setStrIngredient(String strIngredient) {
        this.strIngredient = strIngredient;
    }

    public String getStrDescription() {
        return strDescription;
    }

    public void setStrDescription(String strDescription) {
        this.strDescription = strDescription;
    }

    public String getStrType() {
        return strType;
    }

    public void setStrType(String strType) {
        this.strType = strType;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
