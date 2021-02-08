package org.sid.service;

import javax.transaction.Transactional;

import org.sid.dao.UserRepository;
import org.sid.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;

@Transactional
@Service
public class UserService {

	@Autowired
    private UserRepository userRepo;
	
public void updateResetPasswordToken(String token, String email) throws NotFoundException {
    User customer = userRepo.findByEmail(email);
    if (customer != null) {
        customer.setResetPasswordToken(token);
        userRepo.save(customer);
    } else {
        throw new NotFoundException("Could not find any customer with the email " + email);
    }
}
 
public User getByResetPasswordToken(String token) {
    return userRepo.findByResetPasswordToken(token);
}
 
public void updatePassword(User customer, String newPassword) {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    String encodedPassword = passwordEncoder.encode(newPassword);
    customer.setPassword(encodedPassword);
     
    customer.setResetPasswordToken(null);
    userRepo.save(customer);
}
}