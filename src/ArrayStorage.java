/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    Resume fakeResume = new Resume();


    int size = size();
    int lastIndex = 0;

    void clear() {
        for (int i = 0; i < lastIndex; i++) {
            storage[i] = null;
        }
        size = 0;
        lastIndex = 0;
    }

    void save(Resume r) {
        storage[lastIndex] = r;
        lastIndex++;
        size++;
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        fakeResume.uuid = "doesnt exit";
        return fakeResume;

    }

    void delete(String uuid) {
        for (int i = 0; i < lastIndex; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[i] = null;
                size--;

            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {

        Resume[] newResume = new Resume[size];
        int k = 0;
        for (int i = 0; i < lastIndex; i++) {
            if (storage[i] != null) {
                newResume[k] = storage[i];
                k++;
            }
        }
        return newResume;
    }

    int size() {
        int size = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {
                size++;
            }
        }
        return size;
    }
}
