package com.beour.beourminicrud.global.exception;

public class SpaceNotFoundException extends RuntimeException {
    public SpaceNotFoundException(Long id) {
        super("해당 공간이 존재하지 않습니다. id = " + id); // 메시지 안에 id를 넣어서 디버깅 시 편리하게 함!
    }
}
