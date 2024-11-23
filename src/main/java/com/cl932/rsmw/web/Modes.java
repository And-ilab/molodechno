package com.cl932.rsmw.web;

public enum Modes {
    MODE1(new double[]{290, 3663, 0, 281.3}),
    MODE2(new double[]{322.6, 3663, 73.5, 268.3}),
    MODE3(new double[]{271.1, 3663, 0, 250.6}),
    MODE4(new double[]{312.1, 3663, 82.6, 259.9}),
    MODE5(new double[]{236.6, 3663, 0, 191.5}),
    MODE6(new double[]{284.2, 3663, 74.8, 230.3}),
    MODE7(new double[]{234.2, 3663, 0, 185.4}),
    MODE8(new double[]{273.6, 3663, 62.5, 217.1}),
    MODE9(new double[]{288.4, 3663, 52.8, 236.8}),
    MODE10(new double[]{295.8, 3663, 58.5, 246.3}),
    MODE11(new double[]{282.2, 3663, 0, 266.7}),
    MODE12(new double[]{264, 3663, 44.7, 212.2}),
    MODE13(new double[]{332.4, 3663, 93.2, 282.4}),
    MODE14(new double[]{300, 3663, 93.2, 250}),
    MODE15(new double[]{220, 3663, 0, 150}),
    MODE16(new double[]{350, 3663, 93.2, 300});
    private double[] doubles;
    Modes(double[] doubles) {
        this.doubles = doubles;
    }

    public double[] toDouble() {
        return doubles;
    }
}
