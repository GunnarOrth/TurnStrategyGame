package hotciv.standard.variants;

import hotciv.framework.strategies.WorldAging;

import static hotciv.framework.GameConstants.ageDelta;
import static hotciv.framework.GameConstants.age_start;

public class IncrementalWorldAging implements WorldAging {
    private int age;

    public IncrementalWorldAging() {
        setAge(age_start);
    } // starts at 4000BC

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void advanceAge() { age+=ageDelta; } // increment by 100
}
