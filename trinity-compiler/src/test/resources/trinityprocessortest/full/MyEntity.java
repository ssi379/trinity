package trinityprocessortest.full;

import com.nhaarman.trinity.annotations.Column;
import com.nhaarman.trinity.annotations.PrimaryKey;
import com.nhaarman.trinity.annotations.Table;

@Table(name = "entities")
public class MyEntity {

  private Long mId;

  private String mName;

  private boolean mSomeBoolean;

  private int mSomeInt;

  private MyObject mMyObject;

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
  public String getName() {
    return mName;
  }

  @Column("name")
  public void setName(final String name) {
    mName = name;
  }

  @Column("some_boolean")
  public boolean getSomeBoolean() {
    return mSomeBoolean;
  }

  @Column("some_boolean")
  public void setSomeBoolean(boolean someBoolean) {
    mSomeBoolean = someBoolean;
  }

  @Column("some_boxed_boolean")
  public Boolean getSomeBoxedBoolean() {
    return mSomeBoolean;
  }

  @Column("some_boxed_boolean")
  public void setSomeBoxedBoolean(Boolean someBoolean) {
    mSomeBoolean = someBoolean;
  }

  @Column("some_int")
  public int getSomeInt() {
    return mSomeInt;
  }

  @Column("some_int")
  public void setSomeInt(int someInt) {
    mSomeInt = someInt;
  }

  @Column("some_integer")
  public Integer getSomeInteger() {
    return mSomeInt;
  }

  @Column("some_integer")
  public void setSomeInteger(Integer someInt) {
    mSomeInt = someInt;
  }

  @Column("my_object")
  public MyObject getMyObject(){
    return mMyObject;
  }

  @Column("my_object")
  public void setMyObject(MyObject myObject) {
    mMyObject = myObject;
  }
}