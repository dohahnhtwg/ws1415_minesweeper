package de.htwg.se.model;

public interface IUser {
	/**
	 * Returns the user name
	 * @return user name
	 */
    String getName();
    
    /**
     * Changes the user name
     * @param name new user name
     */
    void setName(String name);
    
    /**
     * Changes the password
     * @param password new passwors
     */
    void setPassword(String password);
    
    /**
     * Returns the encrypted password
     * @return encrypted password
     */
    byte[] getEncryptedPassword();

    /**
     *
     * @param encryptedPassword
     */
    void setEncryptedPassword(byte[] encryptedPassword);

    /**
     * Authenticates the user
     * @param name
     * @param password
     * @return Returns true if password and name is correct
     */
    boolean authenticate(String name, String password);
    
    /**
     * Returns statistic of this user
     * @return bound IStatistic instance
     */
    IStatistic getStatistic();
    
    /**
     * Changes the statistic
     * @param statistic reference to IStatistic class
     */
    void setStatistic(IStatistic statistic);
    
    /**
     * Get the field of this user
     * @return Returns the field of this user
     */
    IField getPlayingField();
    
    /**
     * Changes the field of this user
     * @param playingField
     */
    void setPlayingField(IField playingField);
    
    /**
     * Changes the algorithms which is used to cipher the password
     * @param algorithm
     */
    void setAlgorithm(String algorithm);

    /**
     * Get current algorithm
     * @return string
     */
    String getAlgorithm();

    /**
     * Get user id
     * @return int
     */
    String getId();

    /**
     * Set user id
     * @param id int
     */
    void setId(String id);

    /**
     * Get salt
     * @return byte[]
     */
    byte[] getSalt();

    /**
     * Set salt
     * @param salt byte[]
     */
    void setSalt(byte[] salt);
}
