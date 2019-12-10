package repo;

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

    private static final String GET_STORAGE_FROM_FILE = "SELECT STORAGE FROM FILES WHERE ID = ?";
    private static final String FILE_REQUEST_DELETE = "DELETE FROM FILES WHERE ID = ?";

    @Autowired
    public FileDAO(HibernateUtil hibernateUtil) {
        this.hibernateUtil = hibernateUtil;
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
        super.delete(id, FILE_REQUEST_DELETE);
    }

    @Override
    public File findById(long id) throws HibernateException {
        return super.findById(id);
    }

    public long getStorage(long id) throws HibernateException {
        try (Session session = hibernateUtil.openSession()) {

            Query<Long> query = session.createNativeQuery(GET_STORAGE_FROM_FILE, Long.class);
            query.setParameter(1, id);

            return query.getSingleResult();
        } catch (HibernateException e) {
            throw new HibernateException("Operation with file with id: " + id
                    + " was filed in method getStorage(long id) from class " + FileDAO.class.getName());
        }
    }
}
