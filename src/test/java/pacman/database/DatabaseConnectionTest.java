package pacman.database;

import java.sql.Connection;

import org.junit.Assert;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

@PrepareForTest({DbConnect.class})
@SuppressWarnings("PMD")
public class DatabaseConnectionTest {


    private DbConnect dbConnect;

    private DbConnect newDbConnect;
    private Connection connection;

    @Test
    public void testdbconnection() throws Exception {
        newDbConnect = new DbConnect();
        connection = newDbConnect.getMyConnection();
        Assert.assertNotNull(connection);
    }

    @Test
    public void testdbconnectionmockito() {
        dbConnect = Mockito.mock(DbConnect.class);
        Mockito.when(dbConnect.getMyConnection()).thenReturn(connection);
    }
}
