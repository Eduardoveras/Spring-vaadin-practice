/**
 * Created by Djidjelly Siclait on 10/26/2016.
 */
package com.pucmm.Services;

import com.pucmm.Repositories.UserRepository;
import com.pucmm.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PersistenceException;
import java.util.List;

public class AccessControlService {
    // Repository
    @Autowired
    private UserRepository userRepository;

    // Core Functions
    public User registerUser(String email, String firstName, String lastName, String password) throws Exception{

        try {
            return userRepository.save(new User(email, firstName, lastName, password));
        } catch (PersistenceException exp){
            throw new PersistenceException("Persistence Error while registering new user");
        } catch (NullPointerException exp){
            throw new NullPointerException("Null Pointer Error while registering new user");
        } catch (Exception exp){
            throw new Exception("General Error while registering new user");
        }
    }

    public void editUser(User user) throws Exception{

        try {
            userRepository.save(user);
        } catch (PersistenceException exp){
            throw new PersistenceException("Persistence Error while editing new user");
        } catch (NullPointerException exp){
            throw new NullPointerException("Null Pointer Error while editing new user");
        } catch (Exception exp){
            throw new Exception("General Error while editing new user");
        }
    }

    public boolean validateUserCredentials(String email, String password){
        User user = userRepository.findByEmailAndPassword(email, password);

        return (user != null);
    }

    public List<User> fetchAllRegisteredUser(){ return userRepository.findAll(); }

}
