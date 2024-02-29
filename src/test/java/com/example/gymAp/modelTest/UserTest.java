package com.example.gymAp.modelTest;

import com.example.gymAp.model.Trainee;
import com.example.gymAp.model.Trainer;
import com.example.gymAp.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTest {

    @Test
    void testGettersAndSetters() {
        User user = new User();

        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUserName("john.doe");
        user.setPassword("password");
        user.setIsActive(true);

        assertEquals(1L, user.getId());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john.doe", user.getUserName());
        assertEquals("password", user.getPassword());
        assertTrue(user.getIsActive());
    }

    @Test
    void testEqualsAndHashCode() {
        User user1 = new User();
        user1.setId(1L);

        User user2 = new User();
        user2.setId(1L);

        User user3 = new User();
        user3.setId(2L);

        assertEquals(user1, user2);
        assertNotEquals(user1, user3);

        assertEquals(user1.hashCode(), user2.hashCode());
        assertNotEquals(user1.hashCode(), user3.hashCode());
    }

    @Test
    public void testToString() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setIsActive(true);

        String result = user.toString();

        assertEquals("firstName='John', lastName='Doe', isActive=true\t", result);
    }
    @Test
    void testUserEquality() {
        User user1 = new User();
        user1.setId(1L);

        User user2 = new User();
        user2.setId(1L);

        User user3 = new User();
        user3.setId(2L);

        assertEquals(user1, user2);
        assertNotEquals(user1, user3);
        assertNotEquals(user2, user3);

        assertEquals(user1.hashCode(), user2.hashCode());
        assertNotEquals(user1.hashCode(), user3.hashCode());
        assertNotEquals(user2.hashCode(), user3.hashCode());
    }


    @Test
    void testTrainerGetterSetter() {
        Trainer trainer = new Trainer();
        User user = new User();

        user.setTrainer(trainer);
        Trainer retrievedTrainer = user.getTrainer();

        assertEquals(retrievedTrainer, trainer);
    }

    @Test
    void testTraineeGetterSetter() {
        Trainee trainee = new Trainee();
        User user = new User();

        user.setTrainee(trainee);
        Trainee retrievedTrainee = user.getTrainee();

        assertEquals(retrievedTrainee, trainee);
    }
}