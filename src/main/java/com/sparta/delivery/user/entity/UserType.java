package com.sparta.delivery.user.entity;

public enum UserType {
    CUSTOMER(Authority.CUSTOMER), // 손님
    OWNER(Authority.OWNER);  // 사장님

    private final String authority;

    UserType(String authority) {
        this.authority = authority;
    }
    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String CUSTOMER = "ROLE_CUSTOMER";
        public static final String OWNER = "ROLE_OWNER";
    }

}
