package com.github.openwebnet.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GatewayModelTest {

    @Test(expected = NullPointerException.class)
    public void testNewGateway_nullHost() {
        GatewayModel.newGateway(null, 0, null);
    }

    @Test(expected = NullPointerException.class)
    public void testNewGateway_nullPort() {
        GatewayModel.newGateway("", null, null);
    }

    @Test
    public void testNewGateway_success() {
        String GATEWAY_HOST = "HOST";
        Integer GATEWAY_PORT = 123;
        String GATEWAY_PASSWORD = "PASSWORD";

        GatewayModel gateway = GatewayModel.newGateway(GATEWAY_HOST, GATEWAY_PORT, GATEWAY_PASSWORD);

        assertNotNull("invalid uuid", gateway.getUuid());
        assertEquals("invalid host", GATEWAY_HOST, gateway.getHost());
        assertEquals("invalid port", GATEWAY_PORT, gateway.getPort());
        assertEquals("invalid password", GATEWAY_PASSWORD, gateway.getPassword());
    }
}
