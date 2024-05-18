package com.example.foodsharing.model.FoodSharing;


import com.example.foodsharing.model.FoodSharing.FoodGiver;
import com.example.foodsharing.model.FoodSharing.FoodTaker;
import com.example.foodsharing.model.FoodSharing.GrahLaxmi;
import com.example.foodsharing.model.FoodSharing.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodGiverRepository extends JpaRepository<FoodGiver, Long> { }