package com.tofibashers.studentlist.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@javax.persistence.Entity
@Table
public class StudentEntry implements Serializable
{

	@Id
	@GeneratedValue
	private Long id;

    @Column(length = 32, nullable = false)
    private String firstName;

    @Column(length = 32, nullable = false)
    private String lastName;

    @Column(length = 4, nullable = false)
    private String groupNumber;

    @Column(length = 2, nullable = false)
    private Integer gradePointAverage;

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public Integer getGradePointAverage() {
        return gradePointAverage;
    }

    public void setGradePointAverage(Integer gradePointAverage) {
        this.gradePointAverage = gradePointAverage;
    }

    @Override
	public String toString()
	{
		return String.format("StudentEntry[%d, %s, %s, %s]", this.id, this.firstName, this.lastName, this.groupNumber, this.gradePointAverage);
	}

}