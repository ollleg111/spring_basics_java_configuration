package constants;

public class Constants {
    public static final String FILE_REQUEST_DELETE = "DELETE FROM FILES WHERE ID = ?";
    public static final String STORAGE_REQUEST_DELETE = "DELETE FROM STORAGE WHERE ID = ?";
    public static final String FILE_SIZE_REQUEST = "SELECT FILE_SIZE FROM FILES WHERE ID = ?";
    public static final String STORAGE_FILLED_VOLUME = "SELECT SUM(FILE_SIZE) FROM FILES WHERE STORAGE = ?";

    public static final String FILES_REQUEST_FIND_BY_STORAGE_ID = "SELECT * FROM FILES WHERE STORAGE ID = ?";
    public static final String FIND_AMOUNT_OF_STORAGE = "SELECT STORAGE_SIZE FROM STORAGE WHERE ID = ?";

}
