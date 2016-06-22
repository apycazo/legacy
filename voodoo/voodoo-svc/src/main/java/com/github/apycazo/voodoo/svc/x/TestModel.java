package com.github.apycazo.voodoo.svc.x;

/**
 * @author Andres Picazo
 */
public class TestModel
{
    private int id;
    private String name;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString ()
    {
        return String.format("id: %d, name: %s", id, name);
    }
}
