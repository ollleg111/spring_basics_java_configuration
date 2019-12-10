package repo;

import exceptions.BadRequestException;
import model.File;
import model.Storage;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import util.HibernateUtil;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class StorageDAO extends GeneralDAO<Storage> {
    private HibernateUtil hibernateUtil;

    private static final String STORAGE_REQUEST_DELETE = "DELETE FROM STORAGE WHERE ID = ?";
    private static final String FILES_REQUEST_FIND_BY_STORAGE_ID = "SELECT * FROM FILES WHERE STORAGE  = ?";
    private static final String FIND_AMOUNT_OF_STORAGE = "SELECT STORAGE_SIZE FROM STORAGE WHERE ID = ?";
    private static final String STORAGE_FILLED_VOLUME = "SELECT SUM(FILE_SIZE) FROM FILES WHERE STORAGE = ?";
    private static final String SELECT_ALL_ID_FILES_FROM_STORAGE = "SELECT ID FROM FILES WHERE STORAGE = ?";
    private static final String FILE_SIZE_REQUEST = "SELECT FILE_SIZE FROM FILES WHERE ID = ?";
    private static final String GET_FORMAT_FROM_STORAGE = "SELECT FORMAT_SUPPORTED FROM STORAGE WHERE ID = ?";

    @Autowired
    public StorageDAO(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
        setHibernateUtil(hibernateUtil);
        setTypeClass(Storage.class);
    }

    @Override
    public Storage save(Storage storage) throws HibernateException {
        return super.save(storage);
    }

    @Override
    public Storage update(Storage storage) throws HibernateException {
        return super.update(storage);
    }

    public void delete(long id) throws HibernateException {
        super.delete(id, STORAGE_REQUEST_DELETE);
    }

    @Override
    public Storage findById(long id) throws HibernateException {
        return super.findById(id);
    }

    public List<File> filesList(long storageId) throws HibernateException {
        try (Session session = hibernateUtil.openSession()) {

            Query<File> query = session.createNativeQuery(FILES_REQUEST_FIND_BY_STORAGE_ID, File.class);
            query.setParameter(1, storageId);

            return query.list();
        } catch (HibernateException e) {
            throw new HibernateException("Operation with storage with id: " + storageId
                    + " was filed in method filesList(long id) from class " + StorageDAO.class.getName());
        }
    }

    public long getStorageAmount(Storage storage) throws BadRequestException {
        try (Session session = hibernateUtil.openSession()) {
            Query<Long> query = session.createNativeQuery(FIND_AMOUNT_OF_STORAGE, Long.class);
            query.setParameter(1, storage.getId());

            return query.getSingleResult();
        } catch (NoResultException e) {
            throw new BadRequestException("Storage id: " + storage.getId() + " was not found in method " +
                    "getStorageSize(Storage storage) from class " + StorageDAO.class.getName());
        }
    }

    public long getFilledVolume(long id) throws HibernateException {
        try (Session session = hibernateUtil.openSession()) {

            Query<Long> query = session.createNativeQuery(STORAGE_FILLED_VOLUME, Long.class);
            query.setParameter(1, id);

            return query.getSingleResult();
        } catch (HibernateException e) {
            throw new HibernateException("Operation with storage with id: " + id
                    + " was filed in method getStorageAmountSize(long id) from class " + StorageDAO.class.getName());
        }
    }

    public long getFileSize(long id) throws HibernateException {
        try (Session session = hibernateUtil.openSession()) {

            Query<Long> query = session.createNativeQuery(FILE_SIZE_REQUEST, Long.class);
            query.setParameter(1, id);

            return query.getSingleResult();
        } catch (HibernateException e) {
            throw new HibernateException("Operation with file with id: " + id
                    + " was filed in method getFileSize(long id) from class " + StorageDAO.class.getName());
        }
    }

    public List<Long> getIdFileInStorage(long storageId) throws HibernateException {
        try (Session session = hibernateUtil.openSession()) {

            Query<Long> query = session.createNativeQuery(SELECT_ALL_ID_FILES_FROM_STORAGE, Long.class);
            query.setParameter(1, storageId);

            return query.list();
        } catch (HibernateException e) {
            throw new HibernateException("Operation with storage with id: " + storageId
                    + " was filed in method getIdFileInStorage(long storageId) from class "
                    + StorageDAO.class.getName());
        }
    }

    public List<String> getFormatFromStorage(long storageId) throws HibernateException {
        try (Session session = hibernateUtil.openSession()) {

            Query<String> query = session.createNativeQuery(GET_FORMAT_FROM_STORAGE, String.class);
            query.setParameter(1, storageId);

            return query.list();
        } catch (HibernateException e) {
            throw new HibernateException("Operation with storage with id: " + storageId
                    + " was filed in method getFormatFromStorage(long storageId) from class "
                    + StorageDAO.class.getName());
        }
    }
}
