package net.lyczak.MineMath.plot;

import org.bukkit.Material;
import org.bukkit.util.Vector;

public class DefaultMaterialProvider implements PlotMaterialProvider {
    private Material point;
    private Material i;
    private Material j;
    private Material k;
    private Material origin;

    public DefaultMaterialProvider(ColorScheme colorScheme) {
        switch (colorScheme) {
            case WOOL:
                point = Material.WHITE_WOOL;
                i = Material.RED_WOOL;
                j = Material.GREEN_WOOL;
                k = Material.BLUE_WOOL;
                origin = Material.GRAY_WOOL;
                break;
            case WOOL_LIGHT:
                point = Material.WHITE_WOOL;
                i = Material.PINK_WOOL;
                j = Material.LIME_WOOL;
                k = Material.LIGHT_BLUE_WOOL;
                origin = Material.LIGHT_GRAY_WOOL;
                break;
            case STAINED_GLASS_LIGHT:
                point = Material.WHITE_STAINED_GLASS;
                i = Material.PINK_STAINED_GLASS;
                j = Material.LIME_STAINED_GLASS;
                k = Material.LIGHT_BLUE_STAINED_GLASS;
                origin = Material.LIGHT_GRAY_STAINED_GLASS;
                break;
            default:
            case STAINED_GLASS:
                point = Material.WHITE_STAINED_GLASS;
                i = Material.RED_STAINED_GLASS;
                j = Material.GREEN_STAINED_GLASS;
                k = Material.BLUE_STAINED_GLASS;
                origin = Material.GRAY_STAINED_GLASS;
                break;
        }
    }

    public DefaultMaterialProvider(ColorScheme colorScheme, Material point) {
        this(colorScheme);
        this.point = point;
    }

    public DefaultMaterialProvider(Material point) {
        this();
        this.point = point;
    }

    public DefaultMaterialProvider() {
        this(ColorScheme.STAINED_GLASS);
    }

    public Material getPoint(Vector normalizedPosition) {
        return point;
    }

    public Material getPoint() {
        return point;
    }

    public void setPoint(Material material) {
        this.point = material;
    }

    public Material getAxis(int axis) {
        switch (axis) {
            case 0:
                return i;
            case 1:
                return j;
            case 2:
                return k;
            default:
                return origin;
        }
    }

    public void setIAxis(Material i) {
        this.i = i;
    }

    public void setJAxis(Material j) {
        this.j = j;
    }

    public void setKAxis(Material k) {
        this.k = k;
    }

    public Material getOrigin() {
        return origin;
    }

    public void setOrigin(Material origin) {
        this.origin = origin;
    }

    public enum ColorScheme {
        WOOL,
        WOOL_LIGHT,
        STAINED_GLASS,
        STAINED_GLASS_LIGHT
    }
}
