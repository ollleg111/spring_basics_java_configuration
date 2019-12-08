package model;

import javax.persistence.*;
import java.util.Arrays;
import util.StringArrayToStringConverter;
import java.util.Objects;

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
    private String storageCountry;
    private long storageSize;
    private String[] formatSupported;

    public Storage() {
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "STORAGE_S", sequenceName = "STORAGE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STORAGE_S")
    public long getId() {
        return id;
    }

    @Column(name = "STORAGE_COUNTRY")
    public String getStorageCountry() {
        return storageCountry;
    }

    @Column(name = "STORAGE_SIZE")
    public long getStorageSize() {
        return storageSize;
    }

    @Column(name = "FORMAT_SUPPORTED")
    @Convert(converter = StringArrayToStringConverter.class)
    public String[] getFormatSupported() {
        return formatSupported;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFormatSupported(String[] formatSupported) {
        this.formatSupported = formatSupported;
    }

    public void setStorageCountry(String storageCountry) {
        this.storageCountry = storageCountry;
    }

    public void setStorageSize(long storageSize) {
        this.storageSize = storageSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Storage storage = (Storage) o;

        if (id != storage.id) return false;
        if (storageSize != storage.storageSize) return false;
        if (!Objects.equals(storageCountry, storage.storageCountry))
            return false;
        return Arrays.equals(formatSupported, storage.formatSupported);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (storageCountry != null ? storageCountry.hashCode() : 0);
        result = 31 * result + (int) (storageSize ^ (storageSize >>> 32));
        result = 31 * result + (formatSupported != null ? Arrays.hashCode(formatSupported) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "id=" + id +
                ", storageCountry='" + storageCountry + '\'' +
                ", storageSize=" + storageSize +
                ", formatSupported=" + Arrays.toString(formatSupported) +
                '}';
    }
}
