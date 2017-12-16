package com.webischia.ticketfrontend;

import com.webischia.ticketfrontend.Domains.Tickets;
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
        List<Tickets> tickets = apiService.getTickets("");
        assertEquals(4, tickets.size());
    }
}
