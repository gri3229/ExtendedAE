package com.glodblock.github.appflux.common.parts;

import appeng.api.networking.GridFlags;
import appeng.api.networking.IGridNode;
import appeng.api.networking.energy.IEnergyService;
import appeng.api.networking.security.IActionSource;
import appeng.api.networking.storage.IStorageService;
import appeng.api.networking.ticking.IGridTickable;
import appeng.api.networking.ticking.TickRateModulation;
import appeng.api.networking.ticking.TickingRequest;
import appeng.api.parts.IPartCollisionHelper;
import appeng.api.parts.IPartItem;
import appeng.api.parts.IPartModel;
import appeng.api.util.AECableType;
import appeng.parts.AEBasePart;
import appeng.parts.PartModel;
import com.glodblock.github.appflux.AppFlux;
import com.glodblock.github.appflux.common.me.energy.CapAdaptor;
import com.glodblock.github.appflux.common.me.energy.EnergyDistributor;
import com.glodblock.github.appflux.config.AFConfig;
import com.glodblock.github.appflux.util.AFUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class PartFluxAccessor extends AEBasePart implements IGridTickable {

    public static final ResourceLocation RL = AppFlux.id("part/flux_accessor");
    public static final IPartModel MODEL = new PartModel(RL);

    public PartFluxAccessor(IPartItem<?> partItem) {
        super(partItem);
        this.getMainNode().setFlags(GridFlags.REQUIRE_CHANNEL);
        this.getMainNode().setIdlePowerUsage(1.0).addService(IGridTickable.class, this);
    }

    @Override
    public void getBoxes(IPartCollisionHelper bch) {
        bch.addBox(2.0, 2.0, 14.0, 14.0, 14.0, 16.0);
        bch.addBox(4.0, 4.0, 12.0, 12.0, 12.0, 14.0);
    }

    @Override
    public float getCableConnectionLength(AECableType cable) {
        return 2.0F;
    }

    @Override
    public IPartModel getStaticModels() {
        return MODEL;
    }

    private IStorageService getStorage() {
        if (this.getGridNode() != null) {
            return this.getGridNode().getGrid().getStorageService();
        }
        return null;
    }

    private IActionSource getSource() {
        return IActionSource.ofMachine(this);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap) {
        CapAdaptor.Factory<T> handler = CapAdaptor.find(cap);
        if (handler != null) {
            return LazyOptional.of(() -> handler.create(this.getStorage(), this.getSource()));
        }
        return super.getCapability(cap);
    }

    @Override
    public TickingRequest getTickingRequest(IGridNode iGridNode) {
        return new TickingRequest(1, 1, false, false);
    }

    @Override
    public TickRateModulation tickingRequest(IGridNode iGridNode, int i) {
        var storage = this.getStorage();
        var d = this.getSide();
        var gird = this.getGridNode() == null ? null : this.getGridNode().getGrid();
        if (storage != null && d != null && this.getLevel() != null) {
            var te = this.getLevel().getBlockEntity(this.getBlockEntity().getBlockPos().offset(d.getNormal()));
            var thatGrid = AFUtil.getGrid(te, d.getOpposite());
            if (te != null && thatGrid != gird && !AFUtil.isBlackListTE(te, d.getOpposite())) {
                EnergyDistributor.send(te, d.getOpposite(), storage, this.getSource());
            }
            if (AFConfig.selfCharge() && gird != null) {
                EnergyDistributor.chargeNetwork(gird.getService(IEnergyService.class), storage, this.getSource());
            }
        }
        return TickRateModulation.SAME;
    }

}