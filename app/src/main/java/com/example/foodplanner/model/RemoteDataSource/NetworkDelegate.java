package com.example.foodplanner.model.RemoteDataSource;

import com.example.foodplanner.model.Pojos.ProductsPOJO;
import com.example.foodplanner.model.Pojos.AreasPojo;
import com.example.foodplanner.model.Pojos.CategoriesPojo;
import com.example.foodplanner.model.Pojos.IngeriedientPojo;

import java.util.List;

public interface NetworkDelegate {

    void  onSuccessMeals2(List<ProductsPOJO> productsPOJOS);
    void onSuccessMeals3(List<ProductsPOJO> productsPOJOS);
    void onSuccessMeals (List<ProductsPOJO> productsPOJO);
    void onSuccessCategories (List<CategoriesPojo> categoriesPojo);
    void onSuccessArea (List<AreasPojo> areasPojo);
    void onSuccessIngeriedient (List<IngeriedientPojo> ingeriedientPojo);
    void onfailure (String message);

}
