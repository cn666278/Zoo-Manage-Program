package zoo.entity.base;

import java.io.*;
import java.util.Objects;

/*
* 动物抽象类
* 注意：当动物名和动物类型一致时，认为是同一动物
* @author cn */

public abstract class AbstractAnimal implements Serializable{

    private static final long serialVersionUID = 799133399232518077L;
    /* Animal name */
    private String name;
    /* Animal type */
    private String type;

    public AbstractAnimal(){
    }

    public AbstractAnimal(String name){
        type = getType();
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    /*
    * 获得动物的class类型
    * return 返回当前对象的类的简单名字（转小写）
    * */

    public String getType() {
        return this.getClass().getSimpleName().toLowerCase();
    }

    public void setType(String type) {
        this.type = type;
    }

    /*
    * 深克隆 deep clone: 需要序列化。
    * 为后期扩展而写（目前程序中未使用）*/

    @Deprecated
    public AbstractAnimal deepClone() throws IOException, ClassNotFoundException{
        // 将对象写入流中
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(this);

        //将对象从流中取出
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        return (AbstractAnimal) (ois.readObject());
    }

    @Override
    public String toString(){
        return String.join("\t\t", name, type);
    }

    @Override
    public boolean equals(Object o){
        if(this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractAnimal that = (AbstractAnimal) o;
        return Objects.equals(name, that.name) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode(){
        return Objects.hash(name, type);
    }

    public abstract int getLegNum();
}
