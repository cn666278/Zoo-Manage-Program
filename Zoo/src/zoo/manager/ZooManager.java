package zoo.manager;

import zoo.entity.base.AbstractAnimal;

import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/*
* 动物园管理者
* 设计为单例，提供常用的方法来操作动物的信息 */

public class ZooManager {
    private static ZooManager zooManager = null;

    private final List<AbstractAnimal> animalList;

    private ZooManager(List<AbstractAnimal> animalList){
        this.animalList = animalList;
    }

    /*
    * 得到动物园管理者对象
    * animalList 动物列表 */

    public static ZooManager manager(List<AbstractAnimal> animalList){
        if (zooManager == null){
            zooManager = new ZooManager(animalList);
        }
        return zooManager;
    }

    /*
    * 得到动物园管理者对象（动物列表为空）
    * return 管理者对象 */

    private static ZooManager manager() {
        return manager(Collections.emptyList());
    }

    /*
    * 获取所有动物信息
    * return 所有动物列表 */

    public List<AbstractAnimal> getAnimalList(){
        return animalList;
    }

    /*
    * 通过 Class 来获取指定的类型的动物列表
    * @pram abstractAnimalClass examples: Cat.class
    * @return 动物列表 */

    public List<AbstractAnimal> getAnimalListByClass(Class<?> abstractAnimalClass){
        return animalList.stream()
                .filter(abstractAnimal -> abstractAnimal.getClass() == abstractAnimalClass)
                .collect(toList());
    }

    /*
    * 通过 type 字符串来获取指定类型的动物列表
    * @type examples: "cat"
    * @return 动物列表 */

    public List<AbstractAnimal> getAnimalListByType(String type){
        return animalList.stream()
                .collect(groupingBy(AbstractAnimal::getType, toList()))
                .getOrDefault(type, Collections.emptyList());
    }

    /*
    * 通过动物名字获取动物列表（名字可能重复，因此结果是个列表）
    * @param name 动物的名字
    * @return 名字 == name 的所有动物 */

    public List<AbstractAnimal> getAnimalListByName(String name){
        return animalList.stream()
                .collect(groupingBy(AbstractAnimal::getName, toList()))
                .getOrDefault(name, Collections.emptyList());
    }

    /*
    * 增加一个动物
    * @param animal 动物对象 */
    public boolean addAnimal(AbstractAnimal animal){
        return animalList.add(animal);
    }

    /*
    * 删除一个动物
    * @param animal 动物对象 */

    public boolean deleteAnimal(AbstractAnimal animal){
        return animalList.remove(animal);
    }

    /*
    * 修改动物信息（暴力方法：删除原有的旧信息，添加新信息）
    * @param oldInfo 旧的动物信息
    * @param newInfo 新的动物信息
    * @return 修改成功返回 true */

    public boolean updateAnimal(AbstractAnimal oldInfo, AbstractAnimal newInfo){
        if (containsAnimal(oldInfo)){
            deleteAnimal(oldInfo);
        }
        return addAnimal(newInfo);
    }

    public boolean containsAnimal(AbstractAnimal animal){
        return animalList.contains(animal);
    }
}
