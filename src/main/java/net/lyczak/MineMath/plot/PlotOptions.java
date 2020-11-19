package net.lyczak.MineMath.plot;

import org.bukkit.util.Vector;
import org.mariuszgromada.math.mxparser.Function;

public class PlotOptions {
    private Function ri;
    private Function rj;
    private Function rk;

    private Vector min = new Vector(-1, -1, -1);
    private Vector range = new Vector(2, 2, 2);

    private double uMin = -1;
    private double uRange = 2;
    private double vMin = -1;
    private double vRange = 2;
    private Integer uSamples;
    private Integer vSamples;

    private PlotMaterialProvider materialProvider = new DefaultMaterialProvider();
    private Vector normalizedAxesPosition = new Vector().zero();
    private boolean axesSwapped = true;

    public Vector calcNormalizedPosition(double u, double v) {
        u = u * uRange + uMin;
        v = v * vRange + vMin;

        Vector r = new Vector(
                ri.calculate(u, v),
                rj.calculate(u, v),
                rk.calculate(u, v));
        r.subtract(min).divide(range);

        return axesSwapped ? new Vector(r.getX(), r.getZ(), r.getY()) : r;
    }

    public Function getIFunction() {
        return ri;
    }

    public void setIFunction(Function ri) {
        this.ri = ri;
    }

    public Function getJFunction() {
        return rj;
    }

    public void setJFunction(Function rj) {
        this.rj = rj;
    }

    public Function getKFunction() {
        return rk;
    }

    public void setKFunction(Function rk) {
        this.rk = rk;
    }

    public void setIBounds(double min, double max) {
        this.min.setX(min);
        this.range.setX(max - min);
    }

    public void setJBounds(double min, double max) {
        this.min.setY(min);
        this.range.setY(max - min);
    }

    public void setKBounds(double min, double max) {
        this.min.setZ(min);
        this.range.setZ(max - min);
    }

    public Vector getNormalizedAxesPosition() {
        return normalizedAxesPosition;
    }

    public void setAxesCenter() {
        normalizedAxesPosition = new Vector(0.5, 0.5, 0.5);
    }

    public void setAxesCorner() {
        normalizedAxesPosition = new Vector().zero();
    }

    public void setAxesZero() {
        normalizedAxesPosition = min.clone().divide(range);
    }

    public double getUMin() {
        return uMin;
    }

    public double getVMin() {
        return vMin;
    }

    public double geURange() {
        return uRange;
    }

    public double getVRange() {
        return vRange;
    }

    public void setUBounds(double uMin, double uMax) {
        this.uMin = uMin;
        this.uRange = uMax - this.uMin;
    }

    public void setVBounds(double vMin, double vMax) {
        this.vMin = vMin;
        this.vRange = vMax - this.vMin;
    }

    public Integer getUSamples() {
        return uSamples;
    }

    public Integer getVSamples() {
        return vSamples;
    }

    public void setUSamples(Integer uSamples) {
        if(uSamples == null || uSamples <= 0) {
            this.uSamples = null;
            return;
        }
        this.uSamples = uSamples;
    }

    public void setVSamples(Integer vSamples) {
        if(vSamples == null || vSamples <= 0) {
            this.vSamples = null;
            return;
        }
        this.vSamples = vSamples;
    }

    public PlotMaterialProvider getMaterialProvider() {
        return materialProvider;
    }

    public void setMaterialProvider(PlotMaterialProvider materialProvider) {
        this.materialProvider = materialProvider;
    }

    public boolean areAxesSwapped() {
        return axesSwapped;
    }

    public void setAxesSwapped(boolean axesSwapped) {
        this.axesSwapped = axesSwapped;
    }
}
