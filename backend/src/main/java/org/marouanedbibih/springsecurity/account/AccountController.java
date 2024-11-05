package org.marouanedbibih.springsecurity.account;

import org.marouanedbibih.springsecurity.user.dto.UserDTO;
import org.marouanedbibih.springsecurity.utils.MyResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<MyResponse> updateUserInfo(@PathVariable Long id, @RequestBody AccountRequest request) {
        UserDTO updatedUser = accountService.updateUserInfo(id, request);

        return ResponseEntity.status(HttpStatus.OK).body(MyResponse.builder()
                .data(updatedUser)
                .message("Your information updated successfully")
                .build());
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<MyResponse> updatePassword(@PathVariable Long id,
            @RequestBody PasswordRequest request) {
        UserDTO updatedUser = accountService.updatePassword(id, request);

        return ResponseEntity.status(HttpStatus.OK).body(MyResponse.builder()
                .data(updatedUser)
                .message("Your password updated successfully")
                .build());
    }
}
