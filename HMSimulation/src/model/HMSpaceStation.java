package model;

import org.siso.spacefom.frame.SpaceTimeCoordinateState;
import model.SpaceTimeCoordinateStateCoder;
import skf.coder.HLAunicodeStringCoder;
import skf.model.object.annotations.Attribute;
import skf.model.object.annotations.ObjectClass;
import org.apache.commons.math3.complex.Quaternion;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

@ObjectClass(name = "PhysicalEntity.HMSpaceStation")
public class HMSpaceStation {
	
	@Attribute(name = "name", coder = HLAunicodeStringCoder.class)
	private String name = null;
	
	@Attribute(name = "parent_reference_frame", coder = HLAunicodeStringCoder.class)
	private String parent_name = null;
	
	@Attribute(name = "state", coder = SpaceTimeCoordinateStateCoder.class)
	private SpaceTimeCoordinateState state = null;
	
	public HMSpaceStation(){}

	public HMSpaceStation(String name, String parent_name, Position position) {
		this.name = name;
		this.parent_name = parent_name;
		this.state = new SpaceTimeCoordinateState();
		Quaternion rotation = new Quaternion(0, 0, 0, 1);
		this.state.getRotationState().setAttitudeQuaternion(rotation);
		this.setPosition(position);
	}

	public void setPosition(Position position) {
		Vector3D vector3d = new Vector3D(position.getX(), position.getY(), position.getZ());
		this.state.getTranslationalState().setPosition(vector3d);
	}

	public Position getPosition() {
		Vector3D vector3d = state.getTranslationalState().getPosition();
		return new Position(vector3d.getX(), vector3d.getY(), vector3d.getZ());

	}
	
	public void updatePosition(double time)
	{
		// nothing happening here
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the parent_name
	 */
	public String getParent_name() {
		return parent_name;
	}

	/**
	 * @param parent_name the parent_name to set
	 */
	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}
	
	public SpaceTimeCoordinateState getState() {
		return state;
	}

	public void setState(SpaceTimeCoordinateState state) {
		this.state = state;
	}

}
