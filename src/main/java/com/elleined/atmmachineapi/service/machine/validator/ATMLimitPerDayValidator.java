package com.elleined.atmmachineapi.service.machine.validator;

import com.elleined.atmmachineapi.model.User;

public interface ATMLimitPerDayValidator {

    boolean reachedLimitAmountPerDay(User currentUser);
}