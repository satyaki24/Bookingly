package com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Service;

import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Exception.ResourceNotFoundException;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Repository.UserRepository;
import com.learningJavaBackend.projects.HotelBookingAndManagementSystem.Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: "+id));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElse(null);
    }
}
