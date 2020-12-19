public class User implements IUser {
    private String userName;

    public User(String userName) {
        this.userName = userName;
    }

    @Override
    public void changeName(String newName) {
        this.userName = newName;
    }

    public String getName() {
        return this.userName;
    }
}
