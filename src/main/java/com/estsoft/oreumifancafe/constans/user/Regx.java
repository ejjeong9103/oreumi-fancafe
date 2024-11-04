package com.estsoft.oreumifancafe.constans.user;

import lombok.Getter;

@Getter
public enum Regx {
    PASSWORD_PATTERN("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$");

    private String regex;

    Regx(String regex) {
        this.regex = regex;
    }

}
