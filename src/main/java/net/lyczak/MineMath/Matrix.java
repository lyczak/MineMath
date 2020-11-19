package net.lyczak.MineMath;

import org.bukkit.util.Vector;

public class Matrix {
    private Vector c1;
    private Vector c2;
    private Vector c3;

    public Matrix(Vector c1, Vector c2, Vector c3) {
        this.c1 = c1.clone();
        this.c2 = c2.clone();
        this.c3 = c3.clone();
    }

    public Matrix(Vector diag) {
        this.c1 = new Vector(diag.getX(), 0, 0);
        this.c2 = new Vector(0, diag.getY(), 0);
        this.c3 = new Vector(0, 0, diag.getZ());
    }

    public Matrix clone() {
        return new Matrix(c1.clone(), c2.clone(), c3.clone());
    }

    public Vector multiply(Vector v) {
        return new Vector(
                v.getX() * c1.getX() + v.getY() * c2.getX() + v.getZ() * c3.getX(),
                v.getX() * c1.getY() + v.getY() * c2.getY() + v.getZ() * c3.getY(),
                v.getX() * c1.getZ() + v.getY() * c2.getZ() + v.getZ() * c3.getZ());
    }

    public Matrix transpose() {
        Vector r1 = new Vector(c1.getX(), c2.getX(), c3.getX());
        Vector r2 = new Vector(c1.getY(), c2.getY(), c3.getY());
        Vector r3 = new Vector(c1.getZ(), c2.getZ(), c3.getZ());

        c1 = r1;
        c2 = r2;
        c3 = r3;

        return this;
    }

    public Matrix invert() {
        // This will div-by-zero if non-invertible
        double k = 1.0 / this.determinant();

        this.transpose();

        // Construct adjugate matrix
        Vector a1 = new Vector(
                c2.getY() * c3.getZ() - c3.getY() * c2.getZ(),
                c3.getX() * c2.getZ() - c2.getX() * c3.getZ(),
                c2.getX() * c3.getY() - c3.getX() * c2.getY());
        Vector a2 = new Vector(
                c3.getY() * c1.getZ() - c1.getY() * c3.getZ(),
                c1.getX() * c3.getZ() - c3.getX() * c1.getZ(),
                c3.getX() * c1.getY() - c1.getX() * c3.getY());
        Vector a3 = new Vector(
                c1.getY() * c2.getZ() - c2.getY() * c1.getZ(),
                c2.getX() * c1.getZ() - c1.getX() * c2.getZ(),
                c1.getX() * c2.getY() - c2.getX() * c1.getY());

        a1.multiply(k);
        a2.multiply(k);
        a3.multiply(k);

        c1 = a1;
        c2 = a2;
        c3 = a3;

        return this;
    }

    public double determinant() {
        return  c1.getX() * c2.getY() * c3.getZ() -
                c1.getY() * c2.getX() * c3.getZ() -
                c1.getX() * c2.getZ() * c3.getY() +
                c1.getZ() * c2.getX() * c3.getY() +
                c1.getY() * c2.getZ() * c3.getX() -
                c1.getZ() * c2.getY() * c3.getX();
    }


}
