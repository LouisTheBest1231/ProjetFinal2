package com.example.projetfinal;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
/**
 * The class used as the database's object
 */
@Entity
public class User {
    /**
     * The identifier that differentiate each user, cannot be decided or changed
     */
    @PrimaryKey(autoGenerate = true)
    public int uid;

    /**
     * the score of the user
     */
    @ColumnInfo(name = "score")
    public int score;

    /**
     * The pieces of the user
     */
    @ColumnInfo(name = "pieces")
    public int pieces;

    /**
     * The skins of the user
     */
    @ColumnInfo(name = "skins")
    public Boolean skins[];

    /**
     * The selected skin of the user
     */
    @ColumnInfo(name = "selectedSkin")
    public int selectedSkin;

    /**
     * The sound setting of the user
     */
    @ColumnInfo(name = "sound")
    public int sound;

    /**
     * The basic constructor of the User containing the essentials
     * @param pieces
     * @param score
     */
    public User(int score, int pieces) {
        this.score = score;
        this.pieces = pieces;
        this.sound = 100;
        this.skins = new Boolean[]{true, false, false, false, false, false, false, false, false, false};
        this.selectedSkin = 0;
    }

    /**
     *
     * @return uid
     */
    public int getUid() {
        return uid;
    }

    /**
     *
     * @return score
     */
    public int getScore() {
        return score;
    }

    /**
     *
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     *
     * @return sound
     */
    public int getSound() {
        return sound;
    }

    /**
     *
     * @param sound
     */
    public void setSound(int sound) {
        this.sound = sound;
    }

    /**
     *
     * @return pieces
     */
    public int getPieces() {
        return pieces;
    }

    /**
     *
     * @param pieces
     */
    public void setPieces(int pieces) {
        this.pieces = pieces;
    }

    /**
     *
     * @return skins
     */
    public Boolean[] getSkins() {
        return skins;
    }

    /**
     *
     * @param skins
     */
    public void setSkins(Boolean[] skins) {
        this.skins = skins;
    }

    /**
     *
     * @return selectedSkin
     */
    public int getSelectedSkin() {
        return selectedSkin;
    }

    /**
     *
     * @param selectedSkin
     */
    public void setSelectedSkin(int selectedSkin) {
        this.selectedSkin = selectedSkin;
    }
}
