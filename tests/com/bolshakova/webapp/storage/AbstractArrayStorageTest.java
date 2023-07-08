package com.bolshakova.webapp.storage;

import com.bolshakova.webapp.exception.ExistStorageException;
import com.bolshakova.webapp.exception.NotExistStorageException;
import com.bolshakova.webapp.exception.StorageException;
import com.bolshakova.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractArrayStorageTest {
    private final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final Resume RESUME_3 = new Resume(UUID_3);

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
        Resume[] r1 = storage.getAll();
        Resume[] r2 = new Resume[]{};
        Assertions.assertArrayEquals(r2, r1);
    }

    @Test
    void size() {
        assertSize(3);
    }

    @Test
    void save() {
        final String UUID_SAVE_RESUME = "uuidTest";
        final Resume RESUME_TEST = new Resume(UUID_SAVE_RESUME);
        storage.save(RESUME_TEST);
        assertSize(4);
        assertGet(RESUME_TEST);
    }

    @Test
    void delete() {
        storage.delete(UUID_1);
        assertSize(2);
        assertThrows(NotExistStorageException.class,
                () -> {
                    storage.get(UUID_1);
                });
    }

    @Test
    void getAll() {
        Resume[] expected = new Resume[]{RESUME_1, RESUME_2, RESUME_3};
        Assertions.assertArrayEquals(expected, storage.getAll());
    }

    @Test
    void update() {
        Resume newResume = new Resume(UUID_1);
        storage.update(newResume);
        Assertions.assertSame(storage.get(UUID_1), newResume);
    }


    @Test
    void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test
    public void getNotExist() throws Exception {
        final String UUID_NOT_EXIST = "notExist";
        assertThrows(NotExistStorageException.class,
                () -> {
                    storage.get(UUID_NOT_EXIST);
                });
    }

    @Test
    public void deleteNotExist() throws Exception {
        final String UUID_NOT_EXIST = "notExist";
        assertThrows(NotExistStorageException.class,
                () -> {
                    storage.delete(UUID_NOT_EXIST);
                });
    }

    @Test
    public void saveExist() throws Exception {
        assertThrows(ExistStorageException.class,
                () -> {
                    storage.save(RESUME_1);
                });
    }

    @Test
    public void testSaveOverflow() throws Exception {
        storage.clear();
        for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
            storage.save(new Resume());
        }
        assertThrows(StorageException.class,
                () -> {
                    storage.save(new Resume());
                });
    }

    private void assertSize(int size) {
        Assertions.assertEquals(size, storage.size());
    }

    private void assertGet(Resume r) {
        Assertions.assertEquals(r, storage.get(r.getUuid()));
    }
}