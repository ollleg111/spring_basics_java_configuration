package repo;

import constants.Constants;
import model.File;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import util.HibernateUtil;

@Repository
public class FileDAO extends GeneralDAO<File> {
    private HibernateUtil hibernateUtil;


    @Autowired
    public FileDAO(HibernateUtil hibernateUtil) {
        setHibernateUtil(hibernateUtil);
        setTypeClass(File.class);
    }

    @Override
    public File save(File file) throws HibernateException {
        return super.save(file);
    }

    @Override
    public File update(File file) throws HibernateException {
        return super.update(file);
    }

    public void delete(long id) throws HibernateException {
        super.delete(id, Constants.FILE_REQUEST_DELETE);
    }

    @Override
    public File findById(long id) throws HibernateException {
        return super.findById(id);
    }

    public long getStorage(long id) throws HibernateException {
        try (Session session = hibernateUtil.openSession()) {

            Query<Long> query = session.createQuery(Constants.GET_STORAGE_FROM_FILE, Long.class);
            query.setParameter("id", id);

            return query.getSingleResult();
        } catch (HibernateException e) {
            throw new HibernateException("Operation with file with id: " + id
                    + " was filed in method getStorage(long id) from class " + FileDAO.class.getName());
        }
    }

    public String getFormat(long id) throws HibernateException {
        try (Session session = hibernateUtil.openSession()) {

            Query<String> query = session.createQuery(Constants.GET_FORMAT_FROM_FILE, String.class);
            query.setParameter("id", id);

            return query.getSingleResult();
        } catch (HibernateException e) {
            throw new HibernateException("Operation with file with id: " + id
                    + " was filed in method getFormat(long id) from class " + FileDAO.class.getName());
        }
    }
}
