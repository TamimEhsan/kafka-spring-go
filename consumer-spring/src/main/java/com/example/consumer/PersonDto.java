package com.example.consumer;

public class PersonDto implements java.io.Serializable {
    static final long serialVersionUID = 42L;
    
    private String name;
    private int age;

    public PersonDto() {
    }

    public PersonDto(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
    @Override
    public String toString() {
        return "PersonDto [name=" + name + ", age=" + age + "]";
    }
}

