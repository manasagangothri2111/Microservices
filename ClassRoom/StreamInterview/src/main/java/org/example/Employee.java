package org.example;

public class Employee {
    private String name;
    private String dep;
    private int empId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public Employee(String name, String dep, int empId) {
        this.name = name;
        this.dep = dep;
        this.empId = empId;
    }
}
