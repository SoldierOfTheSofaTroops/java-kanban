package ru.dk;

import org.junit.jupiter.api.Test;
import ru.dk.core.Managers;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    void getDefaultHistory() {
        assertNotNull(Managers.getDefaultHistory(), "Manager is null");
    }
}