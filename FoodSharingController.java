package com.example.foodsharing.model.FoodSharing;

import com.example.foodsharing.model.FoodSharing.FoodGiver;
import com.example.foodsharing.model.FoodSharing.FoodTaker;
import com.example.foodsharing.model.FoodSharing.GrahLaxmi;
import com.example.foodsharing.model.FoodSharing.User;
//import com.example.foodsharing.repository.FoodGiverRepository;
//import com.example.foodsharing.repository.FoodTakerRepository;
//import com.example.foodsharing.repository.GrahLaxmiRepository;
//import com.example.foodsharing.FoodSharing.security.JwtUtil;
//import com.example.foodsharing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FoodSharingController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private FoodGiverRepository foodGiverRepository;

    @Autowired
    private FoodTakerRepository foodTakerRepository;

    @Autowired
    private GrahLaxmiRepository grahLaxmiRepository;

    @PostMapping("/auth/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        userService.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        final UserDetails userDetails = userService.loadUserByUsername(user.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/food-givers")
    public ResponseEntity<?> submitFoodGiver(@RequestBody FoodGiver foodGiver) {
        foodGiverRepository.save(foodGiver);
        return ResponseEntity.ok("Food giver details submitted successfully");
    }

    @PostMapping("/food-takers")
    public ResponseEntity<?> submitFoodTaker(@RequestBody FoodTaker foodTaker) {
        foodTakerRepository.save(foodTaker);
        return ResponseEntity.ok("Food taker details submitted successfully");
    }

    @PostMapping("/grahlaxmi")
    public ResponseEntity<?> registerGrahLaxmi(@RequestBody GrahLaxmi grahLaxmi) {
        grahLaxmiRepository.save(grahLaxmi);
        return ResponseEntity.ok("GrahLaxmi registered successfully");
    }

    @GetMapping("/find-match")
    public ResponseEntity<?> findMatch() {
        List<FoodGiver> foodGivers = foodGiverRepository.findAll();
        List<FoodTaker> foodTakers = foodTakerRepository.findAll();

        for (FoodTaker taker : foodTakers) {
            for (FoodGiver giver : foodGivers) {
                if (taker.getDietaryPreference().equalsIgnoreCase(giver.getFoodType()) &&
                    giver.getNumberOfPersonsServed() >= taker.getNumberOfPeople()) {
                    // Match found
                    return ResponseEntity.ok("Match found: " + giver.getId() + " for taker: " + taker.getId());
                }
            }
        }
        return ResponseEntity.ok("No match found");
    }
}



