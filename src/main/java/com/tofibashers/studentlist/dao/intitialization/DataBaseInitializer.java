package com.tofibashers.studentlist.dao.intitialization;


import com.tofibashers.studentlist.dao.studententry.StudentEntryDao;
import com.tofibashers.studentlist.dao.user.UserDao;
import com.tofibashers.studentlist.entity.StudentEntry;
import com.tofibashers.studentlist.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataBaseInitializer
{

    @Autowired
	private StudentEntryDao studentEntryDao;

    @Autowired
	private UserDao userDao;


    @PostConstruct
	public void initDataBase()
	{
		User userUser = new User("user", "user");
		userUser.addRole("user");
		this.userDao.save(userUser);

		User adminUser = new User("admin", "admin");
		adminUser.addRole("user");
		adminUser.addRole("admin");
		this.userDao.save(adminUser);

        StudentEntry studentEntry = new StudentEntry();
        studentEntry.setFirstName("Pavel");
        studentEntry.setLastName("Korobenin");
        studentEntry.setGroupNumber("221");
        studentEntry.setGradePointAverage(10);
        studentEntryDao.save(studentEntry);
	}

}