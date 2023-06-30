package com.bolshakova.webapp.exception;

public class ExistStorageException extends StorageException{
    public ExistStorageException(String uuid) {
        super("Такое резюме "+uuid+" уже есть в базе!",uuid);
    }
}
