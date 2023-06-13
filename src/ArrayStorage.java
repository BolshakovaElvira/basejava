/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    public static int size = 0;

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume r) {
        storage[size] = r;
        size++;
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        Resume[] resumesCopy = new Resume[size() - 1];
        int k = 0;
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                continue;
            } else {
                resumesCopy[k] = storage[i];
                k++;
            }
        }
        size--;
        storage = resumesCopy.clone();
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        int k = 0;
        for (int i = 0; i < size; i++) {
            resumes[k] = storage[i];
            k++;
        }
        return resumes;
    }

    int size() {
        return size;
    }
}
