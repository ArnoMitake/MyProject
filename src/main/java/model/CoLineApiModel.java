package model;

import java.util.ArrayList;

public class CoLineApiModel extends BaseModel {
    public Data data;
    public boolean success;
    public String message;

    class Data extends BaseModel {
        public ArrayList<User> users;
        public String next;
        public String event_id;

        class User extends BaseModel {
            public String user_id;
            public String name;
            public String nickname;
            public String email;
            public String account_type;
            public String phone;
            public ArrayList<String> branches;
            public String employee_no;
            public int role;
        }
    }
}

