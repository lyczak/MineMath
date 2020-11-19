package net.lyczak.MineMath.plot;

import org.bukkit.Material;
import org.bukkit.util.Vector;

public interface PlotMaterialProvider {
    Material getPoint(Vector normalizedPosition);
    Material getAxis(int index);
    Material getOrigin();
}
