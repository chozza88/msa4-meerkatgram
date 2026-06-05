package com.msa4meerkatgram.global.errors.custom;

public class FileManagedException extends RuntimeException{ //커스텀 에러는 RuntimeException을 상속받음.
    public FileManagedException(String message){
        super(message);
    }

}
