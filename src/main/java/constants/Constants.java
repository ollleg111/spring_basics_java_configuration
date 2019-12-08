package constants;

public class Constants {
    public static final String FILE_REQUEST_DELETE = "DELETE FROM FILES WHERE ID = ?";
    public static final String STORAGE_REQUEST_DELETE = "DELETE FROM STORAGE WHERE ID = ?";
    public static final String FILE_SIZE_REQUEST = "SELECT FILE_SIZE FROM FILES WHERE ID = ?";
    public static final String GET_STORAGE_FROM_FILE = "SELECT STORAGE FROM FILES WHERE ID = ?";
    public static final String GET_FORMAT_FROM_FILE = "SELECT FILE_FORMAT FROM FILES WHERE ID = ?";
    public static final String FIND_AMOUNT_OF_STORAGE = "SELECT STORAGE_SIZE FROM STORAGE WHERE ID = ?";
    public static final String SELECT_ALL_ID_FILES_FROM_STORAGE = "SELECT ID FROM FILES WHERE STORAGE = ?";
    public static final String FILES_REQUEST_FIND_BY_STORAGE_ID = "SELECT * FROM FILES WHERE STORAGE  = ?";
    public static final String GET_FORMAT_FROM_STORAGE = "SELECT FORMAT_SUPPORTED FROM STORAGE WHERE ID = ?";
    public static final String STORAGE_FILLED_VOLUME = "SELECT SUM(FILE_SIZE) FROM FILES WHERE STORAGE = ?";
}
