package pacman.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {

    Player player; //NOPMD helper field for keeping track throughout the tests

    @BeforeEach
    public void init() {
        player = new Player();
    }

    @Test
    public void testExists() {
        assertNotNull(player);
    }

    @Test
    void testGetSetUsername() {
        player.setUsername("TestName");
        assertEquals("TestName", player.getUsername());

    }

    @Test
    void testGetSetScore() {
        player.updateScore(33);
        assertEquals(33, player.getScore().get());
    }

}