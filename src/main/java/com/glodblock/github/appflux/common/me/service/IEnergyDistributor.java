package com.glodblock.github.appflux.common.me.service;

import appeng.api.networking.IGridNodeService;

public interface IEnergyDistributor extends IGridNodeService {

    void distribute();

    default void charge() {

    }

}
