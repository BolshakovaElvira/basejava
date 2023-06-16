package com.bolshakova.webapp.storage;

import com.bolshakova.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final int length = 10_000;
    private Resume[] storage = new Resume[length];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        int indexResume = indexResume(r.getUuid());
        if ((indexResume == 0 && size == 0) || (indexResume < 0 && size < length)) {
            storage[size] = r;
            size++;
        } else {
            System.out.println("Невозможно добавить резюме c uuid = " + r.getUuid() + "! Резюме уже существует или база переполнена!");
        }
    }

    public Resume get(String uuid) {
        int indexResume = indexResume(uuid);
        if (indexResume >= 0) {
            return storage[indexResume];
        } else {
            System.out.println("Невозможно получить резюме c uuid = " + uuid + "! Резюме отсутсвует!");
        }
        return null;
    }

    public void delete(String uuid) {
        int indexResume = indexResume(uuid);
        if (indexResume >= 0) {
            storage[indexResume] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("Невозможно удалить резюме c uuid = " + uuid + "! Резюме отсутсвует!");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        System.arraycopy(storage, 0, resumes, 0, size);
        return resumes;
    }

    public int size() {
        return size;
    }

    public void update(Resume resume) {
        int indexResume = indexResume(resume.getUuid());
        if (indexResume >= 0) {
            resume.setUuid(resume.getUuid());
        } else {
            System.out.println("Невозможно обновить резюме c uuid = " + resume.getUuid() + "! Резюме отсутсвует!");
        }
    }

    int indexResume(String uuid) {
        if (size == 0) {
            return 0;
        }
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
