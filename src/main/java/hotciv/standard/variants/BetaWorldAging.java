package hotciv.standard.variants;

import hotciv.framework.strategies.WorldAging;

import static hotciv.framework.GameConstants.*;

public class BetaWorldAging implements WorldAging {
    private int age;

    public BetaWorldAging() {
        setAge(age_start);
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void advanceAge() {
        // 4000BC - 101BC, 100 years per round
        if(getAge() >= age_start && getAge() <= -200)
            age += ageDelta;

        // around birth of Christ (-1, 1, 50)
        else if(getAge() == -100)
            age += 99;

        else if(getAge() == -1)
            age += 2;

        else if(getAge() == 1)
            age += 49;
        // 50AD - 1750, 50 years per round
        else if(getAge() >= 50 && getAge() < 1750)
            age += age_increment_50;

        // 1750 - 1900, 25 years per round
        else if(getAge() >= 1750 && getAge() < 1900)
            age += age_increment_25;

        // 1900 - 1970, 5 years per round
        else if(getAge() >= 1900 && getAge() < 1970)
            age += age_increment_5;

        // 1971 - until winner, 1 year per round
        else if(getAge() >= 1970)
            age += 1;
    }
}
