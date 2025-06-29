@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testRegisterSuccess() {
        UserRegisterDto dto = new UserRegisterDto();
        dto.setUsername("TestUser12");
        dto.setEmail("test@example.com");
        dto.setPassword("password");
        dto.setBirthday(LocalDate.of(2000, 1, 1));

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        userService.register(dto);

        verify(userRepository).save(any(User.class));
    }

    @Test
    public void testRegisterInvalidUsername() {
        UserRegisterDto dto = new UserRegisterDto();
        dto.setUsername("abc");
        dto.setEmail("test@example.com");
        dto.setPassword("password");
        dto.setBirthday(LocalDate.of(2000, 1, 1));

        Exception exception = assertThrows(RuntimeException.class, () -> userService.register(dto));
        assertEquals("Invalid username format.", exception.getMessage());
    }

    @Test
    public void testLoginSuccess() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("TestUser12");
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(userRepository.findByUsernameOrEmail("TestUser12", "TestUser12")).thenReturn(Optional.of(user));

        User loggedIn = userService.login(new UserLoginDto("TestUser12", "password"));

        assertEquals("TestUser12", loggedIn.getUsername());
        verify(userRepository).findByUsernameOrEmail("TestUser12", "TestUser12");
    }

    @Test
    public void testLoginInvalidCredentials() {
        when(userRepository.findByUsernameOrEmail("WrongUser", "WrongUser")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.login(new UserLoginDto("WrongUser", "password")));
        assertEquals("Invalid credentials", exception.getMessage());
    }
}
