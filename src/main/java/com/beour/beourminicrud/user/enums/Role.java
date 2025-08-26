package com.beour.beourminicrud.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {
    HOST("호스트"),
    GUEST("게스트");

    private final String text;
}
