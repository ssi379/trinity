package trinityprocessortest.full;

import com.nhaarman.trinity.annotations.Table;
import com.nhaarman.trinity.annotations.Column;
import com.nhaarman.trinity.annotations.PrimaryKey;

@Table(name = "entities")
public class MyEntity {

  private Long mId;

  private String mName;

  @Column("id")
  @PrimaryKey
  public Long getId() {
    return mId;
  }

  @Column("id")
  public void setId(final Long id) {
    mId = id;
  }

  @Column("name")
  public void setName(final String name) {
    mName = name;
  }

  @Column("name")
  public String getName() {
    return mName;
  }
}