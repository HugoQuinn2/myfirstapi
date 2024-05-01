package com.hq.myfirtsapi.controllers;

import com.hq.myfirtsapi.models.UserModel;
import com.hq.myfirtsapi.services.UsersService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UsersService usersService;

    @GetMapping
    public ArrayList<UserModel> getUsers(){
        return this.usersService.getUsers();
    }

    @PostMapping
    public UserModel saveUser(@RequestBody UserModel user){
        return this.usersService.saveUser(user);
    }

    @GetMapping(path = "{id}")
    public Optional<UserModel> getUserById(@PathVariable("id") Long id){
        return this.usersService.getById(id);
    }

    @PutMapping(path = "{id}")
    public UserModel updateUserModelById(@RequestBody UserModel request, @PathVariable("id") Long id){
        return this.usersService.updateUserById(request, id);
    }

    @DeleteMapping(path = "{id}")
    public String deleteById(@PathVariable("id") Long id){
        boolean ok = this.usersService.deleteUser(id);
        if(ok){
            return "User with id " + id + " delete!";
        }else{
            return "Error: User with id " + id + " can't be delete";
        }
    }
}
