package com.webischia.ticketfrontend;

import com.webischia.ticketfrontend.Domains.Ticket;
import com.webischia.ticketfrontend.Domains.UserToken;
import com.webischia.ticketfrontend.Services.ApiService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {

    @Autowired
    ApiService apiService;


    @Before
    public void setUp() throws Exception {
    }


    @Test
    public void testGetTickets()
    {
       /* List<Ticket> tickets = apiService.getTickets("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsidGVzdGp3dHJlc291cmNlaWQiXSwidXNlcl9uYW1lIjoidG9ydmFsZHMiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiZXhwIjoxNTEzNTYzMjYyLCJhdXRob3JpdGllcyI6WyJFbXBsb3llZSJdLCJqdGkiOiJlMDIwNTY5Ni1kYTJkLTRkNzEtODlhMC1kZWNjZjZhZmZlYmEiLCJjbGllbnRfaWQiOiJBcGlTZXJ2ZXIifQ.-jQQjgMuieG_4DTE5ZVNPvxclJ_aE8GiAMFLhOfml_g");
        assertEquals(2, tickets.size());*/
        UserToken test = apiService.loginUser("rms","jwtpass");
        System.out.println(test.getToken().getAccess_token());
    }
}
