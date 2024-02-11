package com.joeyliao.linknoteresource.user.userdao;

import java.util.List;

public interface UserDAO {
    List<String> verifyUserByEmailAndUserId(String email, String userId);
}
