package com.educo.educo.DTO.Response;

import com.educo.educo.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class OwnerResponse {
    private String id;
    private String fullName;

    static OwnerResponse transformFromEntity(User user) {
        return new OwnerResponse(user.getId(), user.getFullName());
    }
}
