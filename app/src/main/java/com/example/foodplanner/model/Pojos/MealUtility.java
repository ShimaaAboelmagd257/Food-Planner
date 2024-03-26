package com.example.foodplanner.model.Pojos;

public class MealUtility {

    public static String getStrIngredient(ProductsPOJO meals, int i) {

                if (meals != null) {
                    switch (i) {
                        case 1:
                            return meals.getStrIngredient1();
                        case 2:
                            return meals.getStrIngredient2();
                        case 3:
                            return meals.getStrIngredient3();
                        case 4:
                            return meals.getStrIngredient4();
                        case 5:
                            return meals.getStrIngredient5();
                        case 6:
                            return meals.getStrIngredient6();
                        case 7:
                            return meals.getStrIngredient7();
                        case 8:
                            return meals.getStrIngredient8();
                        case 9:
                            return meals.getStrIngredient9();
                        case 10:
                            return meals.getStrIngredient10();
                        case 11:
                            return meals.getStrIngredient11();
                        case 12:
                            return meals.getStrIngredient12();
                        case 13:
                            return meals.getStrIngredient13();
                        case 14:
                            return meals.getStrIngredient14();
                        case 15:
                            return meals.getStrIngredient15();
                        case 16:
                            return meals.getStrIngredient16();
                        case 17:
                            return meals.getStrIngredient17();
                        case 18:
                            return meals.getStrIngredient18();
                        case 19:
                            return meals.getStrIngredient19();
                        case 20:
                            return meals.getStrIngredient20();
                        default:
                            return ""; // or handle it based on your requirements
                    }
                } else return "";
            }
        }