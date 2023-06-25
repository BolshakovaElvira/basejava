package com.bolshakova.webapp.storage;

import com.bolshakova.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {

    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public int size() {
        return size;
    }

    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (size > STORAGE_LIMIT) {
            System.out.println("Невозможно добавить резюме c uuid = " + resume.getUuid() + ". База переполнена!");
        } else if ((index == 0 && size > 0) || index > 0) {
            System.out.println("Невозможно добавить резюме c uuid = " + resume.getUuid() + ". Такое резюме уже существует в базе!");
        } else {
            addResume(resume, index);
            size++;
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            changeDeletedResumes(index);
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

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            System.out.println("Невозможно обновить резюме c uuid = " + resume.getUuid() + "! Резюме отсутсвует!");
        } else {
            storage[index] = resume;
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

    protected abstract int getIndex(String uuid);

    protected abstract void changeDeletedResumes(int index);

    protected abstract void addResume(Resume resume, int index);

}
