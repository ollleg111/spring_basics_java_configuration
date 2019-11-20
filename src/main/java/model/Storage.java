package model;

import javax.persistence.*;
import java.util.Arrays;

/*
CREATE TABLE STORAGE(
ID NUMBER,
CONSTRAINT STORAGE_PK PRIMARY KEY(ID),
FORMAT_SUPPORTED NVARCHAR2(50) NOT NULL,
STORAGE_COUNTRY NVARCHAR2(50) NOT NULL,
STORAGE_SIZE NUMBER NOT NULL
);
 */

@Entity
@Table(name = "STORAGE")
public class Storage {
    private long id;
    private String[] formatSupported;
    private String storageCountry;
    private long storageSize;
    private String formatSupportedToString;

    public Storage() {
    }

    public Storage(String[] formatSupported, String storageCountry, long storageSize) {
        this.formatSupported = formatSupported;
        this.storageCountry = storageCountry;
        this.storageSize = storageSize;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "STORAGE_S", sequenceName = "STORAGE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STORAGE_S")
    public long getId() {
        return id;
    }

    public String[] getFormatSupported() {
        return formatSupported;
    }

    @Column(name = "FORMAT_SUPPORTED")
    public String getFormatSupportedToString() {
        return formatSupportedToString;
    }

    @Column(name = "STORAGE_COUNTRY")
    public String getStorageCountry() {
        return storageCountry;
    }

    @Column(name = "STORAGE_SIZE")
    public long getStorageSize() {
        return storageSize;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFormatSupported(String[] formatSupported) {
        this.formatSupported = formatSupported;
    }

    public void setFormatSupportedToString(String formatSupportedToString) {
        this.formatSupportedToString = formatSupportedToString;
    }
    public void setStorageCountry(String storageCountry) {
        this.storageCountry = storageCountry;
    }

    public void setStorageSize(long storageSize) {
        this.storageSize = storageSize;
    }

    public String formatSupportedToString() {
        String str = Arrays.toString(formatSupported);
        return str.substring(1, str.length() - 1);
    }

    public String[] formatSupportedToArray() {
        return formatSupportedToString.split(", ");
    }

    public boolean isFormatSupported(String fileFormat) {
        for (String str : formatSupported) {
            if (str.equals(fileFormat))
                return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Storage storage = (Storage) o;

        if (id != storage.id) return false;
        if (storageSize != storage.storageSize) return false;
        if (!Arrays.equals(formatSupported, storage.formatSupported)) return false;
        return storageCountry.equals(storage.storageCountry);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + Arrays.hashCode(formatSupported);
        result = 31 * result + storageCountry.hashCode();
        result = 31 * result + (int) (storageSize ^ (storageSize >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "id=" + id +
                ", formatSupported=" + Arrays.toString(formatSupported) +
                ", storageCountry='" + storageCountry + '\'' +
                ", storageSize=" + storageSize +
                '}';
    }
}
