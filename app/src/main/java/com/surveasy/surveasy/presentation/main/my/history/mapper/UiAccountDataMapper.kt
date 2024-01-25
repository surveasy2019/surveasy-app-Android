package com.surveasy.surveasy.presentation.main.my.history.mapper

import com.surveasy.surveasy.domain.model.AccountInfo
import com.surveasy.surveasy.presentation.main.my.history.model.UiAccountData

fun AccountInfo.toUiAccountData(): UiAccountData = UiAccountData(
    reward = rewardCurrent,
    account = accountNumber,
    owner = accountOwner,
    bank = accountType
)