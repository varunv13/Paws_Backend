package com.example.Paws_Backend.controller;

import com.example.Paws_Backend.exception.UserExcepition;
import com.example.Paws_Backend.model.PurchaseOrder;
import com.example.Paws_Backend.model.User;
import com.example.Paws_Backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Get user by ID
    @GetMapping("/{sellerId}/orders-needing-approval")
    public List<PurchaseOrder> getOrdersNeedingApproval(@PathVariable Long sellerId) {
        return userService.getOrdersNeedingApproval(sellerId);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = userService.findUserById(userId);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Update user details
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User user) throws UserExcepition {
        User updatedUser = userService.updateUser(user, userId);
        if (updatedUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    // Get user by JWT token
    @GetMapping("/me")
    public ResponseEntity<User> getUserByJwt(@RequestHeader("Authorization") String token) {
        String jwtToken = token.startsWith("Bearer ") ? token.substring(7) : token;
        User user = userService.findUserByJwt(jwtToken);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
