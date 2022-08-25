package zoo.entity.base;

import zoo.entity.base.AbstractAnimal;
import zoo.inter.Leg;

public class Fish extends AbstractAnimal implements Leg {
    private static final long serialVersionUID = -8956792508217398988L;

    public Fish(){
    }

    public Fish(String name){
        super(name);
    }

    @Override
    public int getLegNum(){
        return 0;
    }
}
