package model;

import javax.persistence.*;
import java.util.Objects;

/*
CREATE TABLE FILES(
ID NUMBER,
CONSTRAINT FILE_PK PRIMARY KEY(ID),
FILE_NAME NVARCHAR2(50) NOT NULL,
FILE_FORMAT NVARCHAR2(50) NOT NULL,
FILE_SIZE NUMBER NOT NULL,
STORAGE NUMBER,
CONSTRAINT STORAGE_FK FOREIGN KEY(STORAGE) REFERENCES STORAGE(ID)
);
 */

@Entity
@Table(name = "FILES")
public class File {
    private long id;
    private String name;
    private String format;
    private long size;
    private Storage storage;

    public File() {}

    public File(String name, String format, long size, Storage storage) {
        this.name = name;
        this.format = format;
        this.size = size;
        this.storage = storage;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "FILE_S", sequenceName = "FILE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FILE_S")
    public long getId() {
        return id;
    }

    @Column(name = "FILE_NAME")
    public String getName() {
        return name;
    }

    @Column(name = "FILE_FORMAT")
    public String getFormat() {
        return format;
    }

    @Column(name = "FILE_SIZE")
    public long getSize() {
        return size;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "STORAGE")
    public Storage getStorage() {
        return storage;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        File file = (File) o;

        if (id != file.id) return false;
        return Objects.equals(name, file.name);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "File{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", format='" + format + '\'' +
                ", size=" + size +
                ", storage=" + storage +
                '}';
    }
}
