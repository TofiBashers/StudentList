package com.tofibashers.studentlist.rest.services;


import com.tofibashers.studentlist.dao.user.UserDao;
import com.tofibashers.studentlist.entity.User;
import com.tofibashers.studentlist.rest.security.TokenUtils;
import com.tofibashers.studentlist.transfer.TokenTransfer;
import com.tofibashers.studentlist.transfer.UserTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@Path("/user")
public class UserService
{
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserDao userDao;

	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authManager;


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public UserTransfer getUser()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if (principal instanceof String && ((String) principal).equals("anonymousUser")) {
			throw new WebApplicationException(401);
		}
		UserDetails userDetails = (UserDetails) principal;

		return new UserTransfer(userDetails.getUsername(), this.createRoleMap(userDetails));
	}


	@Path("authenticate")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public TokenTransfer authenticate(@FormParam("username") String username, @FormParam("password") String password)
	{
		UsernamePasswordAuthenticationToken authenticationToken =
				new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication = this.authManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

		return new TokenTransfer(TokenUtils.createToken(userDetails));
	}


	private Map<String, Boolean> createRoleMap(UserDetails userDetails)
	{
		Map<String, Boolean> roles = new HashMap<String, Boolean>();
		for (GrantedAuthority authority : userDetails.getAuthorities()) {
			roles.put(authority.getAuthority(), Boolean.TRUE);
		}

		return roles;
	}

    @Path("control")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public User create(User user)
    {
        return this.userDao.save(user);
    }

    @GET
    @Path("control")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> list() throws IOException
    {
        List<User> allUsers = this.userDao.findAll();

        return allUsers;
    }
}