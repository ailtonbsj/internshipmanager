package io.github.ailtonbsj.internshipmanager;

public class Config {
	public static final OperatingSystem OS = OperatingSystem.LINUX;
	public static final String HOST = "localhost";
	public static final String PORT = "5432";
	public static final String USERNAME = "postgres";
    public static final String PASSWORD = "postgres";
    public static final String DATABASE = "internshipdb";
    public static final String DATABASE_URL = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DATABASE;
}
