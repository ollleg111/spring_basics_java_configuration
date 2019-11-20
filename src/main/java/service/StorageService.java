package service;

import exceptions.BadRequestException;
import model.File;
import model.Storage;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repo.FileDAO;
import repo.StorageDAO;

import java.util.List;

@Service
public class StorageService {
    private StorageDAO storageDAO;
    private FileDAO fileDAO;

    @Autowired
    public StorageService(StorageDAO storageDAO, FileDAO fileDAO) {
        this.storageDAO = storageDAO;
        this.fileDAO = fileDAO;
    }

    public Storage save(Storage storage) throws HibernateException {
        return storageDAO.save(storage);
    }

    public Storage update(Storage storage) throws HibernateException {
        return storageDAO.update(storage);
    }

    public void delete(long id) throws HibernateException {
        storageDAO.delete(id);
    }

    /*
    Проверка наличия нужного хранидлища по id
     */
    public Storage findById(long id) throws RuntimeException {
        Storage storage = storageDAO.findById(id);
        if (storage == null) throw new BadRequestException("Storage with id: " + id + " does not exist in method " +
                "findById(long id) from class " +
                StorageService.class.getName());
        return storage;
    }

    /*
    put(Storage storage, File file) - добавляет файл в хранилище. гарантируется что файл уже есть в условной БД
    delete(Storage storage, File file)
    transferAll(Storage storageFrom, Storage storageTo) - трансфер всех файлов
    transferFile(Storage storageFrom, Storage storageTo, long id) - трансфер файла с хранилища storageFrom по его айди
     */

    public void put(Storage storage, File file) throws BadRequestException {
        validateExist(storage, file);
        checkFormatFile(storage, file);
        file.setStorage(storage);
        fileDAO.update(file);
    }

    public void delete(Storage storage, File file) throws BadRequestException {
        validateExist(storage, file);
        file.setStorage(null);
        fileDAO.update(file);
    }

    public void transferAll(Storage storageFrom, Storage storageTo) throws BadRequestException {
        if (storageDAO.getStorageAmount(storageTo) >= storageDAO.getStorageAmount(storageFrom))
            throw new BadRequestException("Do not have needed amount in storageTo in method " +
                    "transferAll(Storage storageFrom, Storage storageTo) from class " +
                    StorageService.class.getName());

        List<File> fileList = storageDAO.filesList(storageFrom.getId());
        for (File file : fileList) {
            checkFormatFile(storageTo, file);
            file.setStorage(storageTo);
            fileDAO.update(file);
        }
    }

    public void transferFile(Storage storageFrom, Storage storageTo, long id) throws BadRequestException {
        File file = fileDAO.findById(id);

        if (file == null || file.getStorage() == null || file.getStorage().getId() != storageFrom.getId())
            throw new BadRequestException("Storage: " + storageFrom.getId() +
                    " does not have file with id: " + id + " in method" +
                    " transferFile(Storage storageFrom, Storage storageTo, long id) " +
                    "from class " + StorageService.class.getName());

        if ((storageDAO.getStorageAmount(storageTo) - storageDAO.getFilledVolume(storageTo.getId())) >=
                storageDAO.getFileSize(id))
            throw new BadRequestException("Do not have needed amount in storageTo in method " +
                    "transferFile(Storage storageFrom, Storage storageTo, long id) " +
                    "from class " + StorageService.class.getName());

        checkFormatFile(storageTo, file);
        file.setStorage(storageTo);
        fileDAO.update(file);
    }

    private void validateExist(Storage storage, File file) throws BadRequestException {
        findById(file.getId());

        if (storageDAO.findById(storage.getId()) == null) throw new
                BadRequestException("Storage with id: " + storage.getId() +
                "does not exist in method validateExist(Storage storage, File file) from class " +
                StorageService.class.getName());
    }

    private void checkFormatFile(Storage storage, File file) throws BadRequestException {
        if (!storage.isFormatSupported(file.getFormat())) {
            throw new BadRequestException("Storage: " + storage.getId() + " does not support format file: "
                    + file.getName() + " in method checkFormatFile(Storage storage, File file) from class " +
                    StorageService.class.getName());
        }
    }
}
