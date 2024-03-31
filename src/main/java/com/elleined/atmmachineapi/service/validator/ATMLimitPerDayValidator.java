package com.elleined.atmmachineapi.service.validator;

import com.elleined.atmmachineapi.model.User;

public interface ATMLimitPerDayValidator {

    boolean reachedLimitAmountPerDay(User currentUser);
}