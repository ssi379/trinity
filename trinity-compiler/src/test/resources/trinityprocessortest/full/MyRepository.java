package trinityprocessortest.full;

import com.nhaarman.trinity.annotations.Repository;
import java.util.List;
import java.util.Collection;

@Repository(MyEntity.class)
public interface MyRepository {

  Long create(MyEntity entity);

  void createAll(Collection<MyEntity> entities);

  MyEntity findById(Long id);

  List<MyEntity> findAll();

}