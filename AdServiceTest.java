@SpringBootTest
public class AdServiceTest {

    @Mock
    private AdRepository adRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdService adService;

    @Test
    public void testCreateAdSuccess() {
        User user = new User();
        user.setId(UUID.randomUUID());

        Ad ad = new Ad();
        ad.setId(1L);

        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(user));
        when(adRepository.save(any(Ad.class))).thenReturn(ad);

        AdResponseDto dto = adService.createAd(new AdRequestDto(), "photo.jpg", user.getId());

        assertEquals(1L, dto.getId());
        verify(userRepository).findById(any(UUID.class));
        verify(adRepository).save(any(Ad.class));
    }

    @Test
    public void testCreateAdUserNotFound() {
        UUID randomId = UUID.randomUUID();
        when(userRepository.findById(randomId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> adService.createAd(new AdRequestDto(), "photo.jpg", randomId));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void testGetAdByIdNotFound() {
        when(adRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> adService.getAdById(1L));
        assertEquals("Ad not found", exception.getMessage());
    }
}
