package models.wifimessages;

public class ClientMessage extends WifiMessage {

    public ClientMessage(String sender, String type) {
        super(sender, type);
    }

    public static class UsernameRequestMessage extends ClientMessage {
        private String username;

        public String getUsername() {
            return username;
        }

        public UsernameRequestMessage(String sender, String username) {
            super(sender, "client_Req_Username");
            this.username=username;
        }
    }

    public static class UsernameResponseMessage extends ClientMessage {
        private String username;

        public String getUsername() {
            return username;
        }

        public UsernameResponseMessage(String sender, String username) {
            super(sender, "client_Resp_Username");
            this.username=username;
        }
    }
}