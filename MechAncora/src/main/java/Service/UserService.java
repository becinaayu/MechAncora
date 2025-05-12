package Service;



import Entity.User;
import Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUser(){
        return userRepository.findAll();
    }
    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
    public void updateUser(User user, Long id){
        userRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("Usuário com Id"+ user.getId()+" não foi encontrado."));
        userRepository.save(user);
    }
    public void deleteUser(Long id){
        userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário com Id"+ id +" não foi encontrado."));
        userRepository.deleteById(id);
    }
    public boolean verifyCredentials(String email, String password){
        User user = userRepository.findByEmail(email);
        if(user.getPassword() == password){
            return true;
        }
        return false;
    }
}
