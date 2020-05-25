package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.api.ApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class AppTest {

    @Autowired
    private ApiService apiService;

    @BeforeEach
    void init() {
        apiService.clearMessages();
    }

    @Test
    public void sendOneMessageTest() throws Exception {
        genMessages(1);
        var msgs = apiService.getLast100Messages();
        assertNotNull(msgs);
        assertTrue(msgs.get(0).contains("Hello1"));
    }

    @Test
    public void send120MessageAndGetLast100Test() throws Exception {
        genMessages(120);
        var msgs = apiService.getLast100Messages();
        assertNotNull(msgs);
        assertEquals(100, msgs.size());
    }

    @Test
    public void testPaging() throws Exception {
        genMessages(120);
        var msgs = apiService.getMessages(0, 10);
        assertNotNull(msgs);
        assertEquals(10, msgs.size());
    }

    private void genMessages(int total) {
        int i = 1;
        do {
            apiService.sendMessage(CreateMessageRequest.builder()
                    .user(User.builder()
                            .userName("UserName" + i)
                            .build())
                    .message("Hello" + i)
                    .build());
            i++;
        } while (i <= total);
        awaitAsyncProcessing();
    }

    private void awaitAsyncProcessing() {
        try {
            sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
