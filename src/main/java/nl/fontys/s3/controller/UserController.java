package nl.fontys.s3.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nl.fontys.s3.business.CreateUser;
import nl.fontys.s3.business.DeleteUser;
import nl.fontys.s3.business.GetUsers;
import nl.fontys.s3.business.UpdateUser;
import nl.fontys.s3.domain.CreateUserRequest;
import nl.fontys.s3.domain.CreateUserResponse;
import nl.fontys.s3.domain.GetAllUsersResponse;
import nl.fontys.s3.domain.UpdateUserRequest;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
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
        request.setUserId(userId);
        updateUser.updateUser(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest request){
        CreateUserResponse response = createUser.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}