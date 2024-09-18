package com.ala.book.email;

import lombok.Getter;

@Getter
public enum EmailTemplateName {
    ActivateAccount("activate_account.html")
    ;
    private final String name;
    EmailTemplateName(String name) {
        this.name = name;
    }

}
