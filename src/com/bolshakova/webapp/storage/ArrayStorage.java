package com.bolshakova.webapp.storage;

import com.bolshakova.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final int STORAGE_LIMIT = 10_000;
    private final Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (size > STORAGE_LIMIT) {
            System.out.println("Невозможно добавить резюме c uuid = " + r.getUuid() + ". База переполнена!");
        } else if ((index == 0 && size > 0) || index > 0) {
            System.out.println("Невозможно добавить резюме c uuid = " + r.getUuid() + ". Такое резюме уже существует в базе!");
        } else {
            storage[size] = r;
            size++;
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        } else {
            System.out.println("Невозможно получить резюме c uuid = " + uuid + "! Резюме отсутсвует!");
        }
        return null;
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            storage[index] = storage[size - 1];
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
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            System.out.println("Невозможно обновить резюме c uuid = " + resume.getUuid() + "! Резюме отсутсвует!");
        } else {
            storage[index] = new Resume();
        }
    }


    int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
