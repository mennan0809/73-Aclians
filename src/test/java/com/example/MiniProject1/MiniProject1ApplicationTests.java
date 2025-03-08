package com.example.MiniProject1;

import java.io.File;
import java.io.IOException;
import java.util.*;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.model.Cart;
import com.example.model.Order;
import com.example.model.Product;
import com.example.model.User;
import com.example.repository.CartRepository;
import com.example.repository.OrderRepository;
import com.example.repository.ProductRepository;
import com.example.repository.UserRepository;
import com.example.service.CartService;
import com.example.service.OrderService;
import com.example.service.ProductService;
import com.example.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;

@ComponentScan(basePackages = "com.example.*")
@WebMvcTest
class MiniProject1ApplicationTests {

	@Value("${spring.application.userDataPath}")
	private String userDataPath;

	@Value("${spring.application.productDataPath}")
	private String productDataPath;

	@Value("${spring.application.orderDataPath}")
	private String orderDataPath;

	@Value("${spring.application.cartDataPath}")
	private String cartDataPath;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;



	@Autowired
	private UserService userService;

	@Autowired
	private CartService cartService;

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderService orderService;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderRepository orderRepository;

	public void overRideAll(){
		try{
			objectMapper.writeValue(new File(userDataPath), new ArrayList<User>());
			objectMapper.writeValue(new File(productDataPath), new ArrayList<Product>());
			objectMapper.writeValue(new File(orderDataPath), new ArrayList<Order>());
			objectMapper.writeValue(new File(cartDataPath), new ArrayList<Cart>());
		} catch (IOException e) {
			throw new RuntimeException("Failed to write to JSON file", e);
		}
	}

	public Object find(String typeString, Object toFind){
		switch(typeString){
			case "User":
				ArrayList<User> users = getUsers();

				for(User user: users){
					if(user.getId().equals(((User)toFind).getId())){
						return user;
					}
				}
				break;
			case "Product":
				ArrayList<Product> products = getProducts();
				for(Product product: products){
					if(product.getId().equals(((Product)toFind).getId())){
						return product;
					}
				}
				break;
			case "Order":
				ArrayList<Order> orders = getOrders();
				for(Order order: orders){
					if(order.getId().equals(((Order)toFind).getId())){
						return order;
					}
				}
				break;
			case "Cart":
				ArrayList<Cart> carts = getCarts();
				for(Cart cart: carts){
					if(cart.getId().equals(((Cart)toFind).getId())){
						return cart;
					}
				}
				break;
		}
		return null;
	}

	public Product addProduct(Product product) {
		try {
			File file = new File(productDataPath);
			ArrayList<Product> products;
			if (!file.exists()) {
				products = new ArrayList<>();
			}
			else {
				products = new ArrayList<>(Arrays.asList(objectMapper.readValue(file, Product[].class)));
			}
			products.add(product);
			objectMapper.writeValue(file, products);
			return product;
		} catch (IOException e) {
			throw new RuntimeException("Failed to write to JSON file", e);
		}
	}
	public ArrayList<Product> getProducts() {
		try {
			File file = new File(productDataPath);
			if (!file.exists()) {
				return new ArrayList<>();
			}
			return new ArrayList<Product>(Arrays.asList(objectMapper.readValue(file, Product[].class)));
		} catch (IOException e) {
			throw new RuntimeException("Failed to read from JSON file", e);
		}
	}

	public User addUser(User user) {
		try {
			File file = new File(userDataPath);
			ArrayList<User> users;
			if (!file.exists()) {
				users = new ArrayList<>();
			}
			else {
				users = new ArrayList<>(Arrays.asList(objectMapper.readValue(file, User[].class)));
			}
			users.add(user);
			objectMapper.writeValue(file, users);
			return user;
		} catch (IOException e) {
			throw new RuntimeException("Failed to write to JSON file", e);
		}
	}
	public ArrayList<User> getUsers() {
		try {
			File file = new File(userDataPath);
			if (!file.exists()) {
				return new ArrayList<>();
			}
			return new ArrayList<User>(Arrays.asList(objectMapper.readValue(file, User[].class)));
		} catch (IOException e) {
			throw new RuntimeException("Failed to read from JSON file", e);
		}
	}
	public Cart addCart(Cart cart){
		try{
			File file = new File(cartDataPath);
			ArrayList<Cart> carts;
			if (!file.exists()) {
				carts = new ArrayList<>();
			}
			else {
				carts = new ArrayList<>(Arrays.asList(objectMapper.readValue(file, Cart[].class)));
			}
			carts.add(cart);
			objectMapper.writeValue(file, carts);
			return cart;
		} catch (IOException e) {
			throw new RuntimeException("Failed to write to JSON file", e);
		}
	}
	public ArrayList<Cart> getCarts() {
		try {
			File file = new File(cartDataPath);
			if (!file.exists()) {
				return new ArrayList<>();
			}
			return new ArrayList<Cart>(Arrays.asList(objectMapper.readValue(file, Cart[].class)));
		} catch (IOException e) {
			throw new RuntimeException("Failed to read from JSON file", e);
		}
	}
	public Order addOrder(Order order){
		try{
			File file = new File(orderDataPath);
			ArrayList<Order> orders;
			if (!file.exists()) {
				orders = new ArrayList<>();
			}
			else {
				orders = new ArrayList<>(Arrays.asList(objectMapper.readValue(file, Order[].class)));
			}
			orders.add(order);
			objectMapper.writeValue(file, orders);
			return order;
		} catch (IOException e) {
			throw new RuntimeException("Failed to write to JSON file", e);
		}
	}
	public ArrayList<Order> getOrders() {
		try {
			File file = new File(orderDataPath);
			if (!file.exists()) {
				return new ArrayList<>();
			}
			return new ArrayList<Order>(Arrays.asList(objectMapper.readValue(file, Order[].class)));
		} catch (IOException e) {
			throw new RuntimeException("Failed to read from JSON file", e);
		}
	}



	private UUID userId;
	private User testUser;
	@BeforeEach
	void setUp() {
		userId = UUID.randomUUID();
		testUser = new User();
		testUser.setId(userId);
		testUser.setName("Test User");
		overRideAll();
	}

	// ------------------------ User Tests -------------------------



	@Test
	void testAddUserEndPoint() throws Exception {
		User testUser3 = new User();
		testUser3.setId(UUID.randomUUID());
		testUser3.setName("Test User3");


		mockMvc.perform(MockMvcRequestBuilders.post("/user/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(testUser3)))
				.andExpect(MockMvcResultMatchers.status().isOk());
		boolean found=false;

		for(User user: getUsers()){
			if(user.getId().equals(testUser3.getId()) && user.getName().equals(testUser3.getName())){
				found=true;
				break;
			}
		}
		assertTrue(found,"User should be added correctly");
	}


	@Test
	void testGetUsersEndPoint() throws Exception {

		addUser(testUser);


		MvcResult result= mockMvc.perform(MockMvcRequestBuilders.get("/user/")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		String responseContent = result.getResponse().getContentAsString();
		List<User> responseUsers = objectMapper.readValue(responseContent, new TypeReference<List<User>>() {});

		assertEquals(responseUsers.size(), getUsers().size(), "Users should be returned correctly From Endpoint");
	}



	@Test
	void testGetUserByIdEndPoint() throws Exception {
		User testUser8=new User();
		testUser8.setId(UUID.randomUUID());
		testUser8.setName("Test User8");
		addUser(testUser8);

		mockMvc.perform(MockMvcRequestBuilders.get("/user/{userId}", testUser8.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(testUser8)));
	}



	@Test
	void testGetOrdersByUserIdEndPoint() throws Exception {
		User testUser10=new User();
		testUser10.setId(UUID.randomUUID());
		testUser10.setName("Test User10");
		List<Order> orders = List.of(new Order(UUID.randomUUID(), testUser10.getId(), 10.0, List.of(new Product(UUID.randomUUID(), "Test Product", 10.0))));
		testUser10.setOrders(orders);
		addUser(testUser10);
		mockMvc.perform(MockMvcRequestBuilders.get("/user/{userId}/orders", testUser10.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(orders)));
	}



	@Test
	void testAddOrderToUserEndPoint() throws Exception {
		User testUser11=new User();
		testUser11.setId(UUID.randomUUID());
		testUser11.setName("Test User11");
		Cart cart=new Cart();
		cart.setId(UUID.randomUUID());
		cart.setUserId(testUser11.getId());
		Product tesProduct=new Product(UUID.randomUUID(), "Test Product", 10.0);
		cart.setProducts(List.of(tesProduct));
		addCart(cart);
		addUser(testUser11);


		mockMvc.perform(MockMvcRequestBuilders.post("/user/{userId}/checkout", testUser11.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Order added successfully"));
	}


	@Test
	void testRemoveOrderOfUserEndPoint() throws Exception{
		User testUser12=new User();
		testUser12.setId(UUID.randomUUID());
		testUser12.setName("Test User12");
		Product product = new Product(UUID.randomUUID(), "Test Product", 100.0);
		Order order = new Order(UUID.randomUUID(), testUser12.getId(), 100.0, List.of(product));
		testUser12.getOrders().add(order);
		addUser(testUser12);
		addOrder(order);

		mockMvc.perform(MockMvcRequestBuilders.post("/user/{userId}/removeOrder", testUser12.getId()).param("orderId", order.getId().toString()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Order removed successfully"));
	}


	@Test
	void testEmptyCartEndpoint() throws Exception{
		User testUser13=new User();
		testUser13.setId(UUID.randomUUID());
		testUser13.setName("Test User13");
		Product product = new Product(UUID.randomUUID(), "Test Product", 100.0);
		Cart cart = new Cart(UUID.randomUUID(), testUser13.getId(), new ArrayList<>(List.of(product)));
		addUser(testUser13);
		addCart(cart);

		mockMvc.perform(MockMvcRequestBuilders.delete("/user/{userId}/emptyCart", testUser13.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Cart emptied successfully"));
	}


	@Test
	void testAddProductToCartEndPoint() throws Exception {
		User testUser14=new User();
		testUser14.setId(UUID.randomUUID());
		testUser14.setName("Test User14");

		Product testProduct=new Product(UUID.randomUUID(), "Test Product", 10.0);
		addUser(testUser14);
		addProduct(testProduct);

		mockMvc.perform(MockMvcRequestBuilders.put("/user/addProductToCart")
						.param("userId", testUser14.getId().toString())
						.param("productId", testProduct.getId().toString()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Product added to cart"));
		assertTrue(getCarts().getLast().getUserId().equals(testUser14.getId()),"New Cart Should be created for user");
		assertEquals(testProduct.getId(), getCarts().getLast().getProducts().get(0).getId(),"Product should be added correctly");
	}



	@Test
	void testDeleteProductFromCartEndPoint1() throws Exception {
		User testUser15=new User();
		testUser15.setId(UUID.randomUUID());
		testUser15.setName("Test User15");

		Product testProduct=new Product(UUID.randomUUID(), "Test Product", 10.0);
		addUser(testUser15);
		addProduct(testProduct);
		Cart cart = new Cart(UUID.randomUUID(), testUser15.getId(), new ArrayList<>(List.of(testProduct)));
		addCart(cart);

		mockMvc.perform(MockMvcRequestBuilders.put("/user/deleteProductFromCart")
						.param("userId", cart.getUserId().toString())
						.param("productId", testProduct.getId().toString()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Product deleted from cart"));
	}
	@Test
	void testDeleteProductFromCartEndPoint2() throws Exception {
		User testUser15=new User();
		testUser15.setId(UUID.randomUUID());
		testUser15.setName("Test User15");

		Product testProduct=new Product(UUID.randomUUID(), "Test Product", 10.0);
		addUser(testUser15);
		addProduct(testProduct);
		// Cart cart = new Cart(UUID.randomUUID(), testUser15.getId(), new ArrayList<>(List.of(testProduct)));
		// addCart(cart);

		mockMvc.perform(MockMvcRequestBuilders.put("/user/deleteProductFromCart")
						.param("userId", testUser15.getId().toString())
						.param("productId", testProduct.getId().toString()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Cart is empty"));
	}



	@Test
	void testDeleteUserByIdEndPoint1() throws Exception {
		User testUser18=new User();
		testUser18.setId(UUID.randomUUID());
		testUser18.setName("Test User18");
		addUser(testUser18);

		mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/{userId}", testUser18.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("User deleted successfully"));
	}
	@Test
	void testDeleteUserByIdEndPoint2() throws Exception {
		User testUser18=new User();
		testUser18.setId(UUID.randomUUID());
		testUser18.setName("Test User18");
		addUser(testUser18);

		mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/{userId}", UUID.randomUUID()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("User not found"));
	}


	// ------------------------ Product Tests -------------------------


	@Test
	void testAddProductEndPoint() throws JsonProcessingException, Exception{

		Product testProduct3=new Product();
		testProduct3.setId(UUID.randomUUID());
		testProduct3.setName("Test Product");
		testProduct3.setPrice(10.0);




		mockMvc.perform(MockMvcRequestBuilders.post("/product/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(testProduct3)))
				.andExpect(MockMvcResultMatchers.status().isOk());

		boolean found=false;

		for(Product product: getProducts()){
			if(product.getId().equals(testProduct3.getId()) && product.getName().equals(testProduct3.getName()) && product.getPrice()==testProduct3.getPrice()){
				found=true;
				break;
			}
		}
		assertTrue(found,"Product should be added correctly");
	}



	@Test
	void testGetProductsEndPoint() throws Exception{
		Product testProduct6=new Product();
		testProduct6.setId(UUID.randomUUID());
		testProduct6.setName("Test Product");
		testProduct6.setPrice(10.0);
		addProduct(testProduct6);

		MvcResult result= mockMvc.perform(MockMvcRequestBuilders.get("/product/")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		String responseContent = result.getResponse().getContentAsString();
		List<Product> responseProducts = objectMapper.readValue(responseContent, new TypeReference<List<Product>>() {});

		assertEquals(getProducts().size(), responseProducts.size(), "Products should be returned correctly From Endpoint");
	}


	@Test
	void testGetProductByIdEndPoint() throws Exception{
		Product testProduct9=new Product();
		testProduct9.setId(UUID.randomUUID());
		testProduct9.setName("Test Product");
		testProduct9.setPrice(10.0);
		addProduct(testProduct9);

		mockMvc.perform(MockMvcRequestBuilders.get("/product/{productId}", testProduct9.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(testProduct9)));
	}


	@Test
	void testUpdateProductEndPoint() throws Exception{
		Product testProduct12=new Product();
		testProduct12.setId(UUID.randomUUID());
		testProduct12.setName("Test Product");
		testProduct12.setPrice(10.0);
		addProduct(testProduct12);
		Map<String,Object> body=new HashMap<>();
		body.put("newName", "UpdatedName");
		body.put("newPrice", 20.0);
		MvcResult result= mockMvc.perform(MockMvcRequestBuilders.put("/product/update/{id}", testProduct12.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(body)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		String responseContent = result.getResponse().getContentAsString();
		Product updatedProduct = objectMapper.readValue(responseContent, Product.class);
		assertEquals(updatedProduct.getId(),testProduct12.getId(),"Product should be updated correctly");
		assertEquals(updatedProduct.getName(),"UpdatedName","Product name should be updated correctly");
		assertEquals(updatedProduct.getPrice(),20.0,"Product price should be updated correctly");
	}


	@Test
	void testApplyDiscountEndPoint() throws Exception{
		Product testProduct15=new Product();
		testProduct15.setId(UUID.randomUUID());
		testProduct15.setName("Test Product");
		testProduct15.setPrice(10.0);
		addProduct(testProduct15);
		ArrayList<UUID> productIds=new ArrayList<>();
		productIds.add(testProduct15.getId());
		mockMvc.perform(MockMvcRequestBuilders.put("/product/applyDiscount")
						.contentType(MediaType.APPLICATION_JSON)
						.param("discount", "10.0")
						.content(objectMapper.writeValueAsString(productIds)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Discount applied successfully"));
		assertEquals(9.0, ((Product)find("Product", testProduct15)).getPrice(),"Product should be updated correctly");
	}


	@Test
	void testDeleteProductByIdEndPoint1() throws Exception{
		Product testProduct15=new Product();
		testProduct15.setId(UUID.randomUUID());
		testProduct15.setName("Test Product");
		testProduct15.setPrice(10.0);
		addProduct(testProduct15);
		mockMvc.perform(MockMvcRequestBuilders.delete("/product/delete/{id}", testProduct15.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Product deleted successfully"));
	}

	// --------------------------------- Cart Tests -------------------------






	@Test
	void testAddCartEndPoint() throws Exception{
		User testUser21=new User();
		testUser21.setId(UUID.randomUUID());
		testUser21.setName("Test User21");
		addUser(testUser21);
		mockMvc.perform(MockMvcRequestBuilders.post("/cart/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(new Cart(UUID.randomUUID(), testUser21.getId(), new ArrayList<>())))
				)
				.andExpect(MockMvcResultMatchers.status().isOk());
		boolean found=false;
		for(Cart cart: getCarts()){
			if(cart.getUserId().equals(testUser21.getId())){
				found=true;
				break;
			}
		}
		assertTrue(found,"Cart should be added correctly");
	}





	@Test
	void testGetCartsEndPoint() throws Exception{
		Cart cart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
		addCart(cart);
		MvcResult result= mockMvc.perform(MockMvcRequestBuilders.get("/cart/")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		String responseContent = result.getResponse().getContentAsString();
		List<Cart> responseCarts = objectMapper.readValue(responseContent, new TypeReference<List<Cart>>() {});
		assertEquals(getCarts().size(), responseCarts.size(), "Carts should be returned correctly From Endpoint");
	}




	@Test
	void testGetCartByIdEndPoint() throws Exception{
		Cart cart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
		addCart(cart);
		mockMvc.perform(MockMvcRequestBuilders.get("/cart/{id}", cart.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(cart)));
	}






	@Test
	void testDeleteCartByIdEndPoint() throws Exception{
		Cart cart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
		addCart(cart);
		mockMvc.perform(MockMvcRequestBuilders.delete("/cart/delete/{id}", cart.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Cart deleted successfully"));
	}


	// --------------------------------- Order Tests -------------------------




	@Test
	void testAddOrderEndPoint() throws Exception{
		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 10.0, new ArrayList<>());
		mockMvc.perform(MockMvcRequestBuilders.post("/order/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(order)))
				.andExpect(MockMvcResultMatchers.status().isOk());
		boolean found=false;
		for(Order o: getOrders()){
			if(o.getId().equals(order.getId())){
				found=true;
				break;
			}
		}
		assertTrue(found,"Order should be added correctly from Endpoint");
	}





	@Test
	void testGetOrdersEndPoint() throws Exception{

		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 10.0, new ArrayList<>());
		addOrder(order);
		MvcResult result= mockMvc.perform(MockMvcRequestBuilders.get("/order/")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		String responseContent = result.getResponse().getContentAsString();
		List<Order> responseOrders = objectMapper.readValue(responseContent, new TypeReference<List<Order>>() {});
		assertEquals(getOrders().size(), responseOrders.size(), "Orders should be returned correctly From Endpoint");
	}





	@Test
	void testGetOrderByIdEndPoint() throws Exception{
		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 10.0, new ArrayList<>());
		addOrder(order);
		mockMvc.perform(MockMvcRequestBuilders.get("/order/{id}", order.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(order)))
				.andReturn();
		// String responseContent = result.getResponse().getContentAsString();
		// Order responseOrder = objectMapper.readValue(responseContent, Order.class);
		// assertEquals(order.getId(), responseOrder.getId(), "Order should be returned correctly From Endpoint");
	}




	@Test
	void testDeleteOrderByIdEndPoint() throws Exception{
		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 10.0, new ArrayList<>());
		addOrder(order);
		mockMvc.perform(MockMvcRequestBuilders.delete("/order/delete/{id}", order.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Order deleted successfully"));
	}

	@Test
	void testDeleteOrderByIdEndPoint2() throws Exception{

		mockMvc.perform(MockMvcRequestBuilders.delete("/order/delete/{id}", UUID.randomUUID()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Order not found"));
	}


	//----------------Our Test Cases ---------
	//----------------User Service Test Cases ---------
	//Add User Service Tests
	@Test
	void testAddUserSuccessfully() {
		User user = new User();
		user.setId(UUID.randomUUID());
		user.setName("Test User");

		userService.addUser(user);

		ArrayList<User> users = userRepository.findAll();
		assertNotNull(find("User", user), "User should be added correctly");
		assertTrue(users.contains(user), "User should be added and found in the repository");
	}

	@Test
	void testAddUserWithEmptyNameThrowsException() {
		User user = new User();
		user.setId(UUID.randomUUID());
		user.setName("");

		assertThrows(Exception.class, () -> {
			userService.addUser(user);
		});
	}

	@Test
	void testAddDuplicateUserThrowsException() {
		User user = new User();
		user.setId(UUID.randomUUID());
		user.setName("Test User");

		userService.addUser(user);

		assertThrows(Exception.class, () -> {
			userService.addUser(user);
		});
	}
	//-------------------------------------------------
	//GetUsers Service Tests
	@Test
	void testGetUsersInitiallyEmpty() {
		ArrayList<User> users = userService.getUsers();
		assertTrue(users.isEmpty(), "User list should be empty initially");
	}

	@Test
	void testGetUsersReturnsAllUsers() {
		User user1 = new User();
		user1.setId(UUID.randomUUID());
		user1.setName("User One");

		User user2 = new User();
		user2.setId(UUID.randomUUID());
		user2.setName("User Two");

		userService.addUser(user1);
		userService.addUser(user2);

		ArrayList<User> users = userService.getUsers();
		assertEquals(2, users.size(), "User list should contain exactly 2 users");
		assertTrue(users.contains(user1) && users.contains(user2), "User list should contain the added users");
	}

	@Test
	void testGetUsersHandlesLargeData() {
		int userCount = 10;
		for (int i = 0; i < userCount; i++) {
			User user = new User();
			user.setId(UUID.randomUUID());
			user.setName("User " + i);
			userService.addUser(user);
		}

		ArrayList<User> users = userService.getUsers();
		assertEquals(userCount, users.size(), "getUsers should return all users even for large datasets");
	}
	//-------------------------------------------------
	//GetUserById Service Tests
	@Test
	void testGetUserByIdValid() {
		User user = new User();
		user.setId(UUID.randomUUID());
		user.setName("Valid User");

		userService.addUser(user);

		User retrievedUser = userService.getUserById(user.getId());
		assertNotNull(retrievedUser, "User should be found");
		assertEquals(user.getId(), retrievedUser.getId(), "Retrieved user should have the correct ID");
		assertEquals(user.getName(), retrievedUser.getName(), "Retrieved user should have the correct name");
	}

	@Test
	void testGetUserByIdWhenNoUsersExist() {
		UUID randomId = UUID.randomUUID();
		User retrievedUser = userService.getUserById(randomId);
		assertNull(retrievedUser, "No user should be found when repository is empty");
	}

	@Test
	void testGetUserByIdWithOneUser() {
		User user1 = new User();
		user1.setId(UUID.fromString("71113bd5-6fc7-472c-85f2-87122241bb56"));
		user1.setName("User One");

		userService.addUser(user1);

		User retrievedUser1 = userService.getUserById(UUID.fromString("71113bd5-6fc7-472c-85f2-87122241bb56"));

		assertNotNull(retrievedUser1, "User One should be found");
		assertEquals(user1.getId(), retrievedUser1.getId(), "Retrieved User One should have the correct ID");
		assertEquals(user1.getName(), retrievedUser1.getName(), "Retrieved User One should have the correct name");
	}

	//-------------------------------------------------
	//GetOrdersByUserId Service Tests

	@Test
	void testGetOrdersByUserIdWhenNoOrders() {
		User testUser = new User();
		testUser.setId(UUID.randomUUID());
		testUser.setName("User Without Orders");
		testUser.setOrders(new ArrayList<>());

		userService.addUser(testUser);

		List<Order> retrievedOrders = userService.getOrdersByUserId(testUser.getId());

		assertNotNull(retrievedOrders, "Returned order list should not be null");
		assertTrue(retrievedOrders.isEmpty(), "User should have no orders");
	}

	@Test
	void testGetOrdersByInvalidUserIdThrowsException() {
		UUID invalidUserId = UUID.randomUUID();

		assertThrows(Exception.class, () -> {
			userService.getOrdersByUserId(invalidUserId);
		});
	}

	@Test
	void testGetOrdersByUserIdWithOneValidOrder() {
		User user = new User();
		user.setId(UUID.fromString("71113bd5-6fc7-472c-85f2-87122241bb56"));
		user.setName("Test User");

		List<Order> orders = List.of(new Order(
				UUID.randomUUID(),
				user.getId(),
				50.0,
				List.of(new Product(UUID.randomUUID(), "Test Product", 50.0)) // Product list
		));

		user.setOrders(orders);

		userService.addUser(user);

		List<Order> retrievedOrders = userService.getOrdersByUserId(user.getId());

		assertNotNull(retrievedOrders, "Order list should not be null");
		assertEquals(1, retrievedOrders.size(), "User should have exactly 1 order");
		assertEquals(orders.get(0).getId(), retrievedOrders.get(0).getId(), "Retrieved order should have the correct ID");
		assertEquals(orders.get(0).getTotalPrice(), retrievedOrders.get(0).getTotalPrice(), "Retrieved order should have the correct total");
	}
	//-------------------------------------------------
	//AddOrderToUser Service Tests
	@Test
	void testAddValidOrderToUser() {
		User testUser = new User();
		testUser.setId(UUID.randomUUID());
		testUser.setName("User With Order");
		testUser.setOrders(new ArrayList<>());

		userService.addUser(testUser);

		Cart cart = new Cart();
		cart.setId(UUID.randomUUID());
		cart.setUserId(testUser.getId());
		Product product1 = new Product(UUID.randomUUID(), "Product A", 20.0);
		Product product2 = new Product(UUID.randomUUID(), "Product B", 30.0);
		cart.setProducts(List.of(product1, product2));

		cartService.addCart(cart);

		int countBefore = testUser.getOrders().size();
		System.out.println("COUNT BEF: " + countBefore);
		System.out.println("test: " +testUser.getId());

		userService.addOrderToUser(testUser.getId());

		User afterUser = (User) find("User", testUser);
		assertEquals(countBefore + 1, afterUser.getOrders().size(), "Order should be added correctly");
	}

	@Test
	void testCartEmptiedAfterOrderAdded() {
		User testUser = new User();
		testUser.setId(UUID.randomUUID());
		testUser.setName("User With Cart");
		testUser.setOrders(new ArrayList<>()); // Initially, no orders

		userService.addUser(testUser);

		Cart cart = new Cart();
		cart.setId(UUID.randomUUID());
		cart.setUserId(testUser.getId());
		Product product1 = new Product(UUID.randomUUID(), "Product A", 20.0);
		Product product2 = new Product(UUID.randomUUID(), "Product B", 30.0);
		cart.setProducts(List.of(product1, product2));

		cartService.addCart(cart);

		Order order = new Order(UUID.randomUUID(), testUser.getId(), 50.0, cart.getProducts());

		int orderCountBefore = testUser.getOrders().size();

		userService.addOrderToUser(testUser.getId());

		User afterUser = (User) find("User", testUser);
		Cart afterCart = (Cart) find("Cart", cart);

		assertEquals(orderCountBefore + 1, afterUser.getOrders().size(), "Order count should increase by 1");
		assertTrue(afterCart.getProducts().isEmpty(), "Cart should be emptied after order is added");
	}

	@Test
	void testAddValidOrderToInvalidUser() {

		UUID randomId= UUID.randomUUID();

		Cart cart = new Cart();
		cart.setId(UUID.randomUUID());
		cart.setUserId(randomId);
		Product product1 = new Product(UUID.randomUUID(), "Product A", 20.0);
		Product product2 = new Product(UUID.randomUUID(), "Product B", 30.0);
		cart.setProducts(List.of(product1, product2));

		cartService.addCart(cart);

		Order order = new Order(UUID.randomUUID(), randomId, 50.0, cart.getProducts());

		assertThrows(Exception.class, () -> {
			userService.addOrderToUser(randomId);
		});


	}

	//-------------------------------------------------
	//EmptyCart Service Tests
	@Test
	void testEmptyCartForInvalidUserThrowsException() {
		UUID userId = UUID.randomUUID();

		assertThrows(Exception.class, () -> {
			userService.emptyCart(userId);
		});

	}
	@Test
	void testEmptyCartForUserWithoutCartThrowsException() {
		User testUser = new User();
		testUser.setName("User With Cart");
		userService.addUser(testUser);
		assertThrows(Exception.class, () -> {
			userService.emptyCart(testUser.getId());
		});

	}

	@Test
	void testEmptyCartWithMultipleProducts() {
		User testUser = new User();
		testUser.setId(UUID.randomUUID());
		testUser.setName("Test User");

		Product product1 = new Product(UUID.randomUUID(), "Product 1", 50.0);
		Product product2 = new Product(UUID.randomUUID(), "Product 2", 75.0);

		ArrayList<Product> products = new ArrayList<>();
		products.add(product1);
		products.add(product2);

		Cart cart = new Cart(testUser.getId(), products);

		userService.addUser(testUser);
		cartService.addCart(cart);

		userService.emptyCart(testUser.getId());

		assertEquals(0, getCarts().getLast().getProducts().size(), "Cart should be emptied correctly when multiple products exist");
	}

	//-------------------------------------------------
	//RemoveOrderFromUser Service Tests
	@Test
	void testRemoveOrderFromInvalidUserThrowsException() {
		UUID userId = UUID.randomUUID();
		UUID orderId = UUID.randomUUID();

		assertThrows(Exception.class, () -> {
			userService.removeOrderFromUser(userId, orderId);
		});

	}

	@Test
	void testRemoveNonExistentOrderFromUserThrowsException() {
		User testUser = new User();
		testUser.setId(UUID.randomUUID());
		testUser.setName("Test User");

		userService.addUser(testUser); // User exists, but has no orders

		UUID randomOrderId = UUID.randomUUID();

		assertThrows(Exception.class, () -> {
			userService.removeOrderFromUser(testUser.getId(), randomOrderId);
		});

	}

	@Test
	void testRemoveOrderWhenUserHasMultipleOrders() {
		User testUser = new User();
		testUser.setId(UUID.randomUUID());
		testUser.setName("Test User");

		Product product1 = new Product(UUID.randomUUID(), "Product 1", 50.0);
		ArrayList<Product> products = new ArrayList<>();
		products.add(product1);
		Product product2 = new Product(UUID.randomUUID(), "Product 2", 75.0);
		ArrayList<Product> products2 = new ArrayList<>();
		products2.add(product2);

		Order order1 = new Order(UUID.randomUUID(), testUser.getId(), 50.0, products);
		Order order2 = new Order(UUID.randomUUID(), testUser.getId(), 75.0, products2);

		testUser.getOrders().add(order1);
		testUser.getOrders().add(order2);

		userService.addUser(testUser);
		orderService.addOrder(order1);
		orderService.addOrder(order2);

		userService.removeOrderFromUser(testUser.getId(), order1.getId());

		assertFalse(getUsers().getLast().getOrders().contains(order1), "Order should be removed successfully");
		assertEquals(1, getUsers().getLast().getOrders().size(), "Only one order should be removed, leaving the other order intact");
	}

	//-------------------------------------------------
	//DeleteUserByUserId Service Tests

	@Test
	void testDeleteInvalidUserThrowsException() {
		UUID random = UUID.randomUUID();

		assertThrows(Exception.class, () -> {
			userService.deleteUserById(random);
		});
	}

	@Test
	void testDeleteUserSuccessfully() {
		User testUser = new User();
		testUser.setId(UUID.randomUUID());
		testUser.setName("Test User");

		userService.addUser(testUser);

		userService.deleteUserById(testUser.getId());

		assertFalse(getUsers().contains(testUser), "User should be removed from the repository");
	}

	@Test
	void testDeleteUserCheckRemainingSuccessfully() {
		User testUser = new User();
		testUser.setId(UUID.randomUUID());
		testUser.setName("Test User");
		User testUser2 = new User();
		testUser2.setId(UUID.randomUUID());
		testUser2.setName("Test User2");

		userService.addUser(testUser);
		userService.addUser(testUser2);


		userService.deleteUserById(testUser.getId());

		assertFalse(getUsers().contains(testUser), "User should be removed from the repository");
		assertEquals(1, getUsers().size(), "Deleting a user should decrease repository size by 1");


	}

	//----------------Order Services Test Cases ---------
	//AddOrder Service Tests
	@Test
	void testAddNullOrderThrowsException() {
		assertThrows(NullPointerException.class, () -> {
			orderService.addOrder(null);
		});
	}

	@Test
	void testAddDuplicateOrderThrowsException() {
		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 200.0, new ArrayList<>());

		orderService.addOrder(order);

		assertThrows(IllegalArgumentException.class, () -> {
			orderService.addOrder(order);
		});

	}

	@Test
	void testAddOrderIncreasesSize() {
		int initialSize = orderService.getOrders().size();

		Order newOrder = new Order(UUID.randomUUID(), UUID.randomUUID(), 150.0, new ArrayList<>());

		orderService.addOrder(newOrder);

		assertTrue(orderService.getOrders().contains(newOrder));
		assertEquals(initialSize + 1, getOrders().size(), "Adding an order should increase repository size by 1");
	}

	//-------------------------------------------------
	//GetOrders Service Tests
	@Test
	void testGetOrdersReturnsEmptyList() {
		ArrayList<Order> orders = orderService.getOrders();
		assertTrue(orders.isEmpty(), "Order list should be empty when no orders exist");
	}

	@Test
	void testGetOrdersReturnsAddedOrders() {
		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 20.0, new ArrayList<>());
		orderService.addOrder(order);

		List<Order> orders = orderService.getOrders();

		assertTrue(orders.contains(order), "The added order should be present in the retrieved list");
	}

	@Test
	void testGetOrdersReturnsCorrectNumberOfOrders() {
		int initialSize = orderService.getOrders().size();

		Order order1 = new Order(UUID.randomUUID(), UUID.randomUUID(), 30.0, new ArrayList<>());
		Order order2 = new Order(UUID.randomUUID(), UUID.randomUUID(), 40.0, new ArrayList<>());

		orderService.addOrder(order1);
		orderService.addOrder(order2);

		List<Order> orders = orderService.getOrders();

		assertEquals(initialSize + 2, orders.size(), "getOrders() should return the correct number of orders");
	}

	//-------------------------------------------------
	//GetOrderById Service Tests
	@Test
	void testGetOrderByIdReturnsCorrectOrder() {
		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 50.0, new ArrayList<>());
		orderService.addOrder(order);

		Order retrievedOrder = orderService.getOrderById(order.getId());

		assertNotNull(retrievedOrder, "Retrieved order should not be null");
		assertEquals(order.getId(), retrievedOrder.getId(), "Order should be returned correctly by ID");
	}

	@Test
	void testGetOrderByIdReturnsNullForNonExistentOrder() {
		UUID randomId = UUID.randomUUID();
		assertNull(orderService.getOrderById(randomId), "No order should be found for a non-existent ID");
	}

	@Test
	void testGetOrderByIdReturnsNullAfterDeletion() {
		Order testOrder = new Order(UUID.randomUUID(), UUID.randomUUID(), 75.0, new ArrayList<>());
		orderService.addOrder(testOrder);

		assertNotNull(orderService.getOrderById(testOrder.getId()), "Order should exist before deletion");

		orderService.deleteOrderById(testOrder.getId());

		assertNull(orderService.getOrderById(testOrder.getId()), "Order should not be found after deletion");
	}

	//-------------------------------------------------
	//DeleteOrderById Service Tests
	@Test
	void testDeleteOrderByIdSuccessfully() {
		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 20.0, new ArrayList<>());
		orderService.addOrder(order);

		assertNotNull(orderService.getOrderById(order.getId()), "Order should exist before deletion");

		orderService.deleteOrderById(order.getId());

		assertNull(orderService.getOrderById(order.getId()), "Order should be deleted correctly");
	}

	@Test
	void testDeleteNonExistentOrderThrowsException() {
		UUID randomId = UUID.randomUUID();

		assertThrows(Exception.class, () -> {
			orderService.deleteOrderById(randomId);
		});
	}

	@Test
	void testDeleteOrderByIdReducesOrderListSize() {
		Order order1 = new Order(UUID.randomUUID(), UUID.randomUUID(), 30.0, new ArrayList<>());
		Order order2 = new Order(UUID.randomUUID(), UUID.randomUUID(), 40.0, new ArrayList<>());

		orderService.addOrder(order1);
		orderService.addOrder(order2);

		int initialSize = orderService.getOrders().size();

		orderService.deleteOrderById(order1.getId());

		int newSize = orderService.getOrders().size();

		assertEquals(initialSize - 1, newSize, "Deleting an order should decrease the repository size by 1");
	}

	// Test made addProductService
	@Test
	void testAddProductWithNullName() {
		Product testProduct = new Product(UUID.randomUUID(), null, 15.0);
		assertThrows(IllegalArgumentException.class, () -> {
			productService.addProduct(testProduct);
		});

	}
	@Test
	void testAddProductWithHighPrice() {
		Product testProduct = new Product(UUID.randomUUID(), "Luxury Item", 1000000.0);
		productService.addProduct(testProduct);
		Product addedProduct = productService.getProductById(testProduct.getId());
		assertEquals(1000000.0, addedProduct.getPrice(), "Should allow adding a high-priced product");
	}

	@Test
	void testAddDuplicateProduct() {
		Product testProduct = new Product(UUID.randomUUID(), "Duplicate Product", 20.0);
		productService.addProduct(testProduct);
		assertThrows(IllegalArgumentException.class, () -> productService.addProduct(testProduct));
	}

//Test made getproducts

	@Test
	void testGetProductsInitiallyEmpty() {
		List<Product> products = productService.getProducts();
		assertTrue(products.isEmpty(), "Product list should be empty initially");
	}

	@Test
	void testGetProductsAfterAddingOne() {
		Product testProduct = new Product(UUID.randomUUID(), "Test Product", 10.0);
		productService.addProduct(testProduct);
		List<Product> products = productService.getProducts();
		assertEquals(1, products.size(), "Should return one product after adding it");
		assertEquals("Test Product", products.get(0).getName(), "Product name should match");
	}

	@Test
	void testGetMultipleProducts() {
		Product product1 = new Product(UUID.randomUUID(), "Product A", 20.0);
		Product product2 = new Product(UUID.randomUUID(), "Product B", 30.0);
		Product product3 = new Product(UUID.randomUUID(), "Product C", 40.0);

		productService.addProduct(product1);
		productService.addProduct(product2);
		productService.addProduct(product3);

		List<Product> products = productService.getProducts();
		assertEquals(3, products.size(), "Should return all added products");
	}


	//Test made GetProductById

	@Test
	void testGetExistingProductById() {
		Product testProduct = new Product(UUID.randomUUID(), "Test Product", 10.0);
		productService.addProduct(testProduct);

		Product foundProduct = productService.getProductById(testProduct.getId());
		assertNotNull(foundProduct, "Product should be found");
		assertEquals(testProduct.getId(), foundProduct.getId(), "Product ID should match");
	}

	@Test
	void testGetProductByIdWhenNotExists() {
		Product foundProduct = productService.getProductById(UUID.randomUUID());
		assertNull(foundProduct, "Should return null when product does not exist");
	}

	@Test
	void testGetProductByIdAfterMultipleAdds() {
		Product product1 = new Product(UUID.randomUUID(), "Product A", 20.0);
		Product product2 = new Product(UUID.randomUUID(), "Product B", 30.0);
		productService.addProduct(product1);
		productService.addProduct(product2);

		Product foundProduct = productService.getProductById(product2.getId());
		assertNotNull(foundProduct, "Product should be found");
		assertEquals("Product B", foundProduct.getName(), "Should return correct product");assertEquals("Product B", foundProduct.getName(), "Should return correct product");
	}

	//Test made UpdateProduct
	@Test
	void testUpdateProductToInvalidValues() {
		Product testProduct = new Product(UUID.randomUUID(), "Valid Product", 30.0);
		productService.addProduct(testProduct);

		assertThrows(IllegalArgumentException.class, () -> {
			productService.updateProduct(testProduct.getId(), "", -5.0);
		});

	}

	@Test
	void testUpdateNonExistentProductThrowsError() {
		UUID randomId = UUID.randomUUID();
		assertThrows(NoSuchElementException.class, () -> {
			productService.updateProduct(randomId, "Updated Name", 20.0);
		});
	}

	@Test
	void testUpdateProductWithSameValues() {
		Product testProduct = new Product(UUID.randomUUID(), "Same Name", 15.0);
		productService.addProduct(testProduct);

		Product updatedProduct = productService.updateProduct(testProduct.getId(), "Same Name", 15.0);
		assertNotNull(updatedProduct, "Updated product should not be null");
		assertEquals("Same Name", updatedProduct.getName(), "Product name should remain the same");
		assertEquals(15.0, updatedProduct.getPrice(), "Product price should remain the same");
	}

	//Test made ApplyDiscount
	@Test
	void testApplyZeroDiscount() {
		Product testProduct = new Product(UUID.randomUUID(), "No Discount Product", 50.0);
		productService.addProduct(testProduct);

		ArrayList<UUID> productIds = new ArrayList<>();
		productIds.add(testProduct.getId());
		productService.applyDiscount(0.0, productIds);

		Product updatedProduct = productService.getProductById(testProduct.getId());
		assertEquals(50.0, updatedProduct.getPrice(), "Product price should remain unchanged with 0% discount");
	}

	@Test
	void testApplyFullDiscount() {
		Product testProduct = new Product(UUID.randomUUID(), "Free Product", 25.0);
		productService.addProduct(testProduct);

		ArrayList<UUID> productIds = new ArrayList<>();
		productIds.add(testProduct.getId());
		productService.applyDiscount(100.0, productIds);

		Product updatedProduct = productService.getProductById(testProduct.getId());
		assertEquals(0.0, updatedProduct.getPrice(), "Product price should be zero after 100% discount");
	}
	@Test
	void testApplyDiscountToNonExistentProduct() {
		ArrayList<UUID> productIds = new ArrayList<>();
		productIds.add(UUID.randomUUID());

		assertThrows(NoSuchElementException.class, () -> {
			productService.applyDiscount(10.0, productIds);
		});

	}


	//Test made Delete
	@Test
	void testDeleteExistingProduct() {
		Product testProduct = new Product(UUID.randomUUID(), "Test Product", 10.0);
		productService.addProduct(testProduct);

		productService.deleteProductById(testProduct.getId());
		Product foundProduct = productService.getProductById(testProduct.getId());
		assertNull(foundProduct, "Product should be deleted correctly");
	}


	@Test
	void testDeleteNonExistentProduct() {
		UUID randomId = UUID.randomUUID();
		assertThrows(NoSuchElementException.class, () -> {
			productService.deleteProductById(randomId);
		});
	}

	@Test
	void testDeleteProductAndEnsureListIsEmpty() {
		Product testProduct = new Product(UUID.randomUUID(), "Temporary Product", 25.0);
		productService.addProduct(testProduct);
		productService.deleteProductById(testProduct.getId());

		List<Product> products = productService.getProducts();
		assertTrue(products.isEmpty(), "Product list should be empty after deletion");
	}

	//test made AddCart
	@Test
	void testAddCartSuccessfully() {
		Cart testCart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
		cartRepository.addCart(testCart);
		Cart foundCart = cartRepository.getCartById(testCart.getId());
		assertNotNull(foundCart, "Cart should be added successfully");
	}

	@Test
	void testUserCannotHaveMultipleCarts() {
		UUID userId = UUID.randomUUID();
		Cart testCart1 = new Cart(UUID.randomUUID(), userId, new ArrayList<>());
		Cart testCart2 = new Cart(UUID.randomUUID(), userId, new ArrayList<>());
		cartRepository.addCart(testCart1);
		assertThrows(IllegalArgumentException.class, () -> {
			cartRepository.addCart(testCart2);
		});
	}

	@Test
	void testAddCartWithoutUserId() {
		assertThrows(NullPointerException.class, () -> {
			cartRepository.addCart(new Cart(UUID.randomUUID(), null, new ArrayList<>()));
		});
	}

	//test made getcarts
	@Test
	void testGetAllCartsInitiallyEmpty() {
		List<Cart> carts = cartRepository.getCarts();
		assertTrue(carts.isEmpty(), "Cart list should be empty initially");
	}

	@Test
	void testGetAllCartsAfterAddingOne() {
		cartRepository.addCart(new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>()));
		List<Cart> carts = cartRepository.getCarts();
		assertEquals(1, carts.size(), "Should return one cart after adding");
	}

	@Test
	void testGetAllCartsAfterAddingMultiple() {
		cartRepository.addCart(new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>()));
		cartRepository.addCart(new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>()));
		List<Cart> carts = cartRepository.getCarts();
		assertEquals(2, carts.size(), "Should return the correct number of carts");
	}

	//test getcartbyid
	@Test
	void testGetSpecificCartById() {
		Cart testCart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
		cartRepository.addCart(testCart);
		Cart foundCart = cartRepository.getCartById(testCart.getId());
		assertNotNull(foundCart, "Cart should be found");
		assertEquals(testCart.getId(), foundCart.getId(), "Cart ID should match");
	}

	@Test
	void testGetSpecificCartByNotExistingId() {
		UUID randomId = UUID.randomUUID();
		Cart foundCart = cartRepository.getCartById(randomId);
		assertNull(foundCart, "Cart should not be found");
	}

	@Test
	void testGetSpecificCartAfterAddingMultiple() {
		Cart cart1 = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
		Cart cart2 = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
		cartRepository.addCart(cart1);
		cartRepository.addCart(cart2);
		Cart foundCart = cartRepository.getCartById(cart2.getId());
		assertNotNull(foundCart, "Cart should be found");
		assertEquals(cart2.getId(), foundCart.getId(), "Should return the correct cart");
	}

	//test made GetCartByUserId
	@Test
	void testGetCartByUserId() {
		UUID userId = UUID.randomUUID();
		Cart testCart = new Cart(UUID.randomUUID(), userId, new ArrayList<>());
		cartRepository.addCart(testCart);
		Cart foundCart = cartRepository.getCartByUserId(userId);
		assertNotNull(foundCart, "User's cart should be found");
		assertEquals(userId, foundCart.getUserId(), "User ID should match");
	}

	@Test
	void testGetCartByUserIdAfterClearingCart() {
		UUID userId = UUID.randomUUID();
		Cart testCart = new Cart(UUID.randomUUID(), userId, new ArrayList<>());
		cartRepository.addCart(testCart);
		cartRepository.deleteCartById(testCart.getId());
		Cart foundCart = cartRepository.getCartByUserId(userId);
		assertNull(foundCart, "User's cart should not exist after deletion");
	}

	@Test
	void testGetCartByUserIdAfterAddingMultipleCartsForDifferentUsers() {
		UUID userId1 = UUID.randomUUID();
		UUID userId2 = UUID.randomUUID();
		Cart cart1 = new Cart(UUID.randomUUID(), userId1, new ArrayList<>());
		Cart cart2 = new Cart(UUID.randomUUID(), userId2, new ArrayList<>());
		cartRepository.addCart(cart1);
		cartRepository.addCart(cart2);

		Cart foundCart = cartRepository.getCartByUserId(userId1);
		assertNotNull(foundCart, "User's cart should be found");
		assertEquals(userId1, foundCart.getUserId(), "Should return the correct cart for the user");
	}

	//test made AddProductToCart

	@Test
	void testAddProductToCart() {
		Cart testCart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
		Product testProduct = new Product(UUID.randomUUID(), "Test Product", 15.0);
		cartRepository.addCart(testCart);
		cartRepository.addProductToCart(testCart.getId(), testProduct);
		assertTrue(cartRepository.getCartById(testCart.getId()).getProducts().contains(testProduct), "Product should be added to cart");
	}

	@Test
	void testAddMultipleProductsToCart() {
		Cart testCart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
		Product product1 = new Product(UUID.randomUUID(), "Product A", 20.0);
		Product product2 = new Product(UUID.randomUUID(), "Product B", 30.0);
		cartRepository.addCart(testCart);
		cartRepository.addProductToCart(testCart.getId(), product1);
		cartRepository.addProductToCart(testCart.getId(), product2);
		assertEquals(2, cartRepository.getCartById(testCart.getId()).getProducts().size(), "Both products should be added to cart");
	}

	@Test
	void testAddProductToNonExistentCart() {
		Product testProduct = new Product(UUID.randomUUID(), "Test Product", 15.0);
		UUID nonExistentCartId = UUID.randomUUID();

		assertThrows(NoSuchElementException.class, () -> {
			cartRepository.addProductToCart(nonExistentCartId, testProduct);
		});

	}



	//test made DeleteProductFromCart
	@Test
	void testDeleteProductFromCart() {
		Cart testCart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
		Product testProduct = new Product(UUID.randomUUID(), "Test Product", 15.0);
		cartRepository.addCart(testCart);
		cartRepository.addProductToCart(testCart.getId(), testProduct);
		cartRepository.deleteProductFromCart(testCart.getId(), testProduct);
		assertFalse(cartRepository.getCartById(testCart.getId()).getProducts().contains(testProduct), "Product should be removed from cart");
	}

	@Test
	void testDeleteProductNotInCart() {
		Cart testCart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
		Product testProduct = new Product(UUID.randomUUID(), "Test Product", 15.0);
		cartRepository.addCart(testCart);
		assertThrows(NoSuchElementException.class, () -> {
			cartRepository.deleteProductFromCart(testCart.getId(), testProduct);
		});
	}

	@Test
	void testDeleteProductFromNonExistentCart() {
		Product testProduct = new Product(UUID.randomUUID(), "Test Product", 15.0);
		UUID nonExistentCartId = UUID.randomUUID();
		assertThrows(NoSuchElementException.class, () -> {
			cartRepository.deleteProductFromCart(nonExistentCartId, testProduct);
		});
	}

	//test made DeleteCartById
	@Test
	void testDeleteCartById() {
		Cart testCart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
		cartRepository.addCart(testCart);
		cartRepository.deleteCartById(testCart.getId());

		Cart foundCart = cartRepository.getCartById(testCart.getId());
		assertNull(foundCart, "Deleted cart should not be found");
	}


	@Test
	void testDeleteNonExistentCart() {
		UUID nonExistentCartId = UUID.randomUUID();
		assertThrows(NoSuchElementException.class, () -> {
			cartRepository.deleteCartById(nonExistentCartId);
		});
	}

	@Test
	void testDeleteCartWithProducts() {
		Cart testCart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
		Product testProduct = new Product(UUID.randomUUID(), "Test Product", 20.0);
		cartRepository.addCart(testCart);
		cartRepository.addProductToCart(testCart.getId(), testProduct);
		cartRepository.deleteCartById(testCart.getId());
		Cart foundCart = cartRepository.getCartById(testCart.getId());
		assertNull(foundCart, "Deleted cart with products should not exist");
	}


}




