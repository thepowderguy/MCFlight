package thepowderguy.mcflight.physics;

import thepowderguy.mcflight.util.Mat3;
import thepowderguy.mcflight.util.Vec3;

//** NOTE: implement this class when needed.
public abstract class ControlSurface {

	public final Vec3 position;
	protected Vec3 prevtransformposition = new Vec3();
	protected Vec3 transformposition = new Vec3();
	public Vec3 normal;
	public final double wingArea;
	protected double angleOfAttack;
	Vec3 prevForce = new Vec3();
	Vec3 force = new Vec3();

	public abstract double getDragFromAlpha(double d);
	public abstract double getLiftFromAlpha(double d);
	
	public void applyAerodynamicForces(Mat3 transform, Vec3 motion, Vec3 angularVelocity, Vec3 vel, double coefficient, double ctrlModifier) {
	}
	
	/*
	public void applyAerodynamicForces(Mat3 transform, Vec3 motion, Vec3 angularVelocity, Vec3 vel, double coefficient, double ctrlModifier) {
		double dragMagnitude = Vec3.cosTheta(vel, normal) * coefficient * wingArea;
		Vec3 force = Vec3.mul(normal, dragMagnitude);
		motion.add(force);
		transformposition = transform.transform(position);
		angularVelocity.add(Vec3.cross(transformposition, force).mul(ctrlModifier));
	}
	*/
	
	public ControlSurface(double x, double y, double z, double scale, double area) {
		position = new Vec3(x*scale, y*scale, z*scale);
		wingArea = area;
		normal = new Vec3();
	}
	
	public void updateNormal(Vec3 n) {
		normal.set(n);
	}
	
	public Vec3 getNormal() {
		return normal;
	}
	
	public Vec3 getInterpolatedPosition(float partialTicks) {
		return Vec3.interpolate(prevtransformposition, transformposition, partialTicks);
	}
	
	public double getAngleOfAttack() {
		return this.angleOfAttack;
	}
	
	public Vec3 getForce(float partialTicks) {
		return Vec3.interpolate(prevForce, force, partialTicks);
	}
	
	public void setForce(Vec3 inforce) {
		prevForce = force;
		force = inforce;
	}
	
	public void setPosition(Vec3 pos) {
		prevtransformposition = transformposition;
		transformposition = pos;
	}
}
