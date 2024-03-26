package com.example.foodplanner.dataBaseHandling.Model.firebase;

import com.example.foodplanner.model.Pojos.ProductsPOJO;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.List;
@IgnoreExtraProperties
public class UserPojo implements Serializable {
    String image;
    String userName;
    String email;
    String passWord;
    List<ProductsPOJO> Favorites;
    List<ProductsPOJO> Saturday;
    List<ProductsPOJO> Sunday;
    List<ProductsPOJO> Monday;
    List<ProductsPOJO> Tuesday;
    List<ProductsPOJO> Thursday;
    List<ProductsPOJO> Wednesday;
    List<ProductsPOJO> Friday;


    public UserPojo(String image, String userName, String email, String passWord, List<ProductsPOJO> favorites, List<ProductsPOJO> saturday, List<ProductsPOJO> sunday, List<ProductsPOJO> monday, List<ProductsPOJO> tuesday, List<ProductsPOJO> thursday, List<ProductsPOJO> wednesday, List<ProductsPOJO> friday) {
        this.image = image;
        this.userName = userName;
        this.email = email;
        this.passWord = passWord;
        Favorites = favorites;
        Saturday = saturday;
        Sunday = sunday;
        Monday = monday;
        Tuesday = tuesday;
        Thursday = thursday;
        Wednesday = wednesday;
        Friday = friday;
    }



    public UserPojo() {

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public List<ProductsPOJO> getFavorites() {
        return Favorites;
    }

    public void setFavorites(List<ProductsPOJO> favorites) {
        Favorites = favorites;
    }

    public List<ProductsPOJO> getSaturday() {
        return Saturday;
    }

    public void setSaturday(List<ProductsPOJO> saturday) {
        Saturday = saturday;
    }

    public List<ProductsPOJO> getSunday() {
        return Sunday;
    }

    public void setSunday(List<ProductsPOJO> sunday) {
        Sunday = sunday;
    }

    public List<ProductsPOJO> getMonday() {
        return Monday;
    }

    public void setMonday(List<ProductsPOJO> monday) {
        Monday = monday;
    }

    public List<ProductsPOJO> getTuesday() {
        return Tuesday;
    }

    public void setTuesday(List<ProductsPOJO> tuesday) {
        Tuesday = tuesday;
    }

    public List<ProductsPOJO> getThursday() {
        return Thursday;
    }

    public void setThursday(List<ProductsPOJO> thursday) {
        Thursday = thursday;
    }

    public List<ProductsPOJO> getWednesday() {
        return Wednesday;
    }

    public void setWednesday(List<ProductsPOJO> wednesday) {
        Wednesday = wednesday;
    }

    public List<ProductsPOJO> getFriday() {
        return Friday;
    }

    public void setFriday(List<ProductsPOJO> friday) {
        Friday = friday;
    }


}
