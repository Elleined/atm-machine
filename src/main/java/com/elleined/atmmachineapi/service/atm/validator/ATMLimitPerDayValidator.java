package com.elleined.atmmachineapi.service.atm.validator;

import com.elleined.atmmachineapi.model.User;

public interface ATMLimitPerDayValidator {

    boolean reachedLimitAmountPerDay(User currentUser);
}