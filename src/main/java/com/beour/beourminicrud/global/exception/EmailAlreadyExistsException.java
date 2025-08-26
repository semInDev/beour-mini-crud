package com.beour.beourminicrud.global.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String alreadyExistsemail) {
        super("이미 회원가입된 이메일입니다. email = " + alreadyExistsemail);
    }
}
