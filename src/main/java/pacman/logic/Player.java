package pacman.logic;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableIntegerValue;

@SuppressWarnings("PMD.BeanMembersShouldSerialize") // Class is not a bean.
public class Player {

    private String username;
    private IntegerProperty score = new SimpleIntegerProperty();
    private IntegerProperty lives = new SimpleIntegerProperty(3);

    public ObservableIntegerValue getLives() {
        return lives;
    }

    public boolean hasLives() {
        return lives.get() > 0;
    }

    public void loseLife() {
        lives.set(lives.get() - 1);
    }

    public ObservableIntegerValue getScore() {
        return score;
    }

    public void updateScore(int score) {
        this.score.set(this.score.get() + score);
    }

    public void setUsername(String nm) {
        username = nm;
    }

    public String getUsername() {
        return username;
    }

    public void newLevel() {
        lives.set(3);
    }
}
