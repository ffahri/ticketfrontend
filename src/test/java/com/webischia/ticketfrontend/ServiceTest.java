package com.webischia.ticketfrontend;

import com.webischia.ticketfrontend.Domains.Ticket;
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
        List<Ticket> tickets = apiService.getTickets("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsidGVzdGp3dHJlc291cmNlaWQiXSwidXNlcl9uYW1lIjoidG9ydmFsZHMiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwiZXhwIjoxNTEzNDk0MDMwLCJhdXRob3JpdGllcyI6WyJFbXBsb3llZSJdLCJqdGkiOiIzNjliMTQ4NS04NWM5LTQ4OTctYTFkYS0yMWEzMTAzODlhZjQiLCJjbGllbnRfaWQiOiJBcGlTZXJ2ZXIifQ.iEcPX3qnwH0M1szdSkOKKO2ce4-q9nAP_aQ0uUMEJB8");
        assertEquals(2, tickets.size());
    }
}
