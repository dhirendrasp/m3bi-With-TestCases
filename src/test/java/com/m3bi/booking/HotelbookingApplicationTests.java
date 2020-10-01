package com.m3bi.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT, classes=HotelBookingApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations= "classpath:application.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) 
class HotelbookingApplicationTests {
	
	@Autowired
	MockMvc mock;
	
	@Test
	@Order(1)
	void getUserByIdTest() throws Exception {
		MvcResult mvcResult = mock.perform(MockMvcRequestBuilders.get("/users/1")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("User1")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.bonusId", Matchers.is(101)))
				.andReturn();
		
		System.out.println(mvcResult.getResponse().getContentAsString());
		assertEquals("{\"id\":1,\"name\":\"User1\",\"bonusId\":101,\"_links\":{\"all-users\":{\"href\":\"http://localhost/users\"}}}", mvcResult.getResponse().getContentAsString(), "get user by Id sucessful");
		
		assertNotNull(mvcResult.getResponse(), "GeT all user sucessful");
	}
	
	@Test
	@Order(2)
	void addUserTest() throws Exception {
			
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/users")
				.accept(MediaType.APPLICATION_JSON).content("{\n" + 
						"        \"name\": \"User5\",\n" + 
						"        \"bonusId\": 105\n" + 
						"    }")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mock.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertNotNull(response);
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		System.out.println(response.getContentAsString());
	}
	
	@Test
	@Order(3)
	void createBookingTest() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/bookings/user/1/hotel/10")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mock.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertNotNull(response);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		System.out.println(response.getContentAsString());
		assertEquals("Your Hotel Is Booked. Booking ID: 1 user id: 1",response.getContentAsString());
	}
	
	@Test
	@Order(4)
	void getBookingStatusTest() throws Exception{
		MvcResult result = mock.perform(MockMvcRequestBuilders.get("/bookings/status/user/1")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				//.andExpect(MockMvcResultMatchers.jsonPath("$.*", Matchers.is("Your Hotel Is Booked. Booking ID: 1 user id: 1")))
				.andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		System.out.println(response.getContentAsString());
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertNotNull(response, "GeT booking detials sucessful");
	}
	
	@Test
	@Order(5)
	void updateBonusTest() throws Exception{
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/bonuses/user/2")
				.accept(MediaType.APPLICATION_JSON).content("{\n" + 
						"	\"id\": 102,\n" + 
						"	\"points\": 1100\n" + 
						"}")
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mock.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertNotNull(response);
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		assertEquals("http://localhost:9090/bonuses/102",response.getHeader(HttpHeaders.LOCATION));
	}

	
}
