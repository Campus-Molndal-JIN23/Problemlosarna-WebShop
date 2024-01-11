package com.example.shopbackend.model;

@Deprecated(forRemoval = true)
public record UserDTO(
        Long id,
        String name,
        String roles
) {
}
