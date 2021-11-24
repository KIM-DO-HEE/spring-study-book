package com.example.springstudybook.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class HelloResponseDto {
    private final String name;
    private final int amount;

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

}
