package trinityprocessortest.full;

import com.nhaarman.trinity.annotations.Repository;
import java.util.List;

@Repository(MyEntity.class)
public interface MyRepository {

  Long create(MyEntity entity);

  MyEntity findById(Long id);

  List<MyEntity> findAll();

}