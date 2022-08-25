package zoo.client;

import zoo.entity.base.AbstractAnimal;
import zoo.entity.base.Cat;
import zoo.entity.base.Dog;
import zoo.entity.base.Fish;
import zoo.manager.ZooManager;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.*;
import java.util.function.Consumer;


/*
* 客户端(主程序)
* */
public class Client {
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    /* 初始化数据 */
    private static final List<AbstractAnimal> ANIMAL_LIST = new ArrayList<AbstractAnimal>(){
        // Java的序列化机制是通过在运行时判断类的 serialVersionUID 来验证版本一致性的。
        // 在进行反序列化时，JVM会把传来的字节流中的 serialVersionUID 与本地相应实体（类）的 serialVersionUID 进行比较，
        // 如果相同就认为是一致的，可以进行反序列化，否则就会出现序列化版本不一致的异常。(InvalidCastException)

        private static final long serialVersionUID = -2569394013903999819L;
        {
            add(new Cat("Kitty"));
            add(new Cat("Anddy"));
            add(new Dog("Puluto"));
            add(new Dog("Honey"));
            add(new Fish("Youki"));
            add(new Fish("Domie"));
        }
    };

    /* 创建管理者 */
    private final static ZooManager MANAGER = ZooManager.manager(ANIMAL_LIST);
    /* 初始化执行策略 */
    private final static Map<Integer, Consumer<String>> MAP = new HashMap<Integer, Consumer<String>>(16){
        private static final long serialVersionUID = 8594371374378459978L;
        {
            // 打印动物列表
            put(0, (title) ->{
                System.out.println(title);
                MANAGER.getAnimalList().forEach(System.out::println);
            });

            // 查找（类型）
            put(1, (title) ->{
                String type = in("请输入你要查找的动物的类型(dog/cat/fish)：");
                System.out.println(title);
                MANAGER.getAnimalListByType(type).forEach(System.out::println);
            });

            // 查找（名字）
            put(2, (title) ->{
                String name = in("请输入你要查找的动物的名字：");
                System.out.println(title);
                MANAGER.getAnimalListByName(name).forEach(System.out::println);
            });

            // 增加一个动物（名字，类型）
            put(3, (title) ->{
                String name = in("请输入你要增加的动物的名字：");
                String type = in("请输入你要增加的动物的类型(dog/cat/fish：");
                AbstractAnimal animal = null;
                try {
                    animal = (AbstractAnimal)Class.forName("zoo.entity.base." + type.substring(0,1)
                            .toUpperCase() + type.substring(1))
                            .newInstance();
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e){
                    e.printStackTrace();
                }

                if (animal != null){
                    animal.setName(name);
                    animal.setType(type);
                    if (MANAGER.containsAnimal(animal)){
                        System.out.println("该动物已存在，不能重复添加。");
                    }
                    else {
                        MANAGER.addAnimal(animal);
                        System.out.println(name + "添加成功！");
                    }
                }
                else
                    System.out.println("error,请检查输入的动物类型：" + type + "?");
            });

            // 修改/更新
            put(4, (title) -> {
                String name = in("请输入你要修改的动物的名字：");
                String type = in("请输入你要修改的动物类型：");
                String newName = in("请输入你要设置的动物的名字：");
                String newType = in("请输入你要设置的动物的类型：");

                try {
                    AbstractAnimal oldInfo = (AbstractAnimal)Class.forName("zoo.entity.base." + type.substring(0,1)
                            .toUpperCase() + type.substring(1)).newInstance();
                    oldInfo.setName(name);
                    oldInfo.setType(type);

                    AbstractAnimal newInfo = (AbstractAnimal)Class.forName("zoo.entity.base." + newType.substring(0,1)
                            .toUpperCase() + newType.substring(1)).newInstance();
                    newInfo.setName(newName);
                    newInfo.setType(newType);

                    if (MANAGER.updateAnimal(oldInfo,newInfo))
                        System.out.println("修改成功！");
                    else
                        System.out.println("修改失败，请检查你的输入名或类型：");
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e){
                    e.printStackTrace();
                }
            });

            // 删除
            put(5, (title) -> {
                String name = in("请输入你要删除的动物的名字：");
                String type = in("请输入你要删除的动物类型：");
                try {
                    AbstractAnimal animal = (AbstractAnimal)Class.forName("zoo.entity.base." + type.substring(0,1)
                            .toUpperCase() + type.substring(1)).newInstance();
                    animal.setName(name);
                    animal.setType(type);
                    if (MANAGER.deleteAnimal(animal))
                        System.out.println("(" + name + ")已经删除成功！");
                    else
                        System.out.println("不存在该动物(" + name + ")");
                } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e){
                    e.printStackTrace();
                }
            });
        }
    };

    public static void main(String[] args){
        Client client = new Client();

        boolean isContinue = true;
        while (isContinue){
            String choice = client.runClient();

            switch (choice){
                case "0":
                    client.setStat(0);
                    break;
                case "1":
                    client.setStat(1);
                    break;
                case "2":
                    client.setStat(2);
                    break;
                case "3":
                    client.setStat(3);
                    break;
                case "4":
                    client.setStat(4);
                    break;
                case "5":
                    client.setStat(5);
                    break;
                default:
                    isContinue = false;
                    System.out.println("[Quit success!]");
                    break;
            }

            System.out.println("***************************************************");
        }
    }

    private String runClient(){
        System.out.println("\n*********************Zoo Menu***********************");
        System.out.println("\t0. 查看所有动物信息");
        System.out.println("\t1. 按动物类型查看");
        System.out.println("\t2. 按动物名查看");
        System.out.println("\t3. 增加一个动物");
        System.out.println("\t4. 修改一个动物");
        System.out.println("\t5. 删除一个动物");
        System.out.println("\t[输入任意其他键盘退出]");

        return in("请输入你的选择： ");
    }

    /*
    * 控制台打印 menu，然后接收从键盘输入的字符串
    * @param menu 菜单/提示信息
    * @return 键盘接收的字符串
    * */

    private static String in(String menu){
        System.out.println(menu);
        String output = null;
        try{
            output = bufferedReader.readLine();
        } catch (IOException e){
            e.printStackTrace();
        }

        return output;
    }

    /*
    * 状态设计模式
    * @param stat 状态
    * */

    private void setStat(int stat){

        MAP.get(stat).accept("动物名\t\t动物类型");
    }


}
