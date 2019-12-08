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
    Проверка наличия нужного хранилища по id
     */
    public Storage findById(long id) throws RuntimeException {
        Storage storage = storageDAO.findById(id);
        storageNullValidator(storage);
        return storage;
    }

    /*
     put(Storage storage, File file) - добавляет файл в хранилище. гарантируется что файл уже есть в условной БД
     переделал в put(long storageId, long fileId)
     */
    public void put(long storageId, File file) throws BadRequestException {
        Storage storage = findById(storageId);

        long fileId = file.getId();
        validationStorageSize(storageId, fileId);
        checkFileInStorage(storageId, fileId);
        checkFormat(storageId, fileId);

        file.setStorage(storage);
        fileDAO.update(file);
    }

    /*
    delete(Storage storage, File file)
    переделал в delete(long storageId, long fileId)
     */
    public void delete(long storageId, long fileId) throws BadRequestException {
        storageNullValidator(storageDAO.findById(storageId));
        File file = fileDAO.findById(fileId);

        file.setStorage(null);
        fileDAO.update(file);
    }

    /*
    transferAll(Storage storageFrom, Storage storageTo) - трансфер всех файлов
     */
    public void transferAll(Storage storageFrom, Storage storageTo) throws BadRequestException {
        /*
        Учитывайте макс размер хранилища
         */
        if (storageDAO.getStorageAmount(storageTo) >=
                storageDAO.getStorageAmount(storageFrom) +
                        storageDAO.getFilledVolume(storageTo.getId()))
            throw new BadRequestException("Do not have needed amount in storageTo in method" +
                    " transferAll(Storage storageFrom, Storage storageTo) from class " +
                    StorageService.class.getName());

        /*
        В одном хранилище не могут хранится файлы с одинаковым айди, но могут хранится файлы с одинаковыми именами
         */
        for (File fileFrom : storageDAO.filesList(storageFrom.getId())) {
            checkFileInStorage(storageTo.getId(), fileFrom.getId());
        }

        /*
         Storage может хранить файлы только поддерживаемого формата
         */
        for (File fileFrom : storageDAO.filesList(storageFrom.getId())) {
            checkFormat(storageTo.getId(), fileFrom.getId());
        }

        List<File> fileList = storageDAO.filesList(storageFrom.getId());
        for (File file : fileList) {

            file.setStorage(storageTo);
            fileDAO.update(file);
        }
    }

    /*
    transferFile(Storage storageFrom, Storage storageTo, long id) - трансфер файла с хранилища storageFrom по его айди
     */
    public void transferFile(Storage storageFrom, Storage storageTo, long id) throws BadRequestException {
        if (storageFrom.getId() != fileDAO.getStorage(id))
            throw new BadRequestException("File with id: " + id + " does not exist in storage with id: " +
                    storageFrom.getId() + " in method" +
                    " transferFile(Storage storageFrom, Storage storageTo, long id) from class + " +
                    StorageService.class.getName());

        long idStorage = storageTo.getId();
        validationStorageSize(idStorage, id);
        checkFileInStorage(idStorage, id);
        checkFormat(idStorage, id);
        File file = fileDAO.findById(id);

        file.setStorage(storageTo);
        fileDAO.update(file);
    }

    private void storageNullValidator(Storage storage) throws RuntimeException {
        if (storage == null) throw new BadRequestException("Storage does not exist in method" +
                " storageValidator(Storage storage) from class " +
                StorageService.class.getName());
    }

    /*
    Учитывайте макс размер хранилища
     */
    private void validationStorageSize(long storageId, long fileId) throws BadRequestException {
        if ((storageDAO.getStorageAmount(storageDAO.findById(storageId)) -
                storageDAO.getFilledVolume(storageId)) >=
                storageDAO.getFileSize(fileId))
            throw new BadRequestException("Do not have needed amount in storageTo in method" +
                    " validationStorageSize(Storage storageTo, long id)" +
                    " from class " + StorageService.class.getName());
    }

    /*
    В одном хранилище не могут хранится файлы с одинаковым айди, но могут хранится файлы с одинаковыми именами
     */
    private void checkFileInStorage(long storageId, long fileId) throws BadRequestException {
        if (storageDAO.getIdFileInStorage(storageId).contains(fileId)) throw
                new BadRequestException("Storage with id: " + storageId +
                        " already contains file with id: " + fileId +
                        " in method checkFileInStorage(Storage storageTo, long id) from class "
                        + StorageService.class.getName());
    }

    /*
    Storage может хранить файлы только поддерживаемого формата
     */
    private void checkFormat(long storageId, long fileId) throws BadRequestException {
        if (!storageDAO.getFormatFromStorage(storageId).contains(fileDAO.findById(fileId).getFormat()))
            throw new BadRequestException("Storage with id: " + storageId +
                    " does not support format file with id: " + fileId + " in method" +
                    " checkFormat(Storage storageTo, long id) " +
                    " from class " + StorageService.class.getName());
    }
}