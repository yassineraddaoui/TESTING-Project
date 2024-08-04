package com.ala.book.email;

public enum EmailTemplateName {
    ActivateAccount("activate_account.html")
    ;
    private final String name;
    EmailTemplateName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
