package com.example.projetfinal;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * An interface (Dao) for a database of User objects
 */
@Dao
public interface UserDao {
    /**
     * Return a List of all users
     * @return List<user>
     */
    @Query("SELECT * FROM user")
    List<User> getAll();

    /**
     * Return a List of users corresponding to the uids that have been inputted
     * @param userIds
     * @return List<user>
     */
    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    /**
     * Return the specific User of the uid inputted
     * @param uid
     * @return User
     */
    @Query("Select * From user Where uid =:uid")
    User findById(int uid);

    /**
     * Returns the user possessing the inputted pieces
     * @param pieces
     * @return User
     */
    @Query("SELECT * FROM user WHERE pieces LIKE :pieces")
    User findByPieces(int pieces);

    /**
     * Returns the user possessing the inputted pieces
     * @param skins
     * @return User
     */
    @Query("SELECT * FROM user WHERE pieces LIKE :skins")
    User findBySkins(Boolean[] skins);

    /**
     * Returns the user possessing the inputted score
     * @param score
     * @return User
     */
    @Query("SELECT * FROM user WHERE score LIKE :score")
    User findByScore(int score);

    /**
     * Inserts all the inputted users  in the database
     * @param users
     */
    @Insert
    void insertAll(User... users);

    /**
     * Insert the inputted user in the database
     * @param user
     */
    @Insert
    void insert(User user);

    /**
     * Delete the user in the database possessing the same variables
     * @param user
     */
    @Delete
    void delete(User user);
}
