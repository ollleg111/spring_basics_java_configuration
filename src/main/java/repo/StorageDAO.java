package repo;

import constants.Constants;
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
        super.delete(id, Constants.STORAGE_REQUEST_DELETE);
    }

    @Override
    public Storage findById(long id) throws HibernateException {
        return super.findById(id);
    }

    public List<File> filesList(long storageId) throws HibernateException {
        try (Session session = hibernateUtil.openSession()) {

            Query<File> query = session.createQuery(Constants.FILES_REQUEST_FIND_BY_STORAGE_ID, File.class);
            query.setParameter("id", storageId);

            return query.list();
        } catch (HibernateException e) {
            throw new HibernateException("Operation with storage with id: " + storageId
                    + " was filed in method filesList(long id) from class " + StorageDAO.class.getName());
        }
    }

    public long getStorageAmount(Storage storage) throws BadRequestException {
        try (Session session = hibernateUtil.openSession()) {
            Query<Long> query = session.createQuery(Constants.FIND_AMOUNT_OF_STORAGE, Long.class);
            query.setParameter("id", storage.getId());

            return query.getSingleResult();
        } catch (NoResultException e) {
            throw new BadRequestException("Storage id: " + storage.getId() + " was not found in method " +
                    "getStorageSize(Storage storage) from class " + StorageDAO.class.getName());
        }
    }

    public long getFilledVolume(long id) throws HibernateException {
        try (Session session = hibernateUtil.openSession()) {

            Query<Long> query = session.createQuery(Constants.STORAGE_FILLED_VOLUME, Long.class);
            query.setParameter("storage", id);

            return query.getSingleResult();
        } catch (HibernateException e) {
            throw new HibernateException("Operation with storage with id: " + id
                    + " was filed in method getStorageAmountSize(long id) from class " + StorageDAO.class.getName());
        }
    }

    public long getFileSize(long id) throws HibernateException {
        try (Session session = hibernateUtil.openSession()) {

            Query<Long> query = session.createQuery(Constants.FILE_SIZE_REQUEST, Long.class);
            query.setParameter("id", id);

            return query.getSingleResult();
        } catch (HibernateException e) {
            throw new HibernateException("Operation with file with id: " + id
                    + " was filed in method getFileSize(long id) from class " + StorageDAO.class.getName());
        }
    }

    public List<Long> getIdFileInStorage(long storageId) throws HibernateException {
        try (Session session = hibernateUtil.openSession()) {

            Query<Long> query = session.createQuery(Constants.SELECT_ALL_ID_FILES_FROM_STORAGE, Long.class);
            query.setParameter("id", storageId);

            return query.list();
        } catch (HibernateException e) {
            throw new HibernateException("Operation with storage with id: " + storageId
                    + " was filed in method getIdFileInStorage(long storageId) from class "
                    + StorageDAO.class.getName());
        }
    }

    public List<String> getFormatFromStorage(long storageId) throws HibernateException {
        try (Session session = hibernateUtil.openSession()) {

            Query<String> query = session.createQuery(Constants.GET_FORMAT_FROM_STORAGE, String.class);
            query.setParameter("id", storageId);

            return query.list();
        } catch (HibernateException e) {
            throw new HibernateException("Operation with storage with id: " + storageId
                    + " was filed in method getFormatFromStorage(long storageId) from class "
                    + StorageDAO.class.getName());
        }
    }
}
