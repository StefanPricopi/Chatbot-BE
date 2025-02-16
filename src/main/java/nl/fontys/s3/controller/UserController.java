package nl.fontys.s3.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.s3.business.CreateUser;
import nl.fontys.s3.business.DeleteUser;
import nl.fontys.s3.business.GetUsers;
import nl.fontys.s3.business.UpdateUser;
import nl.fontys.s3.domain.users.CreateUserRequest;
import nl.fontys.s3.domain.users.CreateUserResponse;
import nl.fontys.s3.domain.users.GetAllUsersResponse;
import nl.fontys.s3.domain.users.UpdateUserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final CreateUser createUser;
    private final GetUsers getUsers;
    private final DeleteUser deleteUser;
    private final UpdateUser updateUser;


    @GetMapping()
    public ResponseEntity<GetAllUsersResponse> getAllUsers(){
        GetAllUsersResponse response = getUsers.getUsers();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable long userId){
        deleteUser.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{userId}")
    public ResponseEntity<Void> updateUser(@PathVariable("userId") long userId, @RequestBody @Valid UpdateUserRequest request){
        request.setUserid(userId);
        updateUser.updateUser(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest request){
        CreateUserResponse response = createUser.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}