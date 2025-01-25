package pl.pjatk.s30135bank.storage;

import pl.pjatk.s30135bank.model.BaseEntity;

import java.util.List;
import java.util.Optional;

public abstract class Storage<T extends BaseEntity> {
    public abstract List<T> getList();

    public Optional<T> getById(int id) {
        return getList().stream().filter(obj -> obj.getId() == id).findFirst();
    }

    public void save(T obj) {
        getList().add(obj);
    }

    public int getMaxId() {
        return getList().stream().mapToInt(BaseEntity::getId).max().orElse(0);
    }
}
