package com.bolshakova.webapp.exception;

public class NotExistStorageException extends StorageException{

    public NotExistStorageException(String uuid) {
        super("Такого резюме "+uuid+" нет в базе!",uuid);
    }
}
