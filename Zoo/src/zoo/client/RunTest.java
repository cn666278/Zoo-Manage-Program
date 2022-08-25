package zoo.client;

import zoo.entity.base.AbstractAnimal;
import zoo.entity.base.Cat;
import zoo.entity.base.Dog;
import zoo.entity.base.Fish;

import zoo.manager.ZooManager;

import java.util.ArrayList;
import java.util.List;

/*
* 测试用程序
* */

public class RunTest {
    public static void main(String[] args) {

        // 初始化数据
        final List<AbstractAnimal> animalList = new ArrayList<AbstractAnimal>(){
            private static final long serialVersionUID = -2569394013903999819L;
            {
                add(new Cat("Cat A"));
                add(new Cat("Cat B"));
                add(new Dog("Dog A"));
                add(new Dog("Dog B"));
                add(new Fish("Fish A"));
                add(new Fish("Fish B"));
            }
        };

        // 获得管理者
        ZooManager manager = ZooManager.manager(animalList);

        // 打印所有的动物信息
        System.out.println("******************** Animal Lists ************************");
        manager.getAnimalList().forEach(System.out::println);

        // 通过类获得指定类型的动物列表
        System.out.println("******************** Using Cat class to find a animal ************************");
        manager.getAnimalListByClass(Cat.class).forEach(System.out::println);

        // 通过名字获取动物（可能重复）
        System.out.println("******************** Using name \"Fish B\" to find an animal ************************");
        manager.getAnimalListByName("Fish B").forEach(System.out::println);

        // 通过类型获取动物列表
        System.out.println("******************** Using type \"cat\" to find animals ************************");
        manager.getAnimalListByType("cat").forEach(System.out::println);

        // 增加一个动物
        Cat cat = new Cat("Pone");
        manager.addAnimal(cat);
        System.out.println("*******************************************************");
        System.out.println("add success: " + manager.containsAnimal(cat)); // true

        // 修改动物信息
        System.out.println("update success: " + manager.updateAnimal(cat, new Cat("Big Pone")));

        // 删除
        System.out.println("delete success: " + manager.deleteAnimal(cat)); // true

    }
}
