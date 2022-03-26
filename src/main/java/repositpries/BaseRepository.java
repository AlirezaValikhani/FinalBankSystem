package repositpries;

import java.sql.ResultSet;
import java.util.List;

public interface BaseRepository<T> {
    Long save(T t);
    T read(T t);
    List<T> readAll();
    void update(T t);
    void delete(T t);
    T mapTo(ResultSet resultSet);
    List<T> mapToList(ResultSet resultSet);
}
