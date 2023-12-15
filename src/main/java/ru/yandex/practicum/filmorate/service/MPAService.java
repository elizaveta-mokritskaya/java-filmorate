package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.MPADoesntExistException;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.mpa.MPAStorage;

import java.util.List;
import java.util.Optional;

@Component
public class MPAService {

    private final MPAStorage storage;


    public MPAService(MPAStorage storage) {
        this.storage = storage;
    }

    public List<MPA> getAllMPA() {
        return storage.getAllMPA();
    }

    public MPA getMPAById(int mpaId) {
        Optional<MPA> mpaOptional = storage.getById(mpaId);
        if (mpaOptional.isEmpty()) {
            throw new MPADoesntExistException();
        }
        return mpaOptional.get();
    }
}
