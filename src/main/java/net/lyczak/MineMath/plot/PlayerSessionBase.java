package net.lyczak.MineMath.plot;

import org.mariuszgromada.math.mxparser.Function;

import java.util.Map;
import java.util.TreeMap;

public class PlayerSessionBase {
    private Plot activePlot;
    private PlotOptions options = new PlotOptions();
    private Map<String, Function> functions = new TreeMap<String, Function>();

    public Plot getActivePlot() {
        return activePlot;
    }

    public void setActivePlot(Plot activePlot) {
        this.activePlot = activePlot;
    }

    public PlotOptions getOptions() {
        return options;
    }

    public void setOptions(PlotOptions options) {
        this.options = options;
    }

    public Map<String, Function> getFunctions() {
        return functions;
    }

    public void setFunctions(Map<String, Function> functions) {
        this.functions = functions;
    }
}
