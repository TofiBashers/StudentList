package com.tofibashers.studentlist.rest.services;


import com.tofibashers.studentlist.dao.studententry.StudentEntryDao;
import com.tofibashers.studentlist.entity.StudentEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;


@Component
@Path("/students")
public class StudentEntryService
{
	@Autowired
	private StudentEntryDao studentEntryDao;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<StudentEntry> list() throws IOException
	{
        return this.studentEntryDao.findAll();
	}


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public StudentEntry read(@PathParam("id") Long id)
	{
		StudentEntry studentEntry = this.studentEntryDao.find(id);
		if (studentEntry == null) {
			throw new WebApplicationException(404);
		}
		return studentEntry;
	}


	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public StudentEntry create(StudentEntry studentEntry)
	{
		return this.studentEntryDao.save(studentEntry);
	}


	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public StudentEntry update(@PathParam("id") Long id, StudentEntry studentEntry)
	{
		return this.studentEntryDao.save(studentEntry);
	}


	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public void delete(@PathParam("id") Long id)
	{
		this.studentEntryDao.delete(id);
	}


}