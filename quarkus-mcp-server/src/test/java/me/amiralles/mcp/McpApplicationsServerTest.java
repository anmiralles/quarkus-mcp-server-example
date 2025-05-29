package me.amiralles.mcp;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class McpApplicationsServerTest {

    @Inject
    McpApplicationsServer mcpServer;

    @Test
    void testMcpServerInjection() {
        assertNotNull(mcpServer);
    }
}