package com.bolshakova.webapp.storage;

import com.bolshakova.webapp.exception.ExistStorageException;
import com.bolshakova.webapp.exception.NotExistStorageException;
import com.bolshakova.webapp.exception.StorageException;
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

    public final void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (size > STORAGE_LIMIT) {
            throw new StorageException("База переполнена!",resume.getUuid());
            //System.out.println("Невозможно добавить резюме c uuid = " + resume.getUuid() + ". База переполнена!");
        } else if ((index == 0 && size > 0) || index > 0) {
            throw new ExistStorageException(resume.getUuid());
            //System.out.println("Невозможно добавить резюме c uuid = " + resume.getUuid() + ". Такое резюме уже существует в базе!");
        } else {
            addResume(resume, index);
            size++;
        }
    }

    public final void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            fillDeletedResume(index);
            storage[size - 1] = null;
            size--;
        } else {
            throw new NotExistStorageException(uuid);
            //System.out.println("Невозможно удалить резюме c uuid = " + uuid + "! Резюме отсутсвует!");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public final void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
            //System.out.println("Невозможно обновить резюме c uuid = " + resume.getUuid() + "! Резюме отсутсвует!");
        } else {
            storage[index] = resume;
        }
    }

    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        } else {
            throw new NotExistStorageException(uuid);
            //System.out.println("Невозможно получить резюме c uuid = " + uuid + "! Резюме отсутсвует!");
        }
    }

    protected abstract int getIndex(String uuid);

    protected abstract void fillDeletedResume(int index);

    protected abstract void addResume(Resume resume, int index);

}
