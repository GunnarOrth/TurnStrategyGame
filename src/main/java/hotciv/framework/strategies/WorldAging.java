package hotciv.framework.strategies;

public interface WorldAging {
    /**
     * Sets world age
     * Precondition: Int age is a valid integer.
     *
     * @param age integer value to set age equal to.
     */
    public void setAge(int age);
    /**
     * @return the current age of the world.
     */
    public int getAge();

    /**
     * Advance age of world.
     */
    public void advanceAge();
}
