package com.example.sajbot.service;

import com.example.sajbot.model.SajRealtimeData;
import com.ghgande.j2mod.modbus.facade.ModbusTCPMaster;
import com.ghgande.j2mod.modbus.procimg.Register;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SajInverterService {
    private final String host;
    private final int port;

    public SajInverterService(@Value("${saj.host}") String host,
                              @Value("${saj.port}") int port) {
        this.host = host;
        this.port = port;
    }

    public SajRealtimeData readRealtimeData() {
        ModbusTCPMaster master = new ModbusTCPMaster(host, port);
        try {
            master.connect();
            Register[] regs = master.readMultipleRegisters(1, 0x100, 60);
            int[] r = new int[regs.length];
            for (int i = 0; i < regs.length; i++) {
                r[i] = regs[i].getValue();
            }
            return SajRealtimeData.parse(r);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read SAJ inverter", e);
        } finally {
            try {
                master.disconnect();
            } catch (Exception ignore) {}
        }
    }
}
