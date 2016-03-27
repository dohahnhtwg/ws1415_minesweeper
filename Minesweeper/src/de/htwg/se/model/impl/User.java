package de.htwg.se.model.impl;

import de.htwg.se.model.IField;
import de.htwg.se.model.IStatistic;
import de.htwg.se.model.IUser;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;

public class User implements IUser {
    private String name;
    private byte[] encryptedPassword;
    private byte[] salt;
    private IField playingField;
    private IStatistic statistic;
    private String algorithm = "PBKDF2WithHmacSHA1";

	// TODO password will be probably changed to char[]
    public User(String name, String password) {
        this.name = name;
        try {
            this.salt = this.generateSalt();
            this.encryptedPassword = generateEncryptedPassword(password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException exc) {
            exc.printStackTrace();
        }
        playingField = new Field();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setPassword(String password) {
        try {
            this.encryptedPassword = generateEncryptedPassword(password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException exc) {
            exc.printStackTrace();
        }
    }

    @Override
    public String getEncryptedPassword() {
        return new String(this.encryptedPassword);
    }

    @Override
    public boolean authenticate(String name, String password) {
        try {
            byte[] encryptedAttemptedPassword = generateEncryptedPassword(password);
            return this.name.equals(name) && Arrays.equals(this.encryptedPassword, encryptedAttemptedPassword);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException exc) {
            exc.printStackTrace();
            return false;
        }
    }

    @Override
    public IStatistic getStatistic() {
        return this.statistic;
    }

    @Override
    public void setStatistic(IStatistic statistic) {
        this.statistic = statistic;
    }

    private byte[] generateEncryptedPassword(String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        int derivedKeyLength = 160;
        int iterations = 20000;
        KeySpec spec = new PBEKeySpec(password.toCharArray(), this.salt, iterations, derivedKeyLength);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm);
        return factory.generateSecret(spec).getEncoded();
    }

    private byte[] generateSalt() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[8];
        random.nextBytes(salt);
        return salt;
    }

	public IField getPlayingField() {
		return playingField;
	}

	public void setPlayingField(IField playingField) {
		this.playingField = playingField;
	}
	
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}
	
}
