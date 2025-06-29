@SpringBootTest
public class FileUploadUtilTest {

    @Test
    public void testSaveFileThrowsException() {
        MultipartFile mockFile = Mockito.mock(MultipartFile.class);
        when(mockFile.getInputStream()).thenThrow(IOException.class);

        assertThrows(IOException.class, () -> FileUploadUtil.saveFile(mockFile));
    }
}
