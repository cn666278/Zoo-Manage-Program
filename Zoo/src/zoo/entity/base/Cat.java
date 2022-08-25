package zoo.entity.base;

import zoo.entity.base.AbstractAnimal;
import zoo.inter.Leg;

public class Cat extends AbstractAnimal implements Leg{
    private static final long serialVersionUID = -2919986919068143322L;

    public Cat(){
    }

    public Cat(String name){
        super(name);
    }

    @Override
    public int getLegNum(){
        return 4;
    }
}
