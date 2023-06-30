package com.bolshakova.webapp.storage;

import com.bolshakova.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void fillDeletedResume(int index) {
        int length = size - index - 1;
        System.arraycopy(storage, index + 1, storage, index, length);

    }

    @Override
    protected void addResume(Resume resume, int index) {
        index = (-index) - 1;
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
    }
}
