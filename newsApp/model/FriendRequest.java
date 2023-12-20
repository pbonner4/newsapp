package newsApp.model;

public class FriendRequest {
    private int request_id;
    private int userIdRequestor;
    private String usernameRequestor;
    private int userIdRequestee;

    private String usernameRequestee;

    private boolean request_pending_or_accepted;

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public int getUserIdReqestor() {
        return userIdRequestor;
    }

    public void setUserIdReqestor(int userIdReqestor) {
        this.userIdRequestor = userIdReqestor;
    }

    public String getUsernameRequestor() {
        return usernameRequestor;
    }

    public void setUsernameRequestor(String usernameRequestor) {
        this.usernameRequestor = usernameRequestor;
    }

    public int getUserIdRequestee() {
        return userIdRequestee;
    }

    public void setUserIdRequestee(int userIdRequestee) {
        this.userIdRequestee = userIdRequestee;
    }

    public String getUsernameRequestee() {
        return usernameRequestee;
    }

    public void setUsernameRequestee(String usernameRequestee) {
        this.usernameRequestee = usernameRequestee;
    }

    public boolean isRequest_pending_or_accepted() {
        return request_pending_or_accepted;
    }

    public void setRequest_pending_or_accepted(boolean request_pending_or_accepted) {
        this.request_pending_or_accepted = request_pending_or_accepted;
    }
}
