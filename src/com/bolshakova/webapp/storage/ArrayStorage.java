package com.bolshakova.webapp.storage;

import com.bolshakova.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void fillDeletedResume(int index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected void addResume(Resume resume, int index) {
        storage[size] = resume;
    }
}
