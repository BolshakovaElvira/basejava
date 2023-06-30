package com.bolshakova.webapp.storage;

import com.bolshakova.webapp.exception.ExistStorageException;
import com.bolshakova.webapp.exception.NotExistStorageException;
import com.bolshakova.webapp.exception.StorageException;
import com.bolshakova.webapp.model.Resume;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractArrayStorageTest {
    private static Storage storage = new ArrayStorage();
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final Resume RESUME_3 = new Resume(UUID_3);

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeAll
    public static void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    void save() {
        Resume resumeTest = new Resume("uuidTest");
        storage.save(resumeTest);
        assertEquals(4, storage.size());
        assertEquals(resumeTest, storage.get(resumeTest.getUuid()));
    }

    @Test
    void delete() {
        storage.delete(UUID_1);
        assertEquals(2, storage.size());
    }

    @Test
    void getAll() {
        Resume[] arrayTest = storage.getAll();
        assertEquals(RESUME_1, arrayTest[0]);
        assertEquals(RESUME_2, arrayTest[1]);
        assertEquals(RESUME_3, arrayTest[2]);
        assertEquals(3, arrayTest.length);
    }

    @Test
    void update() {
        Resume newResume = new Resume(UUID_1);
        storage.update(newResume);
        assertTrue(newResume == storage.get(UUID_1));
    }

    @Test
    void get() {
        assertEquals(RESUME_1, storage.get(RESUME_1.getUuid()));
        assertEquals(RESUME_2, storage.get(RESUME_2.getUuid()));
        assertEquals(RESUME_3, storage.get(RESUME_3.getUuid()));
    }

    @Test
    public void getNotExist() throws Exception {
        assertThrows(NotExistStorageException.class,
                () -> {
                    storage.get("dummy");
                });
    }

    @Test
    public void deleteNotExist() throws Exception {
        assertThrows(NotExistStorageException.class,
                () -> {
                    storage.delete("notExist");
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
    public void saveOverflow() throws Exception {
        assertThrows(StorageException.class,
                () -> {
                    try {
                        for (int i = 3; i <= AbstractArrayStorage.STORAGE_LIMIT + 1; i++) {
                            storage.save(new Resume());
                        }
                    } catch (StorageException | ArrayIndexOutOfBoundsException | AssertionError e) {
                        Assert.fail();
                    }
                });
    }
}