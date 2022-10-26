package com.example.odziezowy.Service;

import com.example.odziezowy.Model.Categories;
import com.example.odziezowy.Repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriesService {

    private final CategoriesRepository categoriesRepository;

    @Autowired
    public CategoriesService(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }


    public List<Categories> getForMainPageService(int number) {

        List<Categories> randomCategories = new ArrayList<>();
        List<Categories> copy = new ArrayList<>(categoriesRepository.findAll());

        SecureRandom rand = new SecureRandom();
        for (int i = 0; i < Math.min(number, categoriesRepository.findAll().size()); i++) {
            randomCategories.add( copy.remove( rand.nextInt( copy.size() ) ));
        }

        return randomCategories;
    }

}
